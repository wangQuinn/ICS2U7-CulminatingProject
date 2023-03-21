/*
Name :Quinn Wang
Teacher: Mrs.Strelkovska
Code: ICS207
Date: January 18th 2023
Class : TileMap class
*/
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.imageio.*;
import java.awt.image.*;

public class TileMap{
	private int x,y;
	private int tileSize;
	
	private int[][]map;
	private int mapWidth;
	private int mapHeight;
	
	private int coinX;
	private int coinY;
	private int spikeX, spikeY;
	
	private BufferedImage[] levelSprites;
	private BufferedImage coin;
	private Rectangle coinRect;
	
	private BufferedImage spikes;
	private Rectangle spikeRect;
	
	private BufferedImage a, d, e, up;
	
	private boolean isLevelOne;
	
	//constuctor
	public TileMap(String file, int tileSize){
		this.tileSize = tileSize;
		isLevelOne = true;
		
		try{
			Scanner scFile = new Scanner(new File(file));
			mapWidth = scFile.nextInt();
			mapHeight = scFile.nextInt();scFile.nextLine();
			
			map = new int[mapHeight][mapWidth];
			
			for(int i = 0; i  < mapHeight; i++){
				String line = scFile.nextLine();
				String[] characters = line.split(" ");
				for(int j =0; j < mapWidth; j++){
					map[i][j] = Integer.parseInt(characters[j]);
				}
			}
		}
		catch(Exception e){
			System.out.print(":TILE MAP ERROR");
			e.printStackTrace();
		}
		
		getTiles();
	}
	
	//getting the tiles from the file
	private void getTiles(){
		try{
			BufferedImage levelSprite = ImageIO.read(new File("tiles.png"));
			levelSprites = new BufferedImage[8];
			for(int i=0; i < 8; i++){
				System.out.println(i);
				levelSprites[i] = levelSprite.getSubimage(i * 10, 0, 10,10);
			}
			
			spikes = ImageIO.read(new File("spikes.png"));
			a = ImageIO.read(new File("a.png"));
			d = ImageIO.read(new File("d.png"));
			e = ImageIO.read(new File("e.png"));
			up = ImageIO.read(new File("up.png"));
		}
		catch(Exception e){
			System.out.print("picture error level");
		}
  }
  
	
	public void switchLevels(String file){
		
		try{
			Scanner scFile = new Scanner(new File(file));
			mapWidth = scFile.nextInt();
			mapHeight = scFile.nextInt();scFile.nextLine();
			
			map = new int[mapHeight][mapWidth];
			
			for(int i = 0; i  < mapHeight; i++){
				String line = scFile.nextLine();
				String[] characters = line.split(" ");
				for(int j =0; j < mapWidth; j++){
					map[i][j] = Integer.parseInt(characters[j]);
				}
			}
			
		}
		catch(Exception e){
			System.out.print(":TILE MAP ERROR");
			e.printStackTrace();
		}
		
	}
	//other functions
	public void update(){
	
	}
	public void setSpike(int Sx, int Sy){
		spikeX = Sx;
		spikeY = Sy;
		
		spikeRect = new Rectangle(spikeX, spikeY, 40, 40);
	}
	
	public void setCoin(int Cx, int Cy){ // reset every level
		coinX = Cx;
		coinY = Cy;
		
		coinRect = new Rectangle(coinX, coinY, 40,40);
		
	}
	public void draw(Graphics g){
		for(int i=0;i < mapHeight; i++){
			for(int j =0;j < mapWidth; j++){
				int tileType = map[i][j];
				g.drawImage(levelSprites[tileType], tileSize *j + (int)x, tileSize*i + (int)y, tileSize,tileSize, null);
				
			
			}
		}
		
		coinRect.setLocation(coinX + (int)x, coinY + (int)y); //updating coin Rectangle
		g.drawImage(levelSprites[7], coinX + (int)x , coinY + (int)y, 40,40, null);
		
		spikeRect.setLocation(spikeX + (int)x, spikeY + (int)y); // updating spike Rectangle
		g.drawImage(spikes, spikeX+ (int)x, spikeY + (int)y, 40 , 40, null);
		//System.out.println("Coin : " + (coinX + (int)x ) + ":" + (coinY + (int)y));
		
		if(isLevelOne){
			g.drawImage(a, 85 + (int)x, 300 + (int)y, 40, 40, null);
			g.drawImage(d, 180 + (int)x, 300 + (int)y, 40, 40, null);
			g.drawImage(up, 400 + (int)x, 360 + (int)y, 40, 40, null);
			g.drawImage(e, 600 + (int)x, 200 + (int)y, 40, 40, null);
		}
		
		
	}
	
	public void setObjects(int levelNum){
		switch(levelNum){
			case 1:
				spikeX = 400; spikeY = 400;
				coinX = 300; coinY = 400; break;
			case 2:
			System.out.println("Level2 ");
				isLevelOne = false;
				spikeX = 400; spikeY = 480;
				coinX = 400; coinY = 75; break;
			case 3: 
			System.out.println("Level3 ");
				spikeX = 640; spikeY = 960;
				coinX = 400; coinY = 120; break;
			case 4:
			System.out.println("Level4 ");
				spikeX = 120; spikeY = 210; 
				coinX = 360; coinY = 40; break;
			case 5:
			System.out.println("Level5 ");
				coinX = 210; coinY = 40; break;
		}
	}
	
	
	//getters setters
	public Rectangle getSpikeRect(){
		return spikeRect;
	}
	public Rectangle getCoinRect(){
		return coinRect;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public int getColTile(int x){
		return x / tileSize;
	}
	public int getRowTile(int y){
		return y/ tileSize;
	}
	
	public int getTile(int row, int col){
		return map[row][col];
	}
	
	public int getTileSize(){
		return tileSize;
	}
	
}