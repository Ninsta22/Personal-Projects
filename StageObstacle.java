import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
 * The purpose of this class is to act as the outer boundaries for the game.
 * This will prevent the player from leaving the created arena, and will 
 * bounce the disk when it collides with the boundaries.
 */

public class StageObstacle {
	int x;
	int y;
	int width;
	int height;
	
	private Tron game;
	
	public StageObstacle(Tron game, int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.game = game;
	}
	
	/*This method, when called, created a rectangle around the object,
	acting as sort of a "hit box" for when the player collides with
	it*/
	public Rectangle getBounds(){
		return new Rectangle(x, y, width, height);
	}
	
	public void paint(Graphics2D g){
		g.fillRect(x, y, width, height);
	}
}
