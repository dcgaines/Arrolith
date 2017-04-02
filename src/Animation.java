import java.awt.Graphics2D;
import java.awt.Image;

public class Animation {
	
	int height, width;
	int frame = 0, totalFrames;
	int [] switchers;
	Image spriteSheet;
	int tFrame = 0;
	
	Animation(Image image, int height, int width, int[] switchers, boolean randomStartFrame) {
		this.spriteSheet = image;
		this.height = height;
		this.width = width;
		this.switchers = switchers;
		totalFrames = switchers.length - 1;
		if (randomStartFrame) {
			frame = (int) (Math.random() * 5);
		}
	}
	
	
	public void calculate() {
		tFrame++;
		if (switchers[frame] == tFrame) {
			frame++;
			if (frame > totalFrames) {
				frame = 0;
				tFrame = 0;
			}	
		}
	}
	
	void draw(Graphics2D g, int posX, int posY, int h, int w, boolean calculate) {
		if (calculate) calculate();
		g.drawImage(spriteSheet, posX, posY,
				posX + w, posY + h, 0, frame * height, width, (frame + 1) * height, null);
	}
}
