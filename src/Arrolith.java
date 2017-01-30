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
	private final int STARTUP = 0;
	private final int MENU = 0;
	private final int 
	private int currState;
	
	
	public Arrolith()
	{
		currState = STARTUP;
		
	}
	
	
	
	@Override
	public void tick(Graphics2D graphics, Input input, Sound sound) {
		
		//Calculations
		switch (currState)
		{
		case STATES.STARTUP.ordinal():
			
		}
		
		
		// Drawing methods placed below
		switch (currState)
		{
		
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
