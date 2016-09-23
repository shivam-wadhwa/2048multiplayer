/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import java.awt.font.*;
import java.net.*;
import java.io.*;

/**
 *
 * @author shivam
 */
public class Grid extends JPanel {

    public Tile[][] Mytiles = new Tile[4][4];
    final int tilesize = 128;
    final int gridsize = 64 * 8;
    static int score = 0;
    static boolean win, lose;
    int SIZE = 4;
    String ip;
    int port;
    int op;
//    public Grid(int a) {
//        for (int i = 0; i < 4; ++i) {
//            for (int j = 0; j < 4; ++j) {
//                Mytiles[i][j] = new Tile();
//            }
//        }
//    }

    public Grid(int a,String ip,int port) {
        this.ip=ip;
        this.port=port;
        op=a;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                Mytiles[i][j] = new Tile();
            }
        }
        setFocusable(true);
        addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ESCAPE && a==1) {
                    reset();
                }
                if (!win && !canmove()) {
                    lose = true;
                }
                if (!win && !lose) {
                    switch (ke.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            LEFT();
                            break;
                        case KeyEvent.VK_DOWN:
                            RIGHT();
                            break;
                        case KeyEvent.VK_RIGHT:
                            DOWN();
                            break;
                        case KeyEvent.VK_LEFT:
                            UP();
                            break;
                        default:
                            break;
                    }
                    //print();
                }
                if(a==0)
                sendMyGridStatus(convertToString(extractvalues()));
                repaint();
            }

            public void keyReleased(KeyEvent ke) {

            }

            public void keyTyped(KeyEvent ke) {

            }
        });
        reset();

    }
    void sendMyGridStatus(String msg){
        try{
            Socket s=new Socket(ip,port);
            PrintWriter pw=new PrintWriter(s.getOutputStream());
            pw.write(msg);
            pw.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"cannot connect");
        }
    }
    public String convertToString(int a[]){
        String s="";
        for(int i=0;i<a.length;++i){
                s=s+a[i]+",";
        }
        s=s+score+","+win+","+lose;
        return s;
    }
    public int[] extractvalues() {
        int temp[] = new int[16];
        int k = 0;
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
//                String temp=String.valueOf(Mytiles[i][j].GetValue());
//                ret.concat(temp);
//                if(!(i==3&&j==3))
//                ret.concat(",");
                temp[k++] = Mytiles[i][j].GetValue();
            }
        }
        return temp;
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(0xbbada0));
        g.fillRect(00, 00, gridsize, gridsize + 20);
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
        g.setColor(Mytiles[x][y].getBackground(Mytiles[x][y].GetValue()));
        g.fillRoundRect(x * (120 + 5) + 5, y * (120 + 5) + 5, 120, 120, 30, 30);
        //String val=(String)Mytiles[x][y].GetValue();
        int val = Mytiles[x][y].GetValue();
        if (val != 0) {
            final int size = val < 100 ? 36 : val < 1000 ? 32 : 24;
            Font ft = new Font("Arial", Font.BOLD, size);
            g.setFont(ft);
            g.setColor(Mytiles[x][y].getForeground(Mytiles[x][y].GetValue()));
            String temp = String.valueOf(val);

            g.drawString(temp, x * (120 + 5)  +57, y * (120 + 5) + 73);

        }
        if (win || lose) {
            g.setColor(new Color(255, 255, 255, 30));
            g.fillRect(0, 0, getWidth(), getHeight());
            Font d = new Font("Arial", Font.PLAIN, 40);
            g.setFont(d);
            g.setColor(Color.BLUE);
            if (lose && op==1) {

                g.drawString("ALAS!", 200, 180);
                g.drawString("You've lost the Game!", 100, 230);
            } else if(win && op==1){
                g.drawString("You've won the game!", 100, 350);
            }
            g.setFont(new Font("Arial", Font.PLAIN, 32));
            if(op==1)
            {
                g.drawString("Press Esc to Restart", 130, 270);
                g.drawString("Score:", 200, 320);
                String sc = String.valueOf(score);
                g.drawString(sc, 300, 320);
            }
        }
    }

    public void setGrid() {

        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                int val = sc.nextInt();
                Mytiles[i][j].SetValue(val);
            }
        }

    }

    public void reset() {
        for (int i = 0; i < Mytiles.length; ++i) {
            for (int j = 0; j < Mytiles[i].length; ++j) {
                Mytiles[i][j].SetValue(0);
            }
        }
        lose = false;
        win = false;
        score = 0;
        addtile();
        addtile();
    }

    public boolean addtile() {
        if (!hasempty()) {
            return false;
        }
        while (true) {
            int x = (int) (Math.random() * SIZE);
            int y = (int) (Math.random() * SIZE);
            if (Mytiles[x][y].GetValue() == 0) {
                Mytiles[x][y].SetValue(Math.random() < 0.9 ? 2 : 4);
                return true;
            }
        }

    }

    public boolean hasempty() {
        for (int i = 0; i < Mytiles.length; ++i) {
            for (int j = 0; j < Mytiles[i].length; ++j) {
                if (Mytiles[i][j].GetValue() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canmove() {
        if (hasempty()) {
            return true;
        }
        if (haseqaulneighbours()) {
            return true;
        }
        return false;
    }

    public boolean haseqaulneighbours() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (Mytiles[i][j].GetValue() == Mytiles[i][j + 1].GetValue()) {
                    return true;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (Mytiles[j][i].GetValue() == Mytiles[j + 1][i].GetValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void LEFT() {
        int temp = score;
        for (int i = 0; i < Mytiles.length; ++i) {
            List<Tile> Mylist = new ArrayList();
            for (int j = 0; j < Mytiles[i].length; ++j) {
                Mylist.add(Mytiles[i][j]);
            }
            merge(Mylist);
            for (int j = 0; j < Mytiles[i].length; ++j) {
                Mytiles[i][j] = Mylist.get(j);
            }
        }
        if (hasempty()) {
            addtile();
        }
    }


    public void slidezero(List<Tile> tilelist) {
        int countzero = 0;
        for (int i = 0; i < tilelist.size(); ++i) {
            if (tilelist.get(i).GetValue() == 0) {
                countzero++;
            } else {
                tilelist.get(i - countzero).SetValue(tilelist.get(i).GetValue());
            }
        }
        for (int i = tilelist.size() - 1; countzero > 0; --i, countzero--) {
            tilelist.get(i).SetValue(0);
        }
    }

    public void merge(List<Tile> tilelist) {
        slidezero(tilelist);
        for (int i = 0; i < tilelist.size() - 1; ++i) {
            if (tilelist.get(i).GetValue() == tilelist.get(i + 1).GetValue()) {
                tilelist.get(i).SetValue(tilelist.get(i).GetValue() * 2);
                score += tilelist.get(i).GetValue();
                tilelist.get(i + 1).SetValue(0);
                i++;
            }
        }
        slidezero(tilelist);
    }

    public void RIGHT() {
        int temp = score;
        for (int i = 0; i < Mytiles.length; ++i) {
            List<Tile> Mylist = new ArrayList();
            for (int j = Mytiles[i].length - 1; j >= 0; --j) {
                Mylist.add(Mytiles[i][j]);
            }
            merge(Mylist);
            //printlist(Mylist);
            for (int j = Mytiles[i].length - 1; j >= 0; --j) {
                Mytiles[i][j].SetValue(Mylist.get(Mytiles[i].length - 1 - j).GetValue());
            }
        }
        if (hasempty()) {
            addtile();
        }
    }

    public void UP() {
        int temp = score;
        for (int j = 0; j < Mytiles[0].length; ++j) {
            List<Tile> Mylist = new ArrayList();
            for (int i = 0; i < Mytiles.length; ++i) {
                Mylist.add(Mytiles[i][j]);
            }
            merge(Mylist);
            for (int i = 0; i < Mytiles.length; ++i) {
                Mytiles[i][j].SetValue(Mylist.get(i).GetValue());
            }
        }
        if (hasempty()) {
            addtile();
        }
    }

    public void DOWN() {
        int temp = score;
        for (int j = 0; j < Mytiles[0].length; ++j) {
            List<Tile> Mylist = new ArrayList();
            for (int i = Mytiles.length - 1; i >= 0; --i) {
                Mylist.add(Mytiles[i][j]);
            }
            merge(Mylist);
            //printlist(Mylist);
            for (int i = Mytiles.length - 1; i >= 0; --i) {
                Mytiles[i][j].SetValue(Mylist.get(Mytiles.length - 1 - i).GetValue());
            }
        }
        if (hasempty()) {
            addtile();
        }
    }
}
