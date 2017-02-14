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
	
	double DIM = 90;
	
	Image tileStrip;
	Image sprites;
	double offsetX, offsetY;
	double multiplyer = (double) Game.HEIGHT / 1080;
	
	public ArroGraphics() {
		try {
			this.tileStrip = ImageIO.read(new File("tileStrip.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void centerMap(Graphics2D g, Map map, Player player) {
		offsetX = ((1920 - DIM) / 2) - (player.getX() * DIM);
		offsetY = ((1080 - DIM) / 2) - (player.getY() * DIM);
	}
	public void drawMap(Graphics2D g, Map map
			//, ArrayList<Player> players
			) throws IOException
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
		//drawPlayers(players);
	}
	private void drawPlayers(Graphics2D g, ArrayList<Player> players)
	{
		for (int i = 0; i < players.size(); i++)
		{
			//TODO	replace player with necessary methods
			g.drawImage(sprites, 
					players.get(i).getX() * DIM, players.get(i).getX() * DIM, 
					(players.get(i).getX() + 1) * DIM, (players.get(i).getX() + 1) * DIM,
					players.get(i).getType() * 32, 0,  (players.get(i).getType() + 1) * 32, 32, null);
		}
	}
	public void drawHud(Graphics2D g, ArrayList<Player> players) {
		int posX, posY = 0;
		for (int i = 0; i < players.size(); i++)		
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
}

