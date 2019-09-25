import java.awt.Color;
import java.awt.Graphics2D;

/*
 * This class deals with the interface on top of the game, being
 * both the healthbar and the indication of which disk the player
 * can throw. The class displays the healthbar and updates the healthbar
 * for when the respective player gets hit.
 */

public class HealthBar {
	int x;
	int y;
	int height;
	int health = 150;
	boolean hasDisk1;
	boolean hasDisk2;
	private Tron tron;
	
	public HealthBar(Tron tron, int x, int y, int height, boolean hasDisk1, boolean hasDisk2){
		this.tron = tron;
		this.x = x;
		this.y = y;
		this.height = height;
		this.hasDisk1 = hasDisk1;
		this.hasDisk2 = hasDisk2;
	}
	
	//In this method, the interface for player 1 gets added to the game window
	public void paintP1(Graphics2D g){
		g.setPaint(Color.GREEN);
		g.fillRect(x, y, health, height);
		if(hasDisk1){
			g.setPaint(Color.RED);
			g.fillOval(x-25, y, 20, 20);
		}
		if(hasDisk2){
			g.setPaint(Color.BLUE);
			g.fillOval(x-55, y, 20, 20);
		}
	}
	
	//In this method, the interface for player 2 gets added to the game window
	public void paintP2(Graphics2D g){
		g.setPaint(Color.GREEN);
		g.fillRect(x, y, health, height);
		if(hasDisk2){
			g.setPaint(Color.BLUE);
			g.fillOval(x+155, y, 20, 20);
		}
		if(hasDisk1){
			g.setPaint(Color.RED);
			g.fillOval(x+185, y, 20, 20);
		}
	}
}
