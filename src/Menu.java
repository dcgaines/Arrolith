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
	//Returns false if moving into ingame
	public boolean calculate(Keys keys, Map map) {
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
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A)) {
				//Generate map
				try { map = new Map(2); }
				catch (IOException e1) { e1.printStackTrace(); }
				//TODO: Generate players
				Random rand = new Random();
//				for (byte i = 0; i < 2; i++) {
//					int n = rand.nextInt(4) + 1;
//					switch (n) {
//					case 1: players.add(new Savant("")); break;
//					case 2: players.add(new Mason("")); break;
//					case 3: players.add(new Juggernaut("")); break;
//					case 4: players.add(new Operative("")); break;
//					}
//					players.get(i).setInitPos(i, map);
//				}
				return false;
			}
			else if (keys.getKey(keys.RIGHT) && keys.getBuffer(keys.RIGHT)) 
				state = THREEPLAYERS;
			else if (keys.getKey(keys.B) && keys.getBuffer(keys.B))
				state = PLAY;
			break;
		case THREEPLAYERS:
			if (keys.getKey(keys.RIGHT) && keys.getBuffer(keys.RIGHT)) 
				state = FOURPLAYERS;
			else if (keys.getKey(keys.LEFT) && keys.getBuffer(keys.LEFT))
				state = TWOPLAYERS;
			else if (keys.getKey(keys.B) && keys.getBuffer(keys.B))
				state = PLAY;
			break;
		case FOURPLAYERS:
			if (keys.getKey(keys.LEFT) && keys.getBuffer(keys.LEFT))
				state = THREEPLAYERS;
			else if (keys.getKey(keys.B) && keys.getBuffer(keys.B))
				state = PLAY;
			break;
		}
		return true;
	}
}
