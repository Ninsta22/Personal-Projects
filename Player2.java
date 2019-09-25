import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/*
 * This class is everything for the other player. This class keeps
 * track of the position of the player, whether or not the player
 * can throw their disk, moves the player, dictates whether the player
 * can or cannot enter a certain area, and also process both the
 * range/details behind a grab.
 */

public class Player2 {
	double x = 100;
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
	
	public Player2(Tron tron){
		this.tron = tron;
	}
	
	//Method to draw the player
	public void paint(Graphics2D g2d){
		g2d.setColor(Color.ORANGE);
		g2d.fillRect((int)x, (int)y, width, height);
	}
	
	//Method that does the math for moving, along with making sure the player dosen't go past the drawn area
	public void move(){
		if(moveRight && x + xV < 370) x += xV;
		if(moveLeft && !tron.leftBorder.getBounds().intersects(getBounds())) x += xV;
		if(moveUp && !tron.topBorder.getBounds().intersects(getBounds())) y += yV;
		if(moveDown && !tron.bottomBorder.getBounds().intersects(getBounds())) y += yV;
		
		//This segment of code checks the scenario where the player was hit by an enemy disk
		if(tron.disk1.getBounds().intersects(getBounds()) && !ownsEnemy){
			tron.disk1.hit = true;
			tron.p1.hasDisk1 = true;
			tron.disk1.attack();
			tron.disk1.isAlive = false;
			if((tron.p2.health-tron.disk1.power)<= 0){
				tron.gameOver("Player 2 Wins");
			}else{
				tron.p2.health = tron.p2.health - tron.disk1.power;
				tron.disk1.power = 0;
			}
		}

		//This segment of code checks the scenario where the player was hit by their own disk that the enemy caught
		if(tron.disk2.getBounds().intersects(getBounds()) && tron.player1.ownsEnemy){
			tron.disk2.hit = true;
			tron.disk2.isAlive = false;
			tron.player1.ownsEnemy = false;
			tron.disk2.attack();
			tron.p2.hasDisk2 = true;
			if((tron.p2.health-tron.disk2.power)<= 0){
				tron.gameOver("Player 2 Wins");
			}else{
				tron.p2.health = tron.p2.health - tron.disk2.power;
				tron.disk2.power = 0;
			}
		}
	}
	
	//This segment of code makes sure that when the player lets go of the key, the model stops as well
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_D) moveRight = false;
		if(e.getKeyCode() == KeyEvent.VK_A) moveLeft = false;
		if(e.getKeyCode() == KeyEvent.VK_W) moveUp = false;
		if(e.getKeyCode() == KeyEvent.VK_S) moveDown = false;
	}
	
	/*This method deals with what happens when the player presses a key, they begin to move in that direction.
	*This method also deals with what happens when the player inputs the throw key, the grab key, and the 
	re-throw key.*/
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_A){
			xV = -3;
			moveLeft = true;
			moveRight = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			xV = 3;
			moveRight = true;
			moveLeft = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_W){
			yV = -3;
			moveUp = true;
			moveDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			yV = 3;
			moveDown = true;
			moveUp = false;
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_4 && !tron.player1.ownsEnemy && !tron.disk2.isAlive && !caughtEnemy){
			tron.disk2.shot(x, y, 0, 0);
		}
		if(e.getKeyCode() == KeyEvent.VK_5){
			if(tron.disk1.getBounds().intersects(grabRange()) && !tron.disk1.getBounds().intersects(getBounds()) && !ownsEnemy){
				ownsEnemy = true;
				tron.disk1.isAlive = false;
				tron.p2.hasDisk1 = true;
			}
			if(tron.disk2.getBounds().intersects(grabRange()) && !tron.disk2.getBounds().intersects(getBounds()) && tron.player1.ownsEnemy){
				tron.player1.ownsEnemy = false;
				tron.disk2.isAlive = false;
				caughtEnemy = true;
				tron.p2.hasDisk2 = true;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_6 && !tron.disk1.isAlive){
			if(ownsEnemy){
				tron.disk1.enemyShot(x, y, tron.disk1.adder+1, tron.disk1.powerAdd+20);
			}
			else if(caughtEnemy){
				tron.disk2.shot(x, y, tron.disk2.adder+1, tron.disk2.powerAdd+20);
				caughtEnemy = false;
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
