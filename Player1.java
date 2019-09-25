import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/*
 * This class is everything for the first player. This class keeps
 * track of the position of the player, whether or not the player
 * can throw their disk, moves the player, dictates whether the player
 * can or cannot enter a certain area, and also process both the
 * range/details behind a grab.
 */

public class Player1 {
	double x = 650;
	double y = 400;
	int xV = 0;
	int yV = 0;
	int health = 40;
	boolean caughtEnemy = false;
	boolean ownsEnemy = false;
	boolean moveRight = false;
	boolean moveLeft = false;
	boolean moveUp = false;
	boolean moveDown = false;
	private static int height = 30;
	private static int width = 30;
	
	private Tron tron;
	
	public Player1(Tron tron){
		this.tron = tron;
	}
	
	//Method to draw the player
	public void paint(Graphics2D g2d){
		g2d.setColor(Color.ORANGE);
		g2d.fillRect((int)x, (int)y, width, height);
	}
	
	//Method that does the math for moving, along with making sure the player dosen't go past the drawn area
	public void move(){
		if(moveRight && !tron.rightBorder.getBounds().intersects(getBounds())) x += xV;
		if(moveLeft && x + xV > 400) x += xV;
		if(moveUp && !tron.topBorder.getBounds().intersects(getBounds())) y += yV;
		if(moveDown && !tron.bottomBorder.getBounds().intersects(getBounds())) y += yV;
		
		//This segment of code checks the scenario where the player was hit by an enemy disk that the enemy caught
		if(tron.disk1.getBounds().intersects(getBounds()) && tron.player2.ownsEnemy){
			tron.disk1.hit = true;
			tron.disk1.isAlive = false;
			tron.disk1.attack();
			tron.player2.ownsEnemy = false;
			tron.p1.hasDisk1 = true;
			if((tron.p1.health-tron.disk1.power)<= 0){
				tron.p1.health = tron.p1.health - tron.disk1.power;
				tron.gameOver("Player 1 Wins");
			}else{
				tron.p1.health = tron.p1.health - tron.disk1.power;
				tron.disk1.power = 0;
			}
		}
		
		//This segment of code checks the scenario where the player was hit by an enemy disk
		if(tron.disk2.getBounds().intersects(getBounds()) && !ownsEnemy){
			tron.disk2.hit = true;
			tron.disk2.isAlive = false;
			tron.disk2.attack();
			tron.p2.hasDisk2 = true;
			if((tron.p1.health-tron.disk2.power) <= 0){
				tron.p1.health = tron.p1.health - tron.disk2.power;
				tron.gameOver("Player 1 Wins");
			}else{
				tron.p1.health = tron.p1.health - tron.disk2.power;
				tron.disk2.power = 0;
			}
		}
	}
	
	//This segment of code makes sure that when the player lets go of the key, the model stops as well
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) moveRight = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT) moveLeft = false;
		if(e.getKeyCode() == KeyEvent.VK_UP) moveUp = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN) moveDown = false;
	}
	
	
	/*This method deals with what happens when the player presses a key, they begin to move in that direction.
	*This method also deals with what happens when the player inputs the throw key, the grab key, and the 
	re-throw key.*/
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			xV = -3;
			moveLeft = true;
			moveRight = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			xV = 3;
			moveRight = true;
			moveLeft = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			yV = -3;
			moveUp = true;
			moveDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			yV = 3;
			moveDown = true;
			moveUp = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_OPEN_BRACKET && !tron.player2.ownsEnemy && !tron.disk1.isAlive && !caughtEnemy){
			tron.disk1.shot(x, y, 0, 0);
		}
		if(e.getKeyCode() == KeyEvent.VK_CLOSE_BRACKET){
			if(tron.disk1.getBounds().intersects(grabRange()) && !tron.disk1.getBounds().intersects(getBounds()) && tron.player2.ownsEnemy){
				tron.player2.ownsEnemy = false;
				tron.disk1.isAlive = false;
				caughtEnemy = true;
			}
			if(tron.disk2.getBounds().intersects(grabRange()) && !tron.disk2.getBounds().intersects(getBounds()) && !ownsEnemy){
				tron.disk2.isAlive = false;
				ownsEnemy = true;
				tron.p1.hasDisk2 = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_BACK_SLASH){
			if(caughtEnemy && !tron.disk1.isAlive){
				tron.disk1.shot(x, y, tron.disk1.adder+1, tron.disk1.powerAdd+20);
			}
			else if(ownsEnemy && !tron.disk2.isAlive){
				tron.disk2.enemyShot(x, y, tron.disk2.adder+1, tron.disk2.powerAdd+20);
				tron.p1.hasDisk2 = false;
			}
		}	
	}
	
	//This method returns the "hit box" for the grab, or where the disk has to be to be considered a grab
	public Rectangle grabRange(){
		return new Rectangle((int)x-10, (int)y-10, width+20, height+20);
	}
	
	//This method returns the "hit box" of the player
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, width, height);
	}
}
