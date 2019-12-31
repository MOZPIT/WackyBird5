
package WackyBird5;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Renderer;

public class GameCore extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 960, HEIGHT = 800;
	private Thread thread;

	//set game state
	public static enum STATE{
		GAME,
		OVER,
		WIN,
		MENU
	};
	public static STATE state = STATE.MENU;

	private Menu menu = new Menu();
	private boolean started = false;
	private static boolean gameOver = true;
	public static double score = 0;
	public static int keyTouched;
	
	public static Pipe pipe;
	public static Bird bird;
	public static BackDrop backdrop;
	public static Score playerScore;
	public static Floor gameFloor;
//	public static Trees trees;
	public static Snow snowFlake;
	public static Eagle eagle;
	
	//Constructor
	public GameCore() {
		Dimension d = new Dimension(GameCore.WIDTH, GameCore.HEIGHT);
		Camera.setLocation(100, 0);
		
		requestFocus();
		setPreferredSize(d);
		this.addKeyListener(new InputManager());
		this.setIgnoreRepaint(true);
		
		newGame();
	}
	
	public synchronized void start() {		
		if(started && state == STATE.GAME) return;
			started = true;
			thread = new Thread(this);
			thread.start();
	}
	
	public synchronized void stop() {
		if(!started  && state == STATE.MENU) return;
		started = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		JFrame jFrame = new JFrame();
		GameCore game = new GameCore();
		jFrame.add(game);
		
		jFrame.setResizable(false);
		jFrame.pack();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setTitle("Wacky Bird");
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
		jFrame.setFocusable(true);

	
		Object Sound;
		Sound = new Sound();
		((Sound) Sound).playBackgroundMusic();
		
		game.createBufferStrategy(2);
		
		game.start();

	}

	@Override
	public void run() {
		int fps = 0;
		long lastUpdated = System.nanoTime();
		double timer = System.currentTimeMillis();
		double delta = 0;
		double nanosecs = 1000000000 / 60;
		
		/* game loop */
		while(started) {
			long now = System.nanoTime();
			delta += (now - lastUpdated) / nanosecs;
			lastUpdated = now;
			
			while(delta >= 1) {
				update();
				try {
					render();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fps++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + fps);
				System.out.println("isGameOver: " + gameOver);
				System.out.println("Game State: " + state);
				System.out.println("KeyTouched: " + keyTouched);
			}
			
			if(score < 4) {
				backdrop = new BackDrop(1);
				pipe.changePipeSpeed(6);
			}else if(score > 4 && score < 10){
				backdrop = new BackDrop(2);
				pipe.changePipeSpeed(9);
			}else if(score > 11 && score < 20){
//				eagle.chase(bird);
				backdrop = new BackDrop(3);
				pipe.changePipeSpeed(12);
			}else if(score == 20) {
				state = STATE.WIN;
			}
			
		}
		
		stop();
		
	}
	
	private void update() {
		if(state == STATE.GAME){
			backdrop.update();
			pipe.update();
			bird.update();
//            trees.update();
			snowFlake.update();
			
			if(score > 10) {
				eagle.chase(bird);
				eagle.update();
			}
		}


	}

	private void render() throws IOException {
		BufferStrategy buf = this.getBufferStrategy();
		if(buf == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = buf.getDrawGraphics();

		if(state == STATE.GAME){
			start();
			backdrop.render(g);
			pipe.render(g);
			bird.render(g);
			playerScore.render(g);
			gameFloor.render(g);
//			trees.render(g);
						
			if(score > 10) {
				snowFlake.render(g);
				eagle.render(g);
			}
		}else{
			menu.render(g);
		}

		g.dispose();
		buf.show();
		
	}

	public static boolean isGameOver(boolean gameStatus) {
		return gameOver = gameStatus;
	}
	
	@Override
	public void paint(Graphics g) {}

	public static void newGame(){
		backdrop = new BackDrop(1);
		pipe = new Pipe(60);
		bird = new Bird(20,GameCore.HEIGHT/3,pipe.pipes);
		bird.setBirdSpeed(1);
		Camera.setLocation(100, 0);
		score = 0;
//		pipe.changePipeSpeed(6);
		playerScore = new Score();
		gameFloor = new Floor();
//		trees = new Trees();
		snowFlake = new Snow(6, 2, 6);
		try {
			eagle = new Eagle(50, 10, 20, 0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
