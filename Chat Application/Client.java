
import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Client extends JFrame {

    Socket socket;
    BufferedReader br;
    PrintWriter out;

// Declare a component
private JLabel heading = new JLabel("Client Area");
private JTextArea messagArea =new JTextArea();
private JTextField messageInput = new JTextField();
private Font font = new Font("Roboto",Font.PLAIN,20);






    // constructor
    public Client(){
        try{
            System.out.println("sending request to server");
            socket= new Socket("127.0.0.1",7777);
            System.out.println("Connection done");


            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream());

createGUI();
handleEvents();


    startReading();
    // startWriting();

        }
        catch(Exception e){

        }

    }
    private void handleEvents (){
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
               
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
               
            }

            @Override
        
            public void keyReleased(KeyEvent e) {
                
                // System.out.println("key released"+e.getKeyCode());
                if(e.getKeyCode()==10){
                    // System.out.println("you have pressed enter button");
                    String contentToSend=messageInput.getText();
                    messagArea.append("Me :"+contentToSend+"\n");
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                }
            }
            
        });
    }
    private void createGUI(){
        // create GUI code
        this.setTitle("Client Messager[END]");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // coding for component
        heading.setFont(font);
        messagArea.setFont(font);
        messageInput.setFont(font);


        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        messagArea.setEditable(false);

        // meassage text input


// set the frame layout
this.setLayout(new BorderLayout());

// Add the components in frame

this.add(heading,BorderLayout.NORTH);
JScrollPane jScrollPane=new JScrollPane(messagArea);
this.add(jScrollPane,BorderLayout.CENTER);
this.add(messageInput,BorderLayout.SOUTH);

        this.setVisible(true);
    }



    // start reading area

    public void startReading(){
        // thread read the data and give them
        Runnable r1=()->{
        
            System.out.println("reader started..");
            
            while(true){
                try{
                String msg=br.readLine();
                if(msg.equals("exit"))
                {
                    System.out.println("Server terminated the chat");
                    JOptionPane.showMessageDialog(this, "Server terminated the chat");
                    messageInput.setEnabled(false);
                    socket.close();
                    break;
                }
                // System.out.println("Server:"+msg);
                messagArea.append("Server: " + msg+"\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        }
        };
    
        new Thread (r1).start();
        }
        
        
        
        public void startWriting(){
        // thread data use  and send the data to client
        Runnable r2 =()->{
            System.out.println("writer started...");
            try{
            while(!socket.isClosed()){
                
        
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit"))
{
    socket.close();
    break;
}
        
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        
        };
        new Thread (r2).start();
        }
    
    public static void main(String[] args){
        System.out.println("hello");
        new Client();
    }
}
