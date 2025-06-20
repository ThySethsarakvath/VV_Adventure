package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	//Screen Panel
	final int originalTileSize = 16; //16x 16
	final int scale = 3;
	
	public final int tileSize = originalTileSize*scale; //make element 48x48
	public final int maxScreenRow = 12;
	public final int maxScreenCol = 14;
	public final int screenWidth = tileSize * maxScreenCol; //768 px of screen
	public final int screenHeight = tileSize * maxScreenRow; //576 px
	
	// WORLD SETTINGS
	public final int maxWorldCol = 50; // Changeable depends on world01.txt
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	//FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread; //to start and to stop
	public Player player = new Player(this, keyH);
	
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		
		/* 
		 DoubleBuffered is a technique that draws graphics to an off-screen image first, 
		 then displays it all at once to prevent flickering and make rendering smoother.
		 */
		this.setDoubleBuffered(true); 
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	//using sleep method
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		
//		double drawInterval = 1000000000/fps; //draw the screen within 0.01666 s
//		double nextDrawTime = System.nanoTime() +drawInterval;
//		//game loop or core
//		while(gameThread != null) {
////			long currentTime = System.nanoTime();
////			System.out.println("Current time: "+currentTime);
//			
//			// update character position
//			update();
//			
//			//draw the screen with the update postion of character
//			repaint();
//			
//			try {
//				
//			double remainingTime = nextDrawTime -System.nanoTime();
//			remainingTime = remainingTime/1000000;
//			
//			if(remainingTime <0) {
//				remainingTime =0;
//			}
//			
//			
//			Thread.sleep((long) remainingTime);
//			nextDrawTime +=drawInterval;
//			
//		}catch(InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//}
	
	//delta method
	public void run() {
		
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >=1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: "+ drawCount );
				drawCount = 0;
				timer =0;
			}
		}
	}
	
	public void update() {

		player.update();
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		// draw the world before player !!
		tileM.draw(g2);
		
		player.draw(g2);
	
		g2.dispose();
	}
}