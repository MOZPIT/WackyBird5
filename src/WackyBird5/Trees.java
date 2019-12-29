package WackyBird5;

import java.awt.Graphics;


public class Trees {
	ImageLayer trees = new ImageLayer("images/dawntree.png", 0, GameCore.HEIGHT+45, GameCore.HEIGHT/2);

	public void update() {
	    int rand = (int) Math.random()*10;
		trees = new ImageLayer("images/dawntree.png", GameCore.HEIGHT/2 + rand, GameCore.HEIGHT/2, rand);
		
	}
	
	public void render(Graphics g) {
		trees.draw(g);
	}
}
