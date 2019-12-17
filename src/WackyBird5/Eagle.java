package WackyBird5;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Eagle extends Sprite
{
	private double x;
	private double y;
	
	private int r;

	private int angle;
	
	private BufferedImage eagleImageUp;
	private BufferedImage eagleImageDown;
	private boolean flappingUp, overlapBird;
	private int speed;
	public Bird bird;
	
	private double cosA = Lookup.cos[angle];
	private double sinA = Lookup.sin[angle];
	
	
	public Eagle(double x, double y, int r, int angle) throws IOException
	{
		this.x = x;
		this.y = y;
		this.r = r;
		
		this.angle = angle;
		
		this.eagleImageUp = Sprite.getSprite("images/flapUpEagle.png");
		this.eagleImageDown = Sprite.getSprite("images/flapDownEagle.png");
		this.flappingUp = true;
		this.overlapBird = false;
		this.speed = 1;
	}
	
	public void update() {
		speed += 2;
		
		if(speed >= 8) speed = 8;
		
		this.y += speed * Bird.getBirdGravity();
	}
	
	public void chase(Bird b)
	{
		turnToward(b);
		
		double dx = b.x - x;
		double dy = b.y - y;
		
		double d2 = dx*dx + dy*dy;
		double d = signedDistance(b);
		
		if (d > 1) {
			moveBy(1, 5);
			moveTowardBy(b);
		}		

		if(d < 1) overlapsBird(b);
		
	}

	
	public void turnToward(Bird b)
	{
		double d = signedDistance(b);
		
		if(d >  5)  rotateLeftBy(10);		
	
		if(d < -5)  rotateRightBy(10);
	}
	
	public void moveTowardBy(Bird b)
	{
		double d = signedDistance(b);
		
		if(d >  5)  rotateLeftBy(10);		
	
		if(d < -5)  rotateRightBy(10);
	}
	
//	public void turnAwayFrom(Bird e)
//	{
//		double d = signedDistance(e);
//		
//		if(d <  -10)  rotateLeftBy(4);		
//	
//		if(d >   10)  rotateRightBy(4);
//	}
	
	public double signedDistance(Bird b)
	{
		double cosA = Lookup.cos[angle];
		double sinA = Lookup.sin[angle];
		
		return (b.x - x)*sinA - (b.y - y)*cosA;
	}
	
	public void overlapsBird(Bird b) {
		double dx = x - b.x;
		double dy = y - b.y;
		
		double d2 = dx*dx + dy*dy;
		
		if(d2 <= (r + b.getCenterX()) * (r + b.getCenterY())) {
			overlapBird = true;
		}
	}

//	public boolean overlaps(Bird e)
//	{
//		double dx = x - e.x;
//		double dy = y - e.y;
//		
//		double d2 = dx*dx + dy*dy;
//		
//		return d2 <= (r + e.r) * (r + e.r);
//	}
	
//	public boolean overlaps(Line L)
//	{
//		double d = L.signedDistanceTo(this);
//		
//		return d <= r;
//	}
//	
//	public void moveBackFrom(Line L)
//	{
//		double d = L.signedDistanceTo(this);
//		
//		double mag = r - d;
//		
//		x += mag * L.xN;
//		y += mag * L.yN;
//	}
	
	
	public void rotateBy(int dangle)
	{
	   angle += dangle;
	   
	   if(angle >= 360)   angle -= 360;
	   
	   if(angle < 0)      angle += 360;
  	}

   
	
	public void rotateLeftBy(int dangle)
	{
	   angle -= dangle;
	   
	   if(angle < 0)      angle += 360;
  	}

   
	
	public void rotateRightBy(int dangle)
	{
	   angle += dangle;
	   
	   if(angle >= 360)   angle -= 360;
  	}

   
   public void moveForwardBy(int distance)
   {
   	double radians = angle*Math.PI/180;
   	double dx = distance * Math.cos(radians);
   	double dy = distance * Math.sin(radians);
   	
   	x += dx;
   	y += dy;
   }

   
	public void moveLeftBy(int dx)
	{
		x -= dx;
	}

	
	public void moveRightBy(int dx)
	{
		x += dx;
	}

	
	public void moveUpBy(int dy)
	{
		y -= dy;
	}

	
	public void moveDownBy(int dy)
	{
		y += dy;
	}

	
	public void moveBy(int dx, int dy)
	{
	   x += dx;
	   y += dy;
	}
	
	public void render(Graphics g)
	{		
		if(flappingUp) {
			g.drawImage(eagleImageUp,(int)x,(int)y,50,50,null);
//			g.drawImage(eagleImageUp,(int)(x + r * cosA),(int)(y + r * sinA),50,50,null);
			flappingUp = false;
		}else {
			try {
				g.drawImage(eagleImageDown, (int)x, (int)y, 50, 50, null);
//				g.drawImage(eagleImageUp,(int)(x + r * cosA),(int)(y + r * sinA),50,50,null);
				Thread.sleep(1);
				flappingUp = true;
			}
			catch (InterruptedException ex) {}
		}
				
//		g.drawLine(Bird.getBirdX(), Bird.getBirdY(), (int)(x + r * cosA), (int)(y + r * sinA));
	}
	
}