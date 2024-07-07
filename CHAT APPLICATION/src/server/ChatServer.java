
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer extends Frame implements Runnable,ActionListener{
    TextField textField;
    TextArea textArea;
    Button send;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread chat;
    ChatServer(){
        textField = new TextField();
        textField.setPreferredSize(new Dimension(400, 40)); // To Set preferred size
        textArea = new TextArea();
        send = new Button("Send");
        send.addActionListener(this);
        try{
            serverSocket=new ServerSocket(12000);
            socket=serverSocket.accept();
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
        }
        catch(Exception e){

        }
        add(textField);
        add(textArea);
        add(send);
        chat=new Thread(this);
        chat.setDaemon(true); //Set priority
        chat.start();


        setSize(500,500);
        setTitle("Dora");
        setLayout(new FlowLayout());
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        String msg=textField.getText();
        textArea.append("Dora:"+msg+"\n");
        textField.setText("");
        try{
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        }
        catch(IOException ex){


        }
    }

    public static void main(String[] args) {
        new ChatServer();

    }
    public  void run(){
        while(true){
            try{
                String msg=dataInputStream.readUTF();
                textArea.append("Bujji:"+msg+"\n");
            }
            catch (Exception E){

            }
        }
    }
}