package WackyBird5;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

@SuppressWarnings("unused")
public class Bird extends Rectangle{
	
	private static final long serialVersionUID = 1L;
	
	private float speed = 1;
	public static boolean keyPressed = false;
	public boolean gameOver = false, restart = false, restartGame = false;
	public boolean flappingUp = false, flappingDown = false, birdDropping = false;
	private boolean objCollision = false;
	private BufferedImage flapUp;
	private BufferedImage flapDown;
	private BufferedImage flapDead;
	private static int birdsXVal, birdsYVal;
	private static float birdGravity;
	
	private ArrayList<Rectangle> pipes;
	
	private float gravity = 0.1f;
	
	public Bird(int x, int y, ArrayList<Rectangle> pipes) {
		setBounds(x,y,32,32);
		this.pipes = pipes;
		this.birdsXVal = x;
		this.birdsYVal = y;
		this.birdGravity = gravity;
		
		try {
			flapUp = Sprite.getSprite("images/flapUpBird.png");
			flapDown = Sprite.getSprite("images/flapDownBird.png");
			flapDead = Sprite.getSprite("images/flapDeadBird.png");
			
		}catch(IOException ex){
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		
	}
	
	public void update() {
		
		if(keyPressed) {
			speed = 2;
			y -= (speed);
			flappingUp = true;
			flappingDown = false;
		}else {
			y += speed;
			flappingUp = false;
			flappingDown = true;
			birdDropping = true;
		}
		 
		//check for collision
		for(int i = 0; i < pipes.size(); i++) {
			if(this.intersects(pipes.get(i))){
					birdDropping = true;
					Sound.birdDownMusic();
					objCollision = true;
					try {
						Thread.sleep(2700);
					}
					catch (InterruptedException ex) {}
					GameCore.state = GameCore.STATE.OVER;
					break;
			}
		}
		
		if(birdDropping) {
			speed += gravity;
			if(speed > 3) speed = 1;
		}
		
//		else {
//			speed -= gravity;
//		}
		
		if(y >= GameCore.HEIGHT-30) {
			gameOver = true;
			y = GameCore.HEIGHT - 20;
			speed = 0;
			objCollision = true;
		}
		
		System.out.println("Bird speed: " + speed);
		System.out.println("Score: " + GameCore.score);
		birdsXVal = x;
	}
	
	public void render(Graphics g) {
 		g.setColor(Color.red);
		
		if(flappingUp && !gameOver) { 
			g.drawImage(flapUp,x,y,width,height,null);
		}else if(flappingDown && !gameOver) {
		    g.drawImage(flapDown,x,y,width,height,null);
		}else {
			System.out.println("Bird collision: " + objCollision);
			g.drawImage(flapDead,x,y,width,height,null);
//			GameCore.state = GameCore.STATE.OVER;
//			try {
//				Thread.sleep(900);
//			}
//			catch (InterruptedException ex) {}
		}	
	}
	
	public static int getBirdX() {
		return birdsXVal;
	}

	public static int getBirdY() {
		return birdsYVal;
	}
	
	public static int getBirdGravity() {
		return (int)birdGravity;
	}
	
	public static Image getBird() throws IOException {
		return Sprite.getSprite("images/flapDownBird.png");
	}
	
	public void setBirdSpeed(float i) {
		speed = i;
	}

}
