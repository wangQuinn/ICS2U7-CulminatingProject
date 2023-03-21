/*
Name :Quinn Wang
Teacher: Mrs.Strelkovska
Code: ICS207
Date: January 18th 2023
Class : Game(Main) class
*/

import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.event.*;

public class Game{

  public static void main(String[] args) {
    JFrame f = new JFrame("Game"); 
   
    Container cont = f.getContentPane();  
	cont.setLayout(new BorderLayout());  
	
	GamePanel game= new GamePanel();  //adding Game Panel to the center
	cont.add(game, BorderLayout.CENTER ); 
	 
	 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	 f.setVisible(true);     
	 f.setSize(400, 400);
	f.setResizable(false);
	 
	}
}
