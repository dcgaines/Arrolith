import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;


public class Graphics {
	
	static final int DIM = 22;
	Image tileStrip;
	
	public Graphics()
	{
		try {
			this.tileStrip = ImageIO.read(new File("tileStrip.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void drawMap(Graphics2D g) throws IOException
	{
		Map map = new Map(2);
		Tile tile;
		for (int i = 0; i < 40; i++)
			for (int j = 0; j < 87; j++)
			{
				tile = map.getTile(i, j);
				g.drawImage(tileStrip, i * DIM, j * DIM, (i + 1) * DIM, (j + 1) * DIM, tile.getType() * 32, 0, (tile.getType() + 1) * 32, (int) 32, null);
			}	
	}
	
}
