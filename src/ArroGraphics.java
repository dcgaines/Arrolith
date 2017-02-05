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
	
	static final int DIM = 22;
	
	Image tileStrip;
	Image sprites;
	
	public ArroGraphics()
	{
		try {
			this.tileStrip = ImageIO.read(new File("tileStrip.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void drawMap(Graphics2D g, Map map
			//, ArrayList<Player> players
			) throws IOException
	{
		Tile tile;
		for (int i = 0; i < 40; i++)
			for (int j = 0; j < 87; j++)
			{
				tile = map.getTile(i, j);
				g.drawImage(tileStrip, i * DIM, j * DIM, (i + 1) * DIM, (j + 1) * DIM, tile.getType() * 32, 0, (tile.getType() + 1) * 32, 32, null);
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
	public void drawHud(Graphics2D g, ArrayList<Player> players)
	{
		int posX, posY = 0;
		for (int i = 0; i < players.size(); i++)
			
	}
}

