import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Menu {
	private final byte PLAY = 0;
	private final byte TWOPLAYERS = 1;
	private final byte THREEPLAYERS = 2;
	private final byte FOURPLAYERS = 3;
	private final byte SOUND = 4;
	public byte state;
	private File prefs;
	
	public Menu() {
		state = PLAY;
		
	}
	
	private void setup() {
		
	}
	
	//Returns false if moving into ingame
	public int calculate(Keys keys) {
		switch (state)
		{
		case PLAY:
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A))
				state = TWOPLAYERS;
			else if (keys.getKey(keys.DOWN) && keys.getBuffer(keys.DOWN))
				state = SOUND;
			break;
		case SOUND:
			if (keys.getKey(keys.UP) && keys.getBuffer(keys.DOWN))
				state = PLAY;
			break;
		case TWOPLAYERS:
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A))
				return 2;
			else if (keys.getKey(keys.RIGHT) && keys.getBuffer(keys.RIGHT)) 
				state = THREEPLAYERS;
			else if (keys.getKey(keys.B) && keys.getBuffer(keys.B))
				state = PLAY;
			break;
		case THREEPLAYERS:
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A))
				return 3;
			if (keys.getKey(keys.RIGHT) && keys.getBuffer(keys.RIGHT)) 
				state = FOURPLAYERS;
			else if (keys.getKey(keys.LEFT) && keys.getBuffer(keys.LEFT))
				state = TWOPLAYERS;
			else if (keys.getKey(keys.B) && keys.getBuffer(keys.B))
				state = PLAY;
			break;
		case FOURPLAYERS:
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A))
				return 4;
			if (keys.getKey(keys.LEFT) && keys.getBuffer(keys.LEFT))
				state = THREEPLAYERS;
			else if (keys.getKey(keys.B) && keys.getBuffer(keys.B))
				state = PLAY;
			break;
		}
		return -1;
	}
	public void draw(Graphics2D g, ArroGraphics graphics) {
		graphics.drawMenu(g, state);
	}
}
