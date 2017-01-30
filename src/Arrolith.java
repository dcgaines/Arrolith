import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class Arrolith extends Game {
	//private enum STATES { STARTUP, MENU, INGAME, PAUSED; }
	private final byte STARTUP = 0;
	private final byte MENU = 1;
	private final byte INGAME = 2;
	private final byte PAUSED = 3;
	private byte currState;
	
	
	public Arrolith()
	{
		currState = STARTUP;
	}
	
	
	
	@Override
	public void tick(Graphics2D graphics, Input input, Sound sound) {
		
		/*    		CALCULATIONS			 */
		switch (currState)
		{
		case STARTUP:
			
			break;
		case MENU:
			
			break;
		case INGAME:
			
			break;
		case PAUSED:
			
			break;
		}
		
		/*  			DRAWING			   */
		switch (currState)
		{
		case STARTUP:
			
			break;
		case MENU:
			
			break;
		case INGAME:
			
			break;
		case PAUSED:
			
			break;
		}
		
	}

	@Override
	public Image cover() {
		try {
			return ImageIO.read(new File("cover.jpg"));
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		
		Arcadia.display(new Arcadia(new Game[] { new Arrolith() }));

	}
}
