import javax.swing.*;
import java.io.*;
import java.util.HashMap;

public class Main {
    private static File users = new File("users.txt");
    static HashMap <String,char[]> passwords = new HashMap<>();

    //GUI
    private static JFrame frame;
    private static JPanel loginPanel;
    private static JPanel chatPanel;
    private static Chat chat;
    private static JTextArea chatField;
    private static Login login;

    private Main(){
        readUsers();
        SwingUtilities.invokeLater(Main::initFrame);
    }


    public static void main(String[] args) {
        new Main();
    }

    private static void initFrame(){
        //create views
        login = new Login();
        chat = new Chat();
        chatPanel = chat.getChatPanel();
        frame = new JFrame("Login window");
        loginPanel = login.getLoginPanel();
        frame.add(loginPanel);

        frame.setSize(400,400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void readUsers(){
        passwords.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(users));
            String line = reader.readLine();
            int counter = 0;

            String username = "";
            char[] pass;
            while (line != null){
                if(counter % 2 == 0){
                    username = line;
                }
                else {
                    pass = line.toCharArray();
                    passwords.put(username,pass);
                }
                counter++;
                line = reader.readLine();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * saves given username and corresponding password to file
     * @param username username to save
     * @param password password to save
     */
    static void addUser(String username,char[] password){
        try {
            FileWriter writer = new FileWriter(users,true);
            writer.write(username + "\n");
            for (char c : password) {
                writer.write(c);
            }
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        readUsers();
    }


    static void logIn() {
        loginPanel.setVisible(false);
        frame.add(chatPanel);
        frame.setTitle("JMS CHAT");
        chatField = chat.getChat();

        //start executor
        chat.setUsername(login.getUsername());
        chat.execute();
        chat.addPropertyChangeListener(evt -> {
            if(evt.getPropertyName().equals("msg")){
                chatField.append(evt.getNewValue().toString() + "\n");
            }
        });


    }
}
