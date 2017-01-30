	import arcadia.Button;
	import arcadia.Input;
public class Keys {
	private boolean [] keyPressed;
	private boolean [] keyBuffers;
	
	/**
	 * Place this before calculations to check key inputs
	 * @param input - input instance
	 */
	public void checkKeys(Input input) {
		if (input.pressed(Button.A))  keyPressed[0] = true;
		else {                        keyPressed[0] = false; keyBuffers[0] = true; }
		if (input.pressed(Button.B))  keyPressed[1] = true;
		else {                        keyPressed[1] = false; keyBuffers[1] = true; }
		if (input.pressed(Button.C))  keyPressed[2] = true;
		else {                        keyPressed[2] = false; keyBuffers[2] = true; }
		if (input.pressed(Button.U))  keyPressed[3] = true;
		else {                        keyPressed[3] = false; keyBuffers[3] = true; }
		if (input.pressed(Button.D))  keyPressed[4] = true;
		else {                        keyPressed[4] = false; keyBuffers[4] = true; }
		if (input.pressed(Button.L))  keyPressed[5] = true;
		else {                        keyPressed[5] = false; keyBuffers[5] = true; }
		if (input.pressed(Button.R))  keyPressed[6] = true;
		else {                        keyPressed[6] = false; keyBuffers[6] = true; }
		if (input.pressed(Button.S))  keyPressed[7] = true;
		else {                        keyPressed[7] = false; keyBuffers[7] = true; }
	}
	
	/**
	 * Use this after all game calculations to set keybuffer flags.
	 */
	public void setBuffers() {
		for (int i = 0; i < keyPressed.length; i++) {
			if (keyPressed[i]) keyBuffers[i] = false;
		}
	}
	
	public boolean getKey(int index){
		return keyPressed[index];
	}
	
	public Keys() {
		keyPressed = new boolean[8];
		keyBuffers = new boolean[8];
		for (int i = 0; i < keyBuffers.length; i++)
			keyBuffers[i] = true;
	}
		
}
