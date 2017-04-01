import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import arcadia.Game;

public class ArroGraphics {
	
	double DIM = 100; //dimension of a space when drawn on a 1080p screen
	
	Image tileStrip;
	Image sprites;
	Image portraits;
	Image splash;
	Animation monster;
	private Font alegreya;
	
	double offsetX, offsetY;
	float multiplyer = (float) (Game.HEIGHT / 1080.);
	
	public ArroGraphics() {
		try {
			//Loads images into memory
			this.tileStrip = ImageIO.read(new File("tileStrip.png"));
			this.sprites = ImageIO.read(new File("sprites.png"));
			this.splash = ImageIO.read(new File("splash.png"));
			this.monster = new Animation(ImageIO.read(new File("monster.png")), 32, 32, new int[]{60, 65, 70, 75});
			this.portraits = ImageIO.read(new File("portraits.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			alegreya = Font.createFont(Font.TRUETYPE_FONT, new File("Alegreya.ttf"))
					.deriveFont(100 * (float) multiplyer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Centers the map around the current player
	public void centerMap(Graphics2D g, Map map, Player player) {
		offsetX = ((1920 - DIM) / 2) - (player.getX() * DIM);
		offsetY = ((1080 - DIM) / 2) - (player.getY() * DIM);
	}
	
	//Draws the map and players
	public void drawMap(Graphics2D g, Map map, ArrayList<Player> players, ArrayList<Monster> monsters)
	{
		Tile tile; //temporary tile space
		for (int i = 0; i < map.getHeight(); i++)
			for (int j = 0; j < map.getWidth(); j++)
			{
				tile = map.getTile(i, j);
				g.drawImage(tileStrip, 
						(int) ((j * DIM + offsetX) * multiplyer), 
						(int) ((i * DIM + offsetY)* multiplyer), 
						(int) (((j + 1) * DIM + offsetX) * multiplyer),
						(int) (((i + 1) * DIM + offsetY)* multiplyer),
						(int) tile.getType() * 32, 
						0, (tile.getType() + 1) * 32, 32, null);
			}
		drawMonsters(g, monsters);
		drawPlayers(g, players);
	}
	private int posX(double x) { return (int) ((x + offsetX) * multiplyer); }
	private int posY(double y) { return (int) ((y + offsetY) * multiplyer); }
	//Draws the players on the map
	private void drawPlayers(Graphics2D g, ArrayList<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			g.drawImage(sprites, 
					posX(players.get(i).getX() * DIM),
					posY(players.get(i).getY() * DIM),
					posX((players.get(i).getX() + 1) * DIM),
					posY((players.get(i).getY() + 1) * DIM),
			 		players.get(i).getType() * 32, 0,  (players.get(i).getType() + 1) * 32, 32, null);
		}
	}
	
	public void drawMonsters(Graphics2D g, ArrayList<Monster> monsters) {
		monster.calculate();
		for (int i = 0; i < monsters.size(); i++) {
			Monster temp = monsters.get(i);
			monster.draw(g, posX(temp.getX() * DIM), posY(temp.getY() * DIM), (int) (DIM * multiplyer), (int) (DIM * multiplyer), false);
		}
		
	}
	//Draws the HUD on the screen
	public void drawHud(Graphics2D g, ArrayList<Player> players, byte actionsUsed, int tFrame, byte currPlayer) {
		g.setColor(Color.BLACK);
		g.fillRect(0, multiplyer(800), multiplyer(1920), multiplyer(280)); 
		g.setColor(Color.WHITE);
		g.setFont(alegreya);
		g.setStroke(new BasicStroke(multiplyer(8)));
		g.drawRect(0, multiplyer(800), multiplyer(1920), multiplyer(280));
//		g.drawString("Actions Left: " + Integer.toString(Update.players.get(currPlayer).getAP() - actionsUsed), 119 * multiplyer, 132 * multiplyer);
//		String timeLeft = String.format("%.2f", (420 - tFrame) / 30.);
//		g.drawString(timeLeft, (1568 * multiplyer), (132 * multiplyer));
		for (int i = 0; i < players.size(); i++) {
			g.drawImage(portraits, 
					multiplyer(i * 480 + 10),
					multiplyer(811),
					multiplyer(i * 480 + 266),
					multiplyer(1067),
					(players.get(i).getType()) * 64, 
					0,
					(players.get(i).getType() + 1) * 64,
					64, null);
		}
	}
	private int multiplyer(double num) {
		return (int) (num * multiplyer);
	}
	//Zooms in on the map
	public void zoomIn(double speed) {
		DIM += speed;
	}
	
	//Zooms out on the map
	public void zoomOut(double speed) {
		DIM -= speed;
	}
	
	//Moves left on the map
	public void moveLeft(double speed) {
		offsetX += speed;
	}
	
	//Moves right on the map
	public void moveRight(double speed) {
		offsetX -= speed;
	}
	
	//Draws the menu
	//TODO: Change when menu has been designed
	public void drawMenu(Graphics2D g, int menuState) {
		g.fillRect(0, 0, multiplyer(1920), multiplyer(1080)); // allows a clean reset of the image
		g.drawImage(splash,multiplyer(-233), multiplyer(-355), multiplyer(2167), multiplyer(996), 0,0, 4106, 2310, null);
		g.setColor(Color.WHITE);
		g.setFont(alegreya);
		switch (menuState)
		{
		case 0:
			g.drawString(">  Play  <", multiplyer(796), multiplyer(762));
			g.drawString("Sound", multiplyer(827), multiplyer(882));
			break;
		case 4: //sound
			g.drawString("Play", multiplyer(865), multiplyer(762));
			g.drawString(">  Sound  <", multiplyer(759), multiplyer(882));
			break;
		case 1: case 2: case 3: // 2 players
			g.drawString(Integer.toString(menuState + 1), multiplyer(935), multiplyer(762));
			if (menuState == 1 || menuState == 2) g.drawString(">", multiplyer(1004), multiplyer(762));
			if (menuState == 2 || menuState == 3) g.drawString("<", multiplyer(875),  multiplyer(762));
			g.drawString("Players", multiplyer(801), multiplyer(882));
			break;
		}
	}
}

