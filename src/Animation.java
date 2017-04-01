import java.awt.Graphics2D;
import java.awt.Image;

public class Animation {
	
	int height, width;
	int frame = 0, totalFrames;
	int [] switchers;
	Image spriteSheet;
	
	Animation(Image image, int height, int width, int[] switchers) {
		this.spriteSheet = image;
		this.height = height;
		this.width = width;
		this.switchers = switchers;
		totalFrames = switchers.length;
	}
	
	void calculate(int tFrame) {
		for (int e : switchers)
			if (tFrame == e) {
				frame++;
				if (frame > totalFrames)
					frame = 0;
			}
	}
	
	void draw(Graphics2D g, int posX, int posY, int h, int w) {
		g.drawImage(spriteSheet, posX, posY,
				posX + width, posX + height, 0, frame * height, width, (frame + 1) * height, null);
	}
}
