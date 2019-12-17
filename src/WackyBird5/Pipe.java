package WackyBird5;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.Renderer;

public class Pipe extends Sprite{

	public ArrayList<Rectangle> pipes;
	private int time;
	private int currentTime = 0;
	public Image pipeImage= new ImageIcon("pipe.png").getImage();
//	public ArrayList<Image> newPipes;
	
	private Random rand = new Random(300);
	public static int speed = 2;
	

	public Pipe(int time) {
		pipes = new ArrayList<Rectangle>();
//		@SuppressWarnings("unused")
//		ArrayList<Image> newPipes = new ArrayList<Image>();
		this.time = time;
	}
	
	public void update() {
		currentTime++;
		if(currentTime == time) {
			currentTime = 0;
			
			int space = 280;
			int width = 150;
			int height = rand.nextInt(GameCore.HEIGHT/2);
			
			pipes.add(new Rectangle(GameCore.WIDTH + width + pipes.size() * 500, GameCore.HEIGHT - height, width, height));
			pipes.add(new Rectangle(GameCore.WIDTH + width + (pipes.size() - 1) * 500, 0, width, GameCore.HEIGHT - height - space));
		}
		
		for(int i = 0; i < pipes.size(); i++) {
			Rectangle rect = pipes.get(i);
			rect.x -= speed;
			
			if(rect.x + rect.width <= 0) {
				pipes.remove(i--);
				GameCore.score += 0.5;
				Sound.test.play();
						
				continue;
			}
		}
	
	}


	public void render(Graphics g) {
		g.setColor(Color.GREEN);
		
		for(int i = 0; i < pipes.size(); i++) {
			Rectangle rect = pipes.get(i);
			g.setColor(Color.GREEN);
		    g.draw3DRect(rect.x, rect.y, rect.width, rect.height, true);
		    g.fill3DRect(rect.x, rect.y, rect.width, rect.height, true);
		}
	
	}
	
	public static void changePipeSpeed(int s) {
		speed = s;
	}
}
