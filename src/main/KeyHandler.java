package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed,fPressed;
	
	GamePanel gp;
	
	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		// PLAY STATE
		if(gp.gameState == gp.playState) {
			if(code == KeyEvent.VK_W) {
				upPressed =true;
			}
			if(code == KeyEvent.VK_A) {
				leftPressed = true	;	
			}
			if(code == KeyEvent.VK_S) {
				downPressed = true;
			}
			if(code == KeyEvent.VK_D) {
				rightPressed = true;
			}
			if (code == KeyEvent.VK_P) {
				gp.gameState = gp.pauseState;
				gp.music.pause();
			}
			if(code == KeyEvent.VK_ENTER) {
				enterPressed = true;
			}
			if(code == KeyEvent.VK_F) {
				fPressed = true;
			}
		}
		
		// PAUSE STATE
		else if(gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_P) {
				gp.gameState = gp.playState;
				gp.music.resume();
			}
		}
		
		// DIALOGUE STATE
		else if(gp.gameState == gp.dialogueState) {
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.textCompleted) {
					gp.gameState = gp.playState;
				} else {
					// Fast-forward typing if user presses before it's done
					gp.ui.displayedText = gp.ui.currentDialogue;
					gp.ui.textCompleted = true;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) {
			upPressed =false;
		}
		if(code == KeyEvent.VK_A) {
			leftPressed = false	;	
		}
		if(code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if(code == KeyEvent.VK_F) {
			fPressed = false;
		}
	}
}