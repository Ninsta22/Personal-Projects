import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Project Started: February 2019
 * Project Ended: April 2019
 * 
 * Created by: Kian Bagherlee
 * 
 * This is the main class of the game Tron Dodgeball, where everything is intended to be built,
 * and implemented. The goal of the game is to make the other player's health bar become
 * 0. You do this by throwing a disk you have, where the disk hitting the player deals
 * damage. As a player, you can also catch your opponents thrown disk, and if you time it
 * correctly, you can catch their disk. With this, you can throw their disk back at them
 * with double the speed and double the damage!
 * 
 */
public class Tron extends JPanel{
	
	//This section of code is for initializing the Borders of the Game, Player Models, Game Objects, and Situation Parameters
	StageObstacle leftBorder = new StageObstacle(this, 0, 40, 30, 745);
	StageObstacle topBorder = new StageObstacle(this, 0, 40, 780, 30);
	StageObstacle rightBorder = new StageObstacle(this, 750, 40, 30, 745);
	StageObstacle bottomBorder = new StageObstacle(this, 0, 715, 780, 30);
	StageObstacle middleLine = new StageObstacle(this, 400,40,1,800);
	Player1 player1 = new Player1(this);
	Player2 player2 = new Player2(this);
	Disk1 disk1 = new Disk1(this);
	Disk2 disk2 = new Disk2(this);
	HealthBar p1 = new HealthBar(this, 600, 10, 20, true, false);
	HealthBar p2 = new HealthBar(this, 20, 10, 20, false, true);
	boolean Owon = false;
	boolean Twon = false;
	
	public Tron(){
		
		//This is used so that the game can register inputs from the actual computer
		addKeyListener(new KeyListener(){
				
				public void keyTyped(KeyEvent e){
				}
				
				public void keyPressed(KeyEvent e){
					player1.keyPressed(e);
					player2.keyPressed(e);
				}
				
				public void keyReleased(KeyEvent e){
					player1.keyReleased(e);
					player2.keyReleased(e);
				}
		});
		setFocusable(true);
	}
	
	//This method moves the players, and if the disk is active, moves it as well
	private void move(){
		player1.move();
		player2.move();
		if(disk1.isAlive) disk1.move();
		if(disk2.isAlive) disk2.move();
	}
	
	//This method is used to allow the player to see everything
	public void paint(Graphics g){
		
		//This sets up the window where the game is played
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//This actually displays/activates the in-game elements
		leftBorder.paint(g2d);
		topBorder.paint(g2d);
		rightBorder.paint(g2d);
		bottomBorder.paint(g2d);
		middleLine.paint(g2d);
		player1.paint(g2d);
		player2.paint(g2d);
		p1.paintP1(g2d);
		p2.paintP2(g2d);
		
		//Checks whether or not to display the Disc
		if(disk1.isAlive){
			disk1.paint(g2d);
		}
		if(disk2.isAlive){
			disk2.paint(g2d);
		}
	}
	
	//Method used to end the game
	public void gameOver(String winner){
		JOptionPane.showMessageDialog(this, "Game Over! " + winner, "Game Over!", JOptionPane.YES_NO_OPTION);
		System.exit(ABORT);
	} 
	
	public static void main(String[] args) throws InterruptedException{
		//This simply calls the game
		JFrame game = new JFrame();
		Tron hi = new Tron();
		game.add(hi);
		game.setSize(800, 800);
		game.setVisible(true);
		
		//Pop-up message when the game begins to run
		JOptionPane.showMessageDialog(null, "The goal of the Game is to Hit Your Opponent!", "Start Game", JOptionPane.YES_NO_CANCEL_OPTION); 
		while(true){
			hi.move();
			hi.repaint();
			Thread.sleep(10);
		}
	}
}
