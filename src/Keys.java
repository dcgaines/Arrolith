	import arcadia.Arcadia;
import arcadia.Button;
	import arcadia.Input;
public class Keys {
	public final byte A = 0;
	public final byte B = 1;
	public final byte C = 2;
	public final byte UP = 3;
	public final byte DOWN = 4;
	public final byte LEFT = 5;
	public final byte RIGHT = 6;
	public final byte S = 7;
	
	private boolean [] keyPressed;
	private boolean [] keyBuffers;
	
	
	
	/**
	 * Place this before calculations to check key inputs
	 * @param input - input instance
	 */
	public void checkKeys(Input input) {
		checkKey(input, Button.A, 0);
		checkKey(input, Button.B, 1);
		checkKey(input, Button.C, 2);
		checkKey(input, Button.U, 3);
		checkKey(input, Button.D, 4);
		checkKey(input, Button.L, 5);
		checkKey(input, Button.R, 6);
		checkKey(input, Button.S, 7);
	}
	public boolean isKeyPressed(int key) {
		return getKey(key) && getBuffer(key);
	}
	public boolean isKeyHeld(int key) {
		return getKey(key);
	}
	private void checkKey(Input input, Button button, int index) {
		if (input.pressed(button)) {
			keyPressed[index] = true;
		}
		else {
			keyPressed[index] = false;
			keyBuffers[index] = true;
		}
	}
	
	/**
	 * Use this after all game calculations to set keybuffer flags.
	 */
	public void setBuffers() {
		for (int i = 0; i < keyPressed.length; i++) {
			if (keyPressed[i]) keyBuffers[i] = false;
		}
	}
	
	public void setBuffer(int index) {
		keyBuffers[index] = false;
	}
	public boolean getKey(int index){
		return keyPressed[index];
	}
	
	public boolean getBuffer(int index) {
		return keyBuffers[index];
	}
	public Keys() {
		keyPressed = new boolean[8];
		keyBuffers = new boolean[8];
		for (int i = 0; i < keyBuffers.length; i++)
			keyBuffers[i] = true;
	}
		
}
