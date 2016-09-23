/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author shivam
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Object[] options={"Single player","Multiplayer"};
        int response=JOptionPane.showOptionDialog(null,"Choose Mode", "2048",
        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
        null, options, options[0]);
        int port = 0;
        if(response==1)
        {
            Object[] opt={"HOST","CONNECT"};
            int res=JOptionPane.showOptionDialog(null,"Select","2048",JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,opt,opt[0]);
            if(res==0)
            {
                port=2345;
            }
            else if(res==1)
            {
                port=2344;
            }
            else
            {
                System.exit(0);
            }
            String ip=JOptionPane.showInputDialog("Enter IP Address of the second player");
            System.out.print(ip);
            if(ip!=null)
            new MyFrame(ip,port);
        }
        else if(response==0)
            new Frame1();
//       Grid gd=new Grid(5);
//       gd.setGrid();
//       int k=0;
//       int[] x=gd.extractvalues();
//      for(int i=0;i<4;++i)
//      {
//          for(int j=0;j<4;++j)
//          {
//              System.out.print(x[k++]+" ");
//          }
//          System.out.println();
//      }
    }
    
}
