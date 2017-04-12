import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Menu {
	private final byte PLAY = 0;
	private final byte CHARACTERS = 1;
	private final byte SOUND = 4;
	public byte state;
	private File prefs;
	private int selChar = 0;
	private int currPlayer = 0;
	
	public Menu() {
		state = PLAY;
		
	}
	
	private int setup(Keys keys) {
		if (keys.isKeyPressed(keys.UP)) {
			selChar++;
			if (selChar > 3)
				selChar = 0;
		}
		else if (keys.isKeyPressed(keys.DOWN)) {
			selChar--;
			if (selChar < 0)
				selChar = 3;
		}
		else if (keys.isKeyPressed(keys.A)) {
			switch (selChar) {
			case 0: Update.players.add(new Juggernaut("Player " + (currPlayer + 1))); break;
			case 1: Update.players.add(new Savant("Player " + (currPlayer + 1))); break;
			case 2: Update.players.add(new Mason("Player " + (currPlayer + 1)));break;
			case 3: Update.players.add(new Operative("Player " + (currPlayer + 1))); break;
			}
			currPlayer++;
			selChar = 0;
			if (currPlayer > 3)
				return 4;
		}
		else if (keys.isKeyPressed(keys.B)) {
			if (currPlayer == 0) {
				state = PLAY;
				selChar = 0;
			}
			else {
				Update.players.remove(currPlayer - 1);
				currPlayer--;
				selChar = 0;
			}
			
		}
		if (keys.isKeyPressed(keys.C) && currPlayer > 1)
			return currPlayer;
		return -1;
	}
	
	//Returns false if moving into ingame
	public int calculate(Keys keys) {
		switch (state)
		{
		case PLAY:
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A))
				state = CHARACTERS;
			else if (keys.getKey(keys.DOWN) && keys.getBuffer(keys.DOWN))
				state = SOUND;
			break;
		case SOUND:
			if (keys.getKey(keys.UP) && keys.getBuffer(keys.DOWN))
				state = PLAY;
			break;
		case CHARACTERS:
			int numOfPlayers = setup(keys);
			if (numOfPlayers > 0)
				return numOfPlayers;
			break;
		}
		return -1;
	}
	public void draw(Graphics2D g, ArroGraphics graphics) {
		graphics.drawMenu(g, state, currPlayer, selChar);
	}
}
