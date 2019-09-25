import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/*
 * This class is for everything related to the Disk that initially
 * belongs to the second player. This class with deal with whether
 * the disk is active, where it's location is, and also how much
 * damage the disk will deal.
 */

public class Disk1 {
	boolean isAlive = false;
	int bouncesLeft;
	boolean hit = false;
	int adder;
	int powerAdd;
	int power = 20;
	double x;
	double y;
	int width = 35;
	int height = 35;
	int xV;
	int yV;
	
	private Tron tron;
	
	public Disk1(Tron tron){
		this.tron = tron;
	}
	
	//This method creates disk
	public void paint(Graphics2D g){
		g.setColor(Color.RED);
		g.fillOval((int)x, (int)y, width, height);
	}
	
	/*
	 * This method deals with a variety of situations. While all of them are related to the disk
	 * moving, such as where it should move, how it reacts to being thrown diagonally, and the speed,
	 * it deals with all the collisions as well
	 */
	public void move(){
		if(bouncesLeft == 0){
			isAlive = false;
			xV = 0;
			adder = 0;
			yV = 0;
			if(tron.player2.ownsEnemy) tron.player2.ownsEnemy = false;
			if(tron.player1.caughtEnemy) tron.player1.caughtEnemy = false;
			tron.p1.hasDisk1 = true;
			return;
		}
		if(hit){
			xV = 0;
			yV = 0;
			adder = 0;
		}
		if(tron.topBorder.getBounds().intersects(getBounds())){
			yV = 3+adder;
			bouncesLeft--;
		}
		if(tron.bottomBorder.getBounds().intersects(getBounds())){
			yV = -3-adder;
			bouncesLeft--;
		}
		if(tron.leftBorder.getBounds().intersects(getBounds())){
			xV = 3+adder;
			bouncesLeft--;
		}
		if(tron.rightBorder.getBounds().intersects(getBounds())){
			xV = -3-adder;
			bouncesLeft--;
		}
		
		x += xV;
		y += yV;
		
	}
	
	//Method to stabilize the disk
	public void attack(){
		x -= 2*xV;
		y -= 2*yV;
	}
	
	//Method that states that the player shot their own disk or not
	public void shot(double x, double y, int adder, int powerAdd){
		bouncesLeft = 5;
		xV = 0;
		yV = 0;
		power = 20;
		isAlive = true;
		hit = false;
		this.x = x;
		this.y = y;
		power += powerAdd;
		if(tron.player1.moveDown) yV = 3+adder;
		if(tron.player1.moveUp) yV = -3-adder;
		if(tron.player1.moveLeft) xV = -3-adder;
		if(tron.player1.moveRight) xV = 3+adder;
		if(!(tron.player1.moveRight && tron.player1.moveLeft && tron.player1.moveUp && tron.player1.moveDown)) xV = -3-adder;
		this.adder = adder;
		this.powerAdd = powerAdd;
		tron.p1.hasDisk1 = false;
	}
	
	//Method that tells the game that the player shot a disk that they caught from their opponent
	public void enemyShot(double x, double y, int adder, int powerAdd){
		bouncesLeft = 5;
		xV = 0;
		yV = 0;
		power = 20;
		isAlive = true;
		hit = false;
		this.x = x;
		this.y = y;
		power += powerAdd;
		if(tron.player2.moveDown) yV = 3+adder;
		if(tron.player2.moveUp) yV = -3-adder;
		if(tron.player2.moveLeft) xV = -3-adder;
		if(tron.player2.moveRight) xV = 3+adder;
		if(!(tron.player2.moveRight && tron.player2.moveLeft && tron.player2.moveUp && tron.player2.moveDown)) xV = 3+adder;
		this.adder = adder;
		this.powerAdd = powerAdd;
	}
	
	//This method creates the "hit box" for the disk
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, width, height);
	}
	
}
