package pkg2048game;


import java.awt.Container;
import javax.swing.JLabel;
import pkg2048game.Grid;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shivam
 */
public class Frame1 extends JFrame{
    Container c;
    public Frame1()
    {
        c=getContentPane();
        setTitle("2048");
        setSize(64*8+10,64*8+60);
        setLayout(null);
        setResizable(false);
        Grid gd=new Grid(1,null,0);
        gd.setBounds(0,0,64*8+10,64*8+60);
        c.add(gd);
        gd.setFocusable(true);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(3);
    }
}
