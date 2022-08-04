/**
 *
 *  @author Partyka Jakub S18830
 *
 */

package S_PASSTIME_SERVER1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class Server {
    private String host;
    private int port;
    private boolean isRunning = false;
    private Selector selector;
    private SelectionKey selectionKey;
    private StringBuffer stringBuffer;
    private ByteBuffer buffer;
    private Charset charset = Charset.forName("UTF-8");


    Thread serverThread;

    private String serverLog;

    //connection
    ServerSocket serverSocket;

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void startServer() {
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.socket().bind(new InetSocketAddress(host, port));
            selector = Selector.open();
            selectionKey = serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            serverThread = new Thread(() -> {
                while (isRunning){
                    try {
                        selector.select();
                        Set keys = selector.selectedKeys();

                        Iterator iter = keys.iterator();
                        while(iter.hasNext()) {

                            // pobranie klucza
                            SelectionKey key = (SelectionKey) iter.next();
                            // musi być usunięty ze zbioru (nie ma autonatycznego usuwania)
                            // w przeciwnym razie w kolejnym kroku pętli "obsłużony" klucz
                            // dostalibyśmy do ponownej obsługi
                            iter.remove();
                            // Wykonanie opreacji opisywanej przez klucz

                            if (key.isAcceptable()) { // połaczenie klienta gotowe do akceptacji
                                // Uzyskanie kanału do komunikacji z klientem
                                // accept jest nieblokujące, bo już jest połączenie
                                SocketChannel cc = serverChannel.accept();
                                // Kanał nieblokujący, bo będzie rejestrowany u selektora
                                cc.configureBlocking(false);
                                // rejestrujemy kanał komunikacji z klientem
                                // do monitorowania przez ten sam selektor
                                cc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                                continue;
                            }

                            if (key.isReadable()) {  // któryś z kanałów gotowy do czytania
                                // Uzyskanie kanału na którym czekają dane do odczytania
                                SocketChannel cc = (SocketChannel) key.channel();
                                handleRequest(cc);
                            }
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleRequest(SocketChannel socketChannel) {
        if (!socketChannel.isOpen()) return; // jeżeli kanał zamknięty - nie ma nic do roboty

        // Odczytanie zlecenia
        stringBuffer.setLength(0);
        buffer.clear();
        try {
            readLoop:                    // Czytanie jest nieblokujące
            while (true) {               // kontynujemy je dopóki
                int n = socketChannel.read(buffer);     // nie natrafimy na koniec wiersza

                if (n > 0) {
                    buffer.flip();
                    CharBuffer charBuffer = charset.decode(buffer);
                    while(charBuffer.hasRemaining()) {
                        char c = charBuffer.get();
                        if (c == '\r' || c == '\n') break readLoop;
                        stringBuffer.append(c);
                    }
                }
            }
            // Analiza zlecenia (jak poprzednio) i wołanie nowej metody
            // sendResponse zapisującej odpowiedź do kanału
            String [] requestMessage = stringBuffer.toString().split(" ");
            String clientCommand = requestMessage[0];

            switch (clientCommand) {

                case "bye":
                    if(requestMessage.length == 1){
                        sendResponse(socketChannel,"logged out");
                    }
                    else if (requestMessage[1].equals("and")) {
                        sendResponse(socketChannel, getServerLog());
                    }
                    else {
                        sendResponse(socketChannel, "incorrect request!");
                    }
                    //end connection - close channel and socket
                    socketChannel.close();
                    socketChannel.socket().close();
                    break;

                case "login":
                    String login = requestMessage[1];
                    sendResponse(socketChannel,"logged in");
                    log(login + " logged in at " + (new Timestamp(System.currentTimeMillis()).toString().split(" ")[1]));
                    break;


                default:
                    //incorrect command
                    sendResponse(socketChannel, "incorrect request!");
                    break;
            }

        } catch (Exception exc) {
            exc.printStackTrace();
            try {
                socketChannel.close();
                socketChannel.socket().close();
            }
            catch (Exception ignored) {}
        }
    }

    private void sendResponse(SocketChannel sc, String message) throws IOException {
        StringBuffer response = new StringBuffer(message);
        response.append(' ');
        response.append('\n');
        ByteBuffer buffer = charset.encode(CharBuffer.wrap(response));
        sc.write(buffer);
    }

    public void stopServer() {
        isRunning = false;
    }

    public String getServerLog() {
        return serverLog;
    }

    private void log(String message){
        serverLog += message + "\n";
    }
}
