package WackyBird5;
import java.awt.*;
import java.io.IOException;

public class Menu{

    public void render(Graphics g) throws IOException {
        Font font0 = new Font("arial", Font.BOLD, 55);
        Font font1 = new Font("arial", Font.BOLD, 45);
        Font font2 = new Font("arial", Font.BOLD, 21);
        g.setFont(font0);

        g.setColor(Color.GRAY);
        g.fillRect(0, 0, GameCore.WIDTH, GameCore.HEIGHT);
        g.drawImage(Bird.getBird(), GameCore.WIDTH / 3 - 150, 150, null);

        g.setColor(Color.white);
        g.drawString("WACKY BIRD", GameCore.WIDTH / 3, 100);

        g.setColor(Color.black);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        
        if(GameCore.state == GameCore.STATE.MENU) {
        	g.drawString("Press S to start!", GameCore.WIDTH / 2 - 80, GameCore.HEIGHT / 2);
        	g.drawString("   Q to quit!    ", GameCore.WIDTH / 2 - 80, GameCore.HEIGHT / 2 + 30);
        }
        else if(GameCore.state == GameCore.STATE.OVER) {
        	g.setColor(Color.red);
        	g.setFont(font1);
        	g.drawString("GAME OVER", GameCore.WIDTH / 2 - 150, GameCore.HEIGHT / 2);
        	g.setFont(font2);
        	g.setColor(Color.white);
        	g.drawString("Press R to start!", GameCore.WIDTH / 2 - 80, GameCore.HEIGHT / 2 + 30);
        	g.drawString("   Q to quit!    ", GameCore.WIDTH / 2 - 80, GameCore.HEIGHT / 2 + 60);
        }else if(GameCore.state == GameCore.STATE.WIN) {
        	g.setColor(Color.blue);
        	g.setFont(font1);
        	g.drawString("CONGRATULATIONS, YOU HAVE WON!!!", GameCore.WIDTH / 2 - 300, GameCore.HEIGHT / 2);
        	g.setFont(font2);
        	g.setColor(Color.white);
        	g.drawString("Press S for new game!", GameCore.WIDTH / 2 - 80, GameCore.HEIGHT / 2 + 30);
        	g.drawString("   Q to quit!    ", GameCore.WIDTH / 2 - 80, GameCore.HEIGHT / 2 + 60);
        }
    }
}
