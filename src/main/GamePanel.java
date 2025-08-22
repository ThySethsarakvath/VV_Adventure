package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {

	// Screen Panel
	final int originalTileSize = 16; // 16x 16
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; // make element 48x48
	public final int maxScreenRow = 12;
	public final int maxScreenCol = 16;
	public final int screenWidth = tileSize * maxScreenCol; // 768 px of screen
	public final int screenHeight = tileSize * maxScreenRow; // 576 px

	// WORLD SETTINGS
	public int maxWorldCol;
	public int maxWorldRow ;

	public final int maxMap = 10;
	public int currentMap = 0;

	public final int worldWidth = tileSize * maxWorldCol; // Can be deleted
	public final int worldHeight = tileSize * maxWorldRow;// Can be deleted

	// Full Screen
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	// FOR MENU ACTION
	public boolean fullScreenOn = true;

	// FPS
	int FPS = 60;

	// SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	public Sound music = new Sound();
	public Sound se = new Sound();
	public EventHandler eHandler = new EventHandler(this);

	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	Config config = new Config(this);
	public PathFinder pFinder = new PathFinder(this);
	EnvironmentManager eManager = new EnvironmentManager(this);
	Thread gameThread; // to start and to stop

	// Entity and Object
	public Player player = new Player(this, keyH);
	public Entity obj[][] = new Entity[maxMap][30];// object array
	public Entity npc[][] = new Entity[maxMap][10];
	public Entity monster[][] = new Entity[maxMap][20];
	public InteractiveTile iTile[][] = new InteractiveTile[maxMap][300];
	public Entity projectile[][] = new Entity[maxMap][20];
	public ArrayList<Entity> particleList = new ArrayList<>();
	// This list store player npc obj
	ArrayList<Entity> entityList = new ArrayList<>();

	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int characterState = 4;
	// For dialogues
	public final int dialogueState = 3;
	public final int optionsState = 5;
	public final int gameOverState = 6;
	public final int transitionState = 7;
	public final int tradeState =8;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);

		/*
		 * DoubleBuffered is a technique that draws graphics to an off-screen image
		 * first, then displays it all at once to prevent flickering and make rendering
		 * smoother.
		 */
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		aSetter.setObject();
		aSetter.setNpc();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
//		playMusic(0);
		eManager.setup();

		gameState = titleState;

		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) tempScreen.getGraphics();

		if (fullScreenOn == true) {
			setFullScreen(); // command this if want to turn off fullscreen mode
		}
	}

	public void retry() {

		music.play();
		player.setDefaultPositions();
		player.restoreLife();
		aSetter.setNpc();
		aSetter.setMonster();

	}

	public void restart() {

		player.setDefaultValues();
		player.setDefaultPositions();
		player.restoreLife();
//		player.setItems(); when we have inventory
		aSetter.setNpc();
		aSetter.setMonster();
//		aSetter.setInteractiveTile(); Future version

	}

	public void setFullScreen() {
		// Get screen size from local device
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();

		if (fullScreenOn) {
			gd.setFullScreenWindow(Main.window);
		} else {
			// Otherwise, stay windowed
			gd.setFullScreenWindow(null);
			Main.window.setSize(screenWidth, screenHeight); // Restore to windowed size
			Main.window.setLocationRelativeTo(null);
		}

		// Update actual width/height based on result
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();

	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	// delta method
	public void run() {

		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
//				repaint();
				drawToTempScreen();
				drawToScreen();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				System.out.println("FPS: "+ drawCount );
				drawCount = 0;
				timer = 0;
			}
		}
	}

	public void update() {

		if (gameState == playState) {

			// Player
			player.update();
			
			// Npc
			for (int i = 0; i < npc[1].length; i++) {
				if (npc[currentMap][i] != null) {
					npc[currentMap][i].update();
				}
			}

			if (monster[currentMap] != null) {
				for (int i = 0; i < monster[currentMap].length; i++) {
					if (monster[currentMap][i] != null) {
						if (monster[currentMap][i].alive == true && monster[currentMap][i].die == false) {
							monster[currentMap][i].update();
						}
						if (monster[currentMap][i].alive == false) {
							monster[currentMap][i].checkDrop();
							monster[currentMap][i] = null;
						}
					}
				}
			}

			// projectile
			for (int i = 0; i < projectile[1].length; i++) {
				if (projectile[currentMap][i] != null) {
					if (projectile[currentMap][i].alive == true) {
						projectile[currentMap][i].update();
					}
					if (projectile[currentMap][i].alive == false) {
						projectile[currentMap][i] = null;
					}
				}
			}

			// Particle
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					if (particleList.get(i).alive == true) {
						particleList.get(i).update();
					}
					if (particleList.get(i).alive == false) {
						particleList.remove(i);
					}
				}
			}

			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) {
					iTile[currentMap][i].update();
				}
			}
			eManager.update();
		}

		if (gameState == pauseState) {

		}
	}

	// For activate full screen
	public void drawToTempScreen() {

		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		}

		// OTHERS
		else {

			tileM.draw(g2); // draw the world before player !!

			// INTERACTIVE TILE
			for (int i = 0; i < iTile[1].length; i++) {
				if (iTile[currentMap][i] != null) {
					iTile[currentMap][i].draw(g2);
				}
			}

			// add entity to the list
			entityList.add(player);

			// NPC
			for (int i = 0; i < npc[1].length; i++) {
				if (npc[currentMap][i] != null) {
					entityList.add(npc[currentMap][i]);
				}
			}

			// OBJECT
			for (int i = 0; i < obj[1].length; i++) {
				if (obj[currentMap][i] != null) { // to avoid null pointer errors
					entityList.add(obj[currentMap][i]);
				}
			}

			// MONSTER
			for (int i = 0; i < monster[1].length; i++) {
				if (monster[currentMap][i] != null) {
					entityList.add(monster[currentMap][i]);
				}
			}

			for (int i = 0; i < projectile[1].length; i++) {
				if (projectile[currentMap][i] != null) {
					entityList.add(projectile[currentMap][i]);
				}
			}

			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					entityList.add(particleList.get(i));
				}
			}

			// Sort the entity to check their coordinate
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {

					int result = Integer.compare(e1.worldY, e2.worldY);

					return result;
				}
			});

			// draw entities
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}

			// empty entities list
			entityList.clear();

			// PLAYER
//			player.draw(g2);
			
			// ENVIRONMENT
			eManager.draw(g2);

			// UI
			ui.draw(g2);
		}
	}

	public void drawToScreen() {

		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}

	// for background music
	public void playMusic(int i) {

		music.setFile(i); // get sound from file
		music.play(); // play sound
		music.loop(); // loop sound through game
	}

	public void stopMusic() {

		music.stop();
	}

	// for item sound effect
	public void playSE(int i) {

		se.setFile(i);
		se.play();
	}
}