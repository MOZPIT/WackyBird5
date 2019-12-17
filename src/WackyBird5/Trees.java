package WackyBird5;

import java.awt.Graphics;

public class Trees {
	ImageLayer trees = new ImageLayer("images/dawntree.png", 0, GameCore.HEIGHT-45, 50);
	
	public void render(Graphics g) {
		trees.draw(g);	
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}
}
