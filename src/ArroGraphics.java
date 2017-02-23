import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class ArroGraphics {
	
	double DIM = 100;
	
	Image tileStrip;
	Image sprites;
	Image splash;
	double offsetX, offsetY;
	float multiplyer = (float) (Game.HEIGHT / 1080.);
	
	public ArroGraphics() {
		try {
			this.tileStrip = ImageIO.read(new File("tileStrip.png"));
			this.sprites = ImageIO.read(new File("sprites.png"));
			this.splash = ImageIO.read(new File("splash.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void centerMap(Graphics2D g, Map map, Player player) {
		offsetX = ((1920 - DIM) / 2) - (player.getX() * DIM);
		offsetY = ((1080 - DIM) / 2) - (player.getY() * DIM);
	}
	public void drawMap(Graphics2D g, Map map, ArrayList<Player> players)
	{
		Tile tile;
		for (int i = 0; i < map.getHeight(); i++)
			for (int j = 0; j < map.getWidth(); j++)
			{
				//System.out.println(multiplyer);
				tile = map.getTile(i, j);
				g.drawImage(tileStrip, 
						(int) ((j * DIM + offsetX) * multiplyer), 
						(int) ((i * DIM + offsetY)* multiplyer), 
						(int) (((j + 1) * DIM + offsetX) * multiplyer),
						(int) (((i + 1) * DIM + offsetY)* multiplyer),
						(int) tile.getType() * 32, 
						0, (tile.getType() + 1) * 32, 32, null);
			}
		drawPlayers(g, players);
		//drawPlayers(players);
	}
	private void drawPlayers(Graphics2D g, ArrayList<Player> players)
	{
		for (int i = 0; i < players.size(); i++)
		{
			//TODO	replace player with necessary methods
			g.drawImage(sprites, 
					(int) ((players.get(i).getX() * DIM + offsetX) * multiplyer),
					(int) ((players.get(i).getY() * DIM + offsetY) * multiplyer), 
					(int) (((players.get(i).getX() + 1) * DIM + offsetX) * multiplyer),
					(int) (((players.get(i).getY() + 1) * DIM + offsetY)* multiplyer),
			 		players.get(i).getType() * 32, 0,  (players.get(i).getType() + 1) * 32, 32, null);
		}
	}
	public void drawHud(Graphics2D g, ArrayList<Player> players, byte actionsUsed, int tFrame) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int) (1920 * multiplyer), (int) (200 * multiplyer)); 
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(15 * multiplyer));
		g.drawRect(0, 0, (int) (1920 * multiplyer), (int) (200 * multiplyer));
		g.drawString("Moves Rem: " + Integer.toString(14 - actionsUsed), 119 * multiplyer, 132 * multiplyer);
		String timeLeft = Integer.toString((600 - tFrame) / 30);
		g.drawString(timeLeft, (1568 * multiplyer), (132 * multiplyer));
	}
	public void zoomIn(double speed) {
		DIM += speed;
	}
	public void zoomOut(double speed) {
		DIM -= speed;
	}
	public void moveLeft(double speed) {
		offsetX += speed;
	}
	public void moveRight(double speed) {
		offsetX -= speed;
	}
	public void drawMenu(Graphics2D g, int menuState) {
		g.fillRect(0, 0, (int) (1920 * multiplyer), (int) (1080 * multiplyer)); // allows a clean reset of the image
		g.drawImage(splash,(int)(-233 * multiplyer), (int)(-355 * multiplyer), (int)(2167 * multiplyer), (int)(996 * multiplyer), 0,0,  4106, 2310, null);
		g.setColor(Color.WHITE);
		switch (menuState)
		{
		case 0:
			g.drawString(">  Play  <", 796 * multiplyer, 762 * multiplyer);
			g.drawString("Sound", 827 * multiplyer, 882 * multiplyer);
			break;
		case 4: //sound
			g.drawString("Play", 865 * multiplyer, 762 * multiplyer);
			g.drawString(">  Sound  <", 759 * multiplyer, 882 * multiplyer);
			break;
		case 1: case 2: case 3: // 2 players
			g.drawString(Integer.toString(menuState + 1), 935 * multiplyer, 762 * multiplyer);
			if (menuState == 1 || menuState == 2) g.drawString(">", 1004 * multiplyer, 762 * multiplyer);
			if (menuState == 2 || menuState == 3) g.drawString("<", 875 * multiplyer, 762 * multiplyer);
			g.drawString("Players", 801 * multiplyer, 882 * multiplyer);
			break;
		}
		//g.drawString("Play", 863 * multiplyer, 762 * multiplyer);
		//g.drawString("Sound", 827 * multiplyer, 882 * multiplyer);
		
	}
}

