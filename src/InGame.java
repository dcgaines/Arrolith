import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.Random;

/**
 * This class is used for making update methods for 
 * @author tyeic
 *
 */
public class InGame {

	
	protected Map map;
	
	private byte turnPhase;
	private final byte READY = 0;
	private final byte MOVE = 1;
	private final byte ACTION = 2;
	private final byte PASS = 3;
	public byte currPlayer;
	public byte actionsUsed;
	
	public InGame() {
		currPlayer = 0;
		actionsUsed = 0;
	}
	
	private boolean checkMovement(Keys keys) {
		//note - movement only occurs when first conditions
		return ((keys.isKeyPressed(keys.DOWN)  && Update.players.get(currPlayer).walkDown(map))    //checks walking down
			||  (keys.isKeyPressed(keys.UP)    && Update.players.get(currPlayer).walkUp(map))      //checks walking up
		    ||  (keys.isKeyPressed(keys.LEFT)  && Update.players.get(currPlayer).walkLeft(map))    //checks walking left
		    || 	(keys.isKeyPressed(keys.RIGHT) && Update.players.get(currPlayer).walkRight(map))); //checks walking right
	}
	
	
	public void init(int numPlayers) {
		//Generate map
		try { map = new Map(numPlayers); }
		catch (IOException e1) { e1.printStackTrace(); }
		//TODO: Generate players
		Random rand = new Random();
		for (byte i = 0; i < numPlayers; i++) {
			int n = rand.nextInt(4) + 1;
			switch (n) {
			case 1: Update.players.add(new Savant("")); break;
			case 2: Update.players.add(new Mason("")); break;
			case 3: Update.players.add(new Juggernaut("")); break;
			case 4: Update.players.add(new Operative("")); break;
			}
			Update.players.get(i).setInitPos(i, map);
		}
	}
	
	
	public void calculate(Keys keys, int tFrame) {
		switch (turnPhase) {
		case READY:
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A))
				turnPhase = MOVE;
			break;
		case MOVE:
			if (actionsUsed < Update.players.get(currPlayer).getMaxAP() && tFrame < 420) {
				if (checkMovement(keys)) //if any key is pressed whatsoever
					actionsUsed++;
				Update.incFrame();
				
				break;
			}
		case ACTION:
		case PASS:
			currPlayer++;
			if (currPlayer >= Update.players.size())
				currPlayer = 0;
			actionsUsed = 0;
			Update.resetFrame();
			turnPhase = READY;
			break;
		}
	}
	
	public void draw(Graphics2D g, ArroGraphics graphics) {
		
		graphics.centerMap(g, map, Update.players.get(currPlayer));
		graphics.drawMap(g, map, Update.players);
		graphics.drawHud(g, Update.players, actionsUsed, Update.getFrame(), currPlayer);
		
		if (turnPhase == READY) {
			g.setColor(Color.BLACK);
			g.fillRect((int) (760 * graphics.multiplyer), (int) (340 * graphics.multiplyer), 
				(int) (400 * graphics.multiplyer), (int) (400 * graphics.multiplyer));
			g.setColor(Color.WHITE);
			g.setStroke(new BasicStroke(15 * graphics.multiplyer));
			g.drawRect((int) (760 * graphics.multiplyer), (int) (340 * graphics.multiplyer), 
				(int) (400 * graphics.multiplyer), (int) (400 * graphics.multiplyer));
			g.drawString("Press A", 800 * graphics.multiplyer, 504 * graphics.multiplyer);
			g.drawString("to start.", 800 * graphics.multiplyer, 640 * graphics.multiplyer);
		}
	}
	
	
	
}
