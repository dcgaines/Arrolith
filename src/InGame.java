import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class is used for making update methods for 
 * @author tyeic
 *
 */
public class InGame {

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
	
	public void calculate(Keys keys) {
		switch (turnPhase) {
		case READY:
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A))
				turnPhase = MOVE;
			break;
		case MOVE:
			if (actionsUsed < 20)
			if (Update.players.get(currPlayer).walkDown(Update.map))
				actionsUsed++;
			break;
		case ACTION:
			break;
		case PASS:
			break;
		}
	}
	
	public void draw(Graphics2D g, ArroGraphics graphics) {
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
