/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048game;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;
import javax.swing.JPanel;
/**
 *
 * @author shivam
 */
public class FriendGrid extends JPanel implements Runnable {
    private Tile[][] mytiles=new Tile[4][4];
    final int tilesize = 128;
    final int gridsize = 64 * 8;
    int port=0;
    static int score=0;
    static boolean win=false,lose=false;
    FriendGrid(int port)
    {
     //   this.port=port;
       if(port==2345)
       {
           this.port=2344;
       }
       else
       {
           this.port=2345;
       }
        for(int i=0;i<4;++i)
        {
            for(int j=0;j<4;++j)
            {
                mytiles[j][i]=new Tile();
            }
        }
    }
    public int a[];
    public void getMsg(){
	
        try {
			ServerSocket ss=new ServerSocket(port);			
		    while(true){
		    	Socket s=ss.accept();
		    	BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
		    	String msg=br.readLine();
		    	a=new int[16];
                        System.out.println(msg);
		    	StringTokenizer st=new StringTokenizer(msg,",");
		    	int i=0;
		    	while(st.hasMoreElements()){
                                if(i==16)
                                {
                                    score=Integer.parseInt(st.nextToken());
                                }
                                else if(i==17)
                                {
                                    win=Boolean.parseBoolean(st.nextToken());
                                }
                                else if(i==18)
                                {
                                    lose=Boolean.parseBoolean(st.nextToken());
                                }
                                else
                                {
                                    a[i]=Integer.parseInt(st.nextToken());
                                }
                                i++;
                                
		    	}
		    	 show();
                         repaint();
		    	//System.out.println (msg);
		    		
		    }
		}
		catch (Exception ex) {
			System.out.println (ex);
		}	
	}
	public void show(){

                
                for (int i = 0; i<16; i++)
                {
                    mytiles[i/4][i%4].SetValue(a[i]);
                    
                }
                repaint();
		//System.out.println ();
	}
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(0xbbada0));
        g.fillRect(0,0 , gridsize, gridsize + 20);
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                painttile(g, i, j);
            }
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.ITALIC, 18));
        g.drawString("Score:", 350, 520);
        String sc = String.valueOf(score);
        g.drawString(sc, 420, 520);
    }
    
    void painttile(Graphics g2, int x, int y) {
        Graphics2D g = ((Graphics2D) g2);
        //g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(mytiles[x][y].getBackground(mytiles[x][y].GetValue()));
        g.fillRoundRect(x * (120 + 5) + 5, y * (120 + 5) + 5, 120, 120, 30, 30);
        //String val=(String)Mytiles[x][y].GetValue();
        int val = mytiles[x][y].GetValue();
        if (val != 0) {
            final int size = val < 100 ? 36 : val < 1000 ? 32 : 24;
            Font ft = new Font("Arial", Font.BOLD, size);
            g.setFont(ft);
            g.setColor(mytiles[x][y].getForeground(mytiles[x][y].GetValue()));
            String temp = String.valueOf(val);

            g.drawString(temp, x * (120 + 5) + 5 + 60, y * (120 + 5) + 5 + 60);

        }
        if (win || lose) {
            g.setColor(new Color(255, 255, 255, 30));
            g.fillRect(0, 0, getWidth(), getHeight());
            Font d = new Font("Arial", Font.PLAIN, 40);
            g.setFont(d);
            g.setColor(Color.BLUE);
            if (lose) {
                Grid.win=true;
//                g.drawString("ALAS!", 200, 180);
//                g.drawString("You've lost the Game!", 100, 230);
            } else {
                Grid.lose=true;
//                g.drawString("You've won the game!", 100, 350);
            }
//            g.setFont(new Font("Arial", Font.PLAIN, 32));
//            //g.drawString("Press Esc to Restart", 130, 270);
//            g.drawString("Score:", 200, 320);
//            String sc = String.valueOf(score);
//            g.drawString(sc, 300, 320);

        }
    }
    public void run()
    {
        getMsg();	
    }
	
}
