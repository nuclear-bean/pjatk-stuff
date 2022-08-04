/**
 *
 *  @author Partyka Jakub S18830
 *
 */
package S_PASSTIME_SERVER1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

class Client {
    private String host , id;
    private int port;
    private String log;

    //server connection
    private SocketChannel socketChannel;
    private ByteBuffer buffer;
    private Charset charset = Charset.forName("UTF-8");

    Client(String host, int port, String id) {
        log("=== " + id + " log start ===");
        this.host = host;
        this.id = id;
        this.port = port;

        //allocate buffer
        buffer = ByteBuffer.allocateDirect(1024);
    }

    void connect() {
        if(socketChannel.isOpen())
            return;

        //connect to server using socket channel
        try {
            socketChannel= SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(host, port));
            while (!socketChannel.finishConnect()) {}
        } catch(UnknownHostException e) {
            System.err.println("Unknown host " + host);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    String send(String message) {
        CharBuffer charBuffer = CharBuffer.wrap(message);
        ByteBuffer outBuf = charset.encode(charBuffer);
        try {
            socketChannel.write(outBuf);
            return readResponse();
        } catch (IOException e) {
            return "error: " + e.getMessage();
        }
    }

    private String readResponse() throws IOException {
        while (true) {
            buffer.clear();

            int readBytes = socketChannel.read(buffer);     // non-blocking read
            // natychmiast zwraca liczbę
            // przeczytanych bajtów

            if (readBytes == 0) {                           //no data ye
                continue;
            }
            else if (readBytes == -1) {
                socketChannel.close();
                throw new IOException("Server channel closed");
            }
            else {
                //data present in buffer - read
                buffer.flip();

                //check if data complete ?

                return new String(buffer.array(), 0, buffer.position());
            }
        }
    }

    private void disconnect(){
        try {
            socketChannel.close();
            socketChannel.socket().close();
        } catch(Exception ignored) {}
    }

    private void log (String message){
        this.log += message + "\n";
    }

}
