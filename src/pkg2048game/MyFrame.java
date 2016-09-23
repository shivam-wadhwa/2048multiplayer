/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048game;
import java.awt.*;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
        
/**
 *
 * @author shivam
 */
public class MyFrame extends JFrame implements Runnable{
    JLabel lTime;
    Container c;
    public MyFrame(String ip,int port)
    {
//        while(checkconnection(ip,port)==false);
//            System.exit(0);
        c=getContentPane();
        setTitle("2048");
        setSize(64*8+800,64*8+60);
        setLayout(null);
        setResizable(true);
        Grid gd=new Grid(0,ip,port);
        FriendGrid fg=new FriendGrid(port);
        fg .setBounds(784,0,64*8+10,64*8+60);
        c.add(fg);
        gd.setBounds(0,0,64*8+10,64*8+60);
        c.add(gd);
        lTime=new JLabel("gdfgd");
        lTime.setBounds(64*8+100,200,100,50);
        c.add(lTime);
        gd.setFocusable(true);
     //   gd.addKeyListener(this);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(3);
        new Thread(this).start();
        new Thread(fg).start();
    }
    public boolean checkconnection(String ip,int port)
    {
        try{
            Socket s=new Socket(ip,2345);
            return true;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,"connection failure");
            return false;
        }
    }
    public void run(){
        try{
            int i=300;
            while(true){
                if(i%60<=9)
                    lTime.setText("0"+String.valueOf(i/60)+":0"+String.valueOf(i%60));
                else
                    lTime.setText("0"+String.valueOf(i/60)+":"+String.valueOf(i%60));
                if(i==0)
                {   
                    endGame();
                }
                else
                    i--;
                Thread.sleep(1000);
                repaint();
            }
        }catch(Exception e){
            
        }
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        if(Grid.lose)
            g.drawString("You Lost!", 64*8+100,500);
        else if(Grid.win)
            g.drawString("You Won!", 64*8+100,500);
    }

    public void endGame() {
        if(FriendGrid.score<=Grid.score)
        {
            FriendGrid.lose=true;
            Grid.win=true;
        }
        else
        {
            Grid.lose=true;
            FriendGrid.win=true;
        }
    }
}
