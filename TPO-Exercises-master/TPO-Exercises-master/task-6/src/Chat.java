import javax.swing.*;
import javax.naming.*;
import javax.jms.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Chat extends SwingWorker {
    private JPanel chatPanel;
    private JTextArea message;
    private JButton sendButton;
    private JTextArea chat;

    private String username = "";

    JPanel getChatPanel() {
        return chatPanel;
    }

    @Override
    protected Object doInBackground() throws Exception {
        Context ctx = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
        String admTopicName = "topic1";
        String subscriptionName = username;
        Topic topic = (Topic) ctx.lookup(admTopicName);
        Connection con = factory.createConnection();

        message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if(e.getExtendedKeyCode() == 10)
                    sendButton.doClick();
                    message.setText("");
            }
        });

        //add action listener to send button
        sendButton.addActionListener(e -> {
            Session ses;
            try {
                Destination dest = (Destination) ctx.lookup(admTopicName);
                ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer producer = ses.createProducer(dest);
                con.start();

                String messageToSend = message.getText();

                if(!messageToSend.equals("") && !messageToSend.equals(" ")){
                    TextMessage msg = ses.createTextMessage();
                    msg.setText("["+username+"]: "+messageToSend);
                    producer.send(msg);
                }
                message.setText("");
            } catch (JMSException | NamingException e1) {
                e1.printStackTrace();
            }
        });

        //receive message from chat
        try {
            Session ses = con.createSession(false, Session.SESSION_TRANSACTED);
            TopicSubscriber subs = ses.createDurableSubscriber(topic, subscriptionName);

            con.start();

            while (true) {
                Message message = subs.receiveNoWait();
                if (message == null) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception exc) {
                        break;
                    }
                } else chat.append(message + "\n");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    JTextArea getChat() {
        return chat;
    }

    void setUsername(String username) {
        this.username = username;
    }
}
