/*
Name :Quinn Wang
Teacher: Mrs.Strelkovska
Code: ICS207
Date: January 18th 2023
Class : GamePanel class
*/
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener{
	private TileMap tileMap;
	private Player player;
	
	private Timer myT;
	
	public JLabel label;
	
	private Timer jumpTimer;
	private static int count = 0;
	
	public GamePanel(){
		//panel stuff
		setPreferredSize(new Dimension(400, 400));
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		setBackground(Color.BLACK);
		
		//initilizing stuff
		tileMap = new TileMap("levelOne.txt", 40);
		player=  new Player(tileMap);
	
		myT = new Timer(60, this);
		myT.start();
		
		label = new JLabel("");
		label.setForeground(Color.WHITE);
		add(label);
		
		jumpTimer = new Timer(1000, this); // 1 second timer
		jumpTimer.start();
		
	}
	
	//for drawing
	public void paintComponent(Graphics g){
		tileMap.draw(g);
		player.draw(g);
	}
	
	//for updating
	private void update(){
	
		tileMap.update();
		player.update();
	}
	
	
	
	public void actionPerformed(ActionEvent e) { // timer events
		//System.out.println("action Performed!");
		update();
		repaint();
		if(e.getSource() == jumpTimer){
			count--;
			if (count >= 0) {
				System.out.println(count);
			  label.setText(Integer.toString(count));
			  if(count ==0){
				  label.setText("JUMP!");
			  }
			} 
			else {
				player.jumping = true;
			  count = 4;
			}
		}
	}
	
	//key events
	
	public void keyTyped(KeyEvent k){}
	public void keyPressed(KeyEvent k){
		//System.out.println("KEY PRESSED!");
		int e = k.getKeyCode();
		
		if(e == KeyEvent.VK_A){
			player.setLeft(true);
		}
		if(e == KeyEvent.VK_D){
			player.setRight(true);
		}
		
		if(e ==  KeyEvent.VK_SPACE){
			player.setJumping(true);
		}
		if(e == KeyEvent.VK_W){
			player.setGliding(true);
		}
		
		if(e == KeyEvent.VK_E){
			player.setInteract(true);
		}
		
	}
	public void keyReleased(KeyEvent k){
		int e = k.getKeyCode();
		
		if(e == KeyEvent.VK_A){
			player.setLeft(false);
		}
		if(e == KeyEvent.VK_D){
			player.setRight(false);
		}
		if(e == KeyEvent.VK_E){
			player.setInteract(false);
		}
		if(e == KeyEvent.VK_W){
			player.setGliding(false);
		}
		
		
	}
	
}

///////////////////

