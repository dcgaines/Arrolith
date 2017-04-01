import java.awt.Graphics2D;
import java.awt.Image;

public class Animation {
	
	int height, width;
	int frame = 0, totalFrames;
	int [] switchers;
	Image spriteSheet;
	int tFrame = 0;
	
	Animation(Image image, int height, int width, int[] switchers) {
		this.spriteSheet = image;
		this.height = height;
		this.width = width;
		this.switchers = switchers;
		totalFrames = switchers.length - 1;
	}
	
	public void calculate() {
		tFrame++;
		for (int e : switchers)
			if (tFrame == e) {
				frame++;
				if (frame > totalFrames) {
					frame = 0;
					tFrame = 0;
				}
					
			}
	}
	
	void draw(Graphics2D g, int posX, int posY, int h, int w, boolean calculate) {
		if (calculate) calculate();
		System.out.println(frame);
		g.drawImage(spriteSheet, posX, posY,
				posX + w, posY + h, 0, frame * height, width, (frame + 1) * height, null);
	}
}
