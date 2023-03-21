/*
Name :Quinn Wang
Teacher: Mrs.Strelkovska
Code: ICS207
Date: January 18th 2023
Class : Player class
*/
import java.io.*;
import java.awt.*;
import java.util.Scanner.*;
import javax.imageio.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.event.*;

public class Player {
	private double x,y,velocityX,velocityY; // velocity
	private int width, height;
	private boolean left, right, falling, gliding, interacting;
	public boolean jumping;
	private double acceleration, maxSpeed, maxFallingSpeed, friction, jumpUp, gravity, glidingSpeed;
	
	private TileMap tileMap;
	
	private static int cnt=0;
	private boolean topLeft, topRight, bottomRight, bottomLeft;
	private boolean touchingCoin;
	
	private BufferedImage[] rightSprites;
	private BufferedImage[] leftSprites;
	private BufferedImage[] flySprites;
	private BufferedImage deathSprites;
	private BufferedImage standingSprite;
	
	private Rectangle playerRect;
	
	int levelNum;
	
	
	private JLabel timeLabel;
	private static int count =0;
	public Player(TileMap tileMap){
		
		this.tileMap = tileMap;
		width = 40;
		height = 40;
		
		tileMap.setCoin(580,280);
		tileMap.setSpike(400, 400);
		levelNum = 1;
		playerRect = new Rectangle((int)x, (int)y, 40,40);
		//change later
		acceleration = 5.6; // accleration 
		maxSpeed = 15.8;
		maxFallingSpeed = 21;
		friction = 4.8;
		jumpUp = -35;
		gravity = 5.6;
		glidingSpeed = 0.3;
		x = 100;
		y = 100;
		
		
		
		touchingCoin =false;
		loadAnimations();

	}
	
	
	
	
	//other functions
	
	public BufferedImage getNextRight(){
		cnt = (cnt + 1) % rightSprites.length;
		return rightSprites[cnt];
	}
	public BufferedImage getNextLeft(){
		cnt = (cnt + 1) % leftSprites.length;
		return leftSprites[cnt];
	}
	/*
	public BufferedImage getNextDeath(){
		cnt = (cnt + 1) % deathSprites.length;
		return deathSprites[cnt];
	}
	*/
	private void loadAnimations(){
		try{
			BufferedImage seagull_right = ImageIO.read(new File("seagull_Right.png")); //gets image
			//loading right
			rightSprites = new BufferedImage[4];
			for(int i =0; i <rightSprites.length;i++){ 
				rightSprites[i] = seagull_right.getSubimage(i*10,0, 10, 10);
				
			}
			//loading right
			BufferedImage seagull_left = ImageIO.read(new File("seagull_Left.png")); // gets image
			leftSprites = new BufferedImage[4];
			for(int i=0; i < leftSprites.length; i++){
				leftSprites[i] = seagull_left.getSubimage(i*10, 0, 10, 10);
			}
			
			//standing 
			standingSprite = ImageIO.read(new File("seagull_Stand.png"));
			
			//flying
			BufferedImage seagull_flying = ImageIO.read(new File("seagull_Flap.png")); // gets image
			flySprites = new BufferedImage[2];
			for(int i=0; i < flySprites.length; i++){
				flySprites[i] = seagull_flying.getSubimage(i*10, 0, 10, 10);
			}
			
		 
			deathSprites = ImageIO.read(new File("death.png"));
		}
			catch(Exception e){
				System.out.print("Error!!!!!!!!!!!!!" + e);
			}  
	}
	public void update(){
		getPosition();
		playerRect.setLocation((int)x,(int)y);
		
		//System.out.println("player : " + playerRect.getX()+ ":" + playerRect.getY());
	
	}
	
	
	
	private void getPosition(){
			//determine next position
		if(left){
			velocityX -= acceleration;
			if(velocityX < -maxSpeed){
				velocityX = -maxSpeed;
			}
		}
		else if(right){
			velocityX += acceleration;
			if(velocityX> maxSpeed){
				velocityX = maxSpeed;
			}
		}
		else{
			//stopping, but friciton
			if(velocityX >0){
				velocityX -= friction;
				if(velocityX < 0){
					velocityX = 0;
				}
			}
			else if(velocityX <0){
				velocityX += friction;
				if(velocityX >0){
					velocityX = 0;
				}
			}
		}
		if(jumping){
			velocityY = jumpUp;
			falling =true;
			jumping = false;
		}
		if(falling){
			if(gliding){
				velocityY += glidingSpeed;
				System.out.print("GLIDING!");
			}
			else
				velocityY += gravity;
			if(velocityY > maxFallingSpeed){
				velocityY = maxFallingSpeed;
			}
		}
		else{
			velocityY =0; 
		}
		
		//check collisions
		int currCol = tileMap.getColTile((int)x);
		int currRow = tileMap.getRowTile((int)y);
		
			//destination variables -> checking ahead to the future
		double futureX = x + velocityX;
		double futureY = y + velocityY;
		
		double tempX = x;
		double tempY = y;
		
		calculateTiles(x, futureY);
		if(velocityY <0){
			if(topLeft || topRight){ // top collisions
				velocityY = 0;
				tempY = currRow * 40 + height /2;
			}
			else{
				tempY += velocityY;
			}
		}
		if(velocityY >0){ // moving down
			if(bottomLeft || bottomRight){
				velocityY = 0;
				falling =false;
				tempY = (currRow + 1) * 40 - height /2; // for bounve
			}
			else{
				tempY += velocityY;
			}
		}
		calculateTiles(futureX, y);
		if(velocityX <0){
			//left collisions
			if(topLeft || bottomLeft){
				velocityX = 0;
				tempX = currCol * 40 + width /2; // stop at the right side of the block
				
			}
			else{
				tempX += velocityX;
			}
		}
		if(velocityX >0){ //
			if(topRight || bottomRight){
				//rightcollisions
				velocityX = 0;
				tempX =(currCol +1) * 40 - width/2;
			}
			else{
				tempX += velocityX;
			}
		}
		
		//checking if walking off block
		if(!falling){
			calculateTiles(x, futureY +1); // check block below a player
			if(!bottomLeft && !bottomRight){
				falling = true;
			}
		}
		
		x = tempX;
		y = tempY;
		
		//move the map 
		tileMap.setX((int)(200 - x));
		tileMap.setY((int)(200 - y));
		/*
		System.out.println("velocityX: " + velocityX + "velocityY:" + velocityY);
		System.out.println("falling: " + falling);
		System.out.println("left: " + left);
		System.out.println("right: " + right);
		*/
	}
	
	private void calculateTiles(double x, double y){
		int leftTile = tileMap.getColTile((int) (x - width/2));
		int rightTile = tileMap.getColTile((int) ( x + width/2) -1);
		int topTile = tileMap.getRowTile((int)(y -height /2));
		int bottomTile = tileMap.getRowTile((int) (y+height/2) -1);
		topLeft = tileMap.getTile(topTile, leftTile) != 0;
		topRight = tileMap.getTile(topTile, rightTile) !=0;
		bottomLeft = tileMap.getTile(bottomTile, leftTile) !=0;
		bottomRight = tileMap.getTile(bottomTile, rightTile) !=0;
		
		/*
		int leftTileValue = tileMap.getTile(leftTile, );
		int rightTileValue = tileMap.getTile(rightTile, );
		int topTileValue = tileMap.getTile((int) x, topTile);
		int bottomTileValue = tileMap.getTile((int) x, bottomTile);
		
		if(leftTileValue == 7 || rightTileValue == 7 || topTileValue == 7 || bottomTileValue == 7){
			System.out.println("COIN NEARBY");
		}
		*/
		
	}
	
	private void nextLevel(){
		levelNum ++;
		String fileName = "level"+levelNum+".txt";
		System.out.println("SWTICHING LEVELS");
		if(levelNum <= 5){
			tileMap.switchLevels(fileName);
		}
		else{
			tileMap.switchLevels("end.txt");
		}
		
		tileMap.setObjects(levelNum);
		reset();
		
		
		
	}
	
	private void reset(){
		
		if(levelNum == 2){
			x = 100;
			y = 400;
			
		}
		else if(levelNum == 3){
			x = 100;
			y = 1000;
		}
		else if(levelNum == 4){
			x = 100;
			y = 400;
		}
		else if(levelNum == 5){
			x = 40;
			y = 40;
		}
		else{
			x =100;
			y = 400;
		}
	}
	
	public void draw(Graphics g){
		int tileX = tileMap.getX();
		int tileY = tileMap.getY();
		BufferedImage playerImg;
		
		if(right){
			playerImg = getNextRight();
			g.drawImage(playerImg,(int)(tileX + x - width/2),(int)(tileY+y-height/2), 40,40,null);
		}
		else if(left){
			playerImg = getNextLeft();
			g.drawImage(playerImg,(int)(tileX + x - width/2),(int)(tileY+y-height/2), 40,40,null);
		}
		else if(jumping){
			g.drawImage(flySprites[1],(int)(tileX + x - width/2),(int)(tileY+y-height/2), 40,40,null);
		}
		else if(falling){
			g.drawImage(flySprites[0],(int)(tileX + x - width/2),(int)(tileY+y-height/2), 40,40,null);
		}
		else{
			g.drawImage(standingSprite,(int)(tileX + x - width/2),(int)(tileY+y-height/2), 40,40,null);
		}
		
		playerRect.setLocation((int)(tileX + x - width/2), (int)(tileY+y-height/2));
		if(playerRect.intersects(tileMap.getCoinRect()) && interacting){
			System.out.println("TOUCHING COIN");
			nextLevel();
		}
		if(playerRect.intersects(tileMap.getSpikeRect())){
			
			g.drawImage(deathSprites,(int)(tileX + x - width/2),(int)(tileY+y-height/2), 40,40,null);
			System.out.println("DIE");
			reset();
			
		}
		
		
		//System.out.println("Player : " + (int)(tileX + x - width/2) + ":" + (int)(tileY+y-height/2));
		
		
	}
	

	
	//getters and setter
	public void setLeft(boolean l){
		left = l;
	}
	public void setRight(boolean r){
		right = r;
	}
	public void setJumping(boolean j){
		if(!falling){
			jumping = j;
		}
	}
	
	public void setInteract(boolean b){
		interacting = b;
	}
	
	public void setGliding(boolean g){
		
		gliding = g;
		
	}
	
	public int getX(){
		return (int) x;
	}
	
	public int getY(){
		return (int)y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}

}