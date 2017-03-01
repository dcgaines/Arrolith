
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
	
	public InGame() {
		currPlayer = 0;
	}
	
	public void calculate(Keys keys) {
		switch (turnPhase) {
		case READY:
			
			break;
		case MOVE:
			break;
		case ACTION:
			break;
		case PASS:
			break;
		}
	}
	
	
	
	
}
