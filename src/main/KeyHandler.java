package main;

import java.awt.desktop.OpenFilesEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, fPressed;

	GamePanel gp;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			titleState(code);
		}

		// PLAY STATE
		if (gp.gameState == gp.playState) {
			playState(code);
		}

		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}

		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}

		// OPTIONS STATE
		else if (gp.gameState == gp.optionsState) {
			optionsState(code);
		}
		// Charater State
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}

		// GAMEOVER STATE
		else if (gp.gameState == gp.gameOverState) {
			gameOverState(code);
		}
	}

	public void titleState(int code) {

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 2;
			}
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 2) {
				gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.playMusic(0);
			}
			if (gp.ui.commandNum == 1) {
				// add later
			}
			if (gp.ui.commandNum == 2) {
				System.exit(0);
			}
		}
	}

	public void playState(int code) {

		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
			gp.music.pause();
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (code == KeyEvent.VK_F) {
			fPressed = true;
		}
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionsState;
			gp.ui.subState = 0;
			gp.ui.commandNum = 0;
			enterPressed = false;

		}
		// reload map for debug
		if (code == KeyEvent.VK_R) {
			gp.tileM.loadMap("/maps/world01.txt");
		}
//		if(code == KeyEvent.VK_ESCAPE){
//		    if (gp.gameState == gp.playState) {
//		        gp.gameState = gp.optionsState;
//		        gp.ui.subState = 0; // make sure it always starts at the top
//		        gp.ui.commandNum = 0;
//		    }
//		}
	}

	public void characterState(int code) {
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}

		if (code == KeyEvent.VK_W) {
			if(gp.ui.slotRow != 0) {
				gp.ui.slotRow --;
			}
		}
		if (code == KeyEvent.VK_A) {
			if(gp.ui.slotCol != 0) {
				gp.ui.slotCol --;
			}
			
		}
		if (code == KeyEvent.VK_S) {
			if(gp.ui.slotRow != 2) {
				gp.ui.slotRow++;
			}
		}
		if (code == KeyEvent.VK_D) {
			if(gp.ui.slotCol != 6) {
				gp.ui.slotCol++;
			}
		}

	}

	public void pauseState(int code) {

		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
			gp.music.resume();
		}
	}

	public void dialogueState(int code) {

		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.textCompleted) {
				gp.gameState = gp.playState;
			} else {
				// Fast-forward typing if user presses before it's done
				gp.ui.displayedText = gp.ui.currentDialogue;
				gp.ui.textCompleted = true;
			}
		}
	}

	public void optionsState(int code) {

		if (code == KeyEvent.VK_ESCAPE) {
			if (gp.gameState == gp.playState) {
				gp.gameState = gp.optionsState;
//		        gp.ui.subState = 0; // make sure it always starts at the top
//		        gp.ui.commandNum = 0;
			}
		}

		if (code == KeyEvent.VK_ENTER && !enterPressed) {
			enterPressed = true;
		}

		int maxCommandNum = 0;
		switch (gp.ui.subState) {
		case 0:
			maxCommandNum = 5;
			break;
		case 3:
			maxCommandNum = 1;
			break;
		}

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
//			gp.playSE(9);
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
		}

		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
//			gp.playSE(9);
			if (gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}

		if (code == KeyEvent.VK_A) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
				}
			}
		}

		if (code == KeyEvent.VK_D) {
			if (gp.ui.subState == 0) {
				if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
				}
			}
		}

	}

	public void gameOverState(int code) {

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
		}

		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
		}

		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.retry();
			} else if (gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.restart();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_F) {
			fPressed = false;
		}
	}
}