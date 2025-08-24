package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import entity.PlayerDummy;
import monster.Ice_Golem;
import object.OBJ_Door;

public class CutscenceManager {

	GamePanel gp;
	Graphics2D g2;
	public int scenceNum;
	public int scencePhase;
	int counter = 0;
	float alpha = 0f;
	int y;
	
	public final int NA = 0;
	public final int Golem =1;
	public final int ending =2;
	
	public CutscenceManager(GamePanel gp) {
		this.gp = gp;
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		
		switch(scenceNum) {
		case Golem: scence_Golem() ;break;
		case ending: scence_Ending();break;
		}
	}
	
	public void scence_Golem() {
	    if(scencePhase == 0) {
	        gp.bossBattleOn = true;
	        for(int i = 0; i< gp.obj[1].length ;i++) {
	            if(gp.obj[gp.currentMap][i] == null) {
	                gp.obj[gp.currentMap][i] = new OBJ_Door(gp);
	                gp.obj[gp.currentMap][i].worldX = gp.tileSize*38;
	                gp.obj[gp.currentMap][i].worldY = gp.tileSize*77;
	                gp.obj[gp.currentMap][i].temp = true;
	                gp.playSE(17);
	                break;
	            }
	        }
	        // search vacant slot for dummy
	        for(int i =0 ; i< gp.npc[1].length;i++) {
	            if(gp.npc[gp.currentMap][i] == null) {
	                gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
	                gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
	                gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
	                gp.npc[gp.currentMap][i].direction = gp.player.direction;
	                break;
	            }
	        }
	        gp.player.drawing = false;
	        scencePhase++;
	    }
	    else if(scencePhase == 1) {
	        gp.player.worldY -= 2;
	        
	        if(gp.player.worldY < gp.tileSize*60) {
	            scencePhase++;
	        }
	    }
	    else if(scencePhase == 2) {
	        // search the boss
	        for(int i=0; i< gp.monster[1].length;i++) {
	            if(gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name.equals(Ice_Golem.monName)) {
	                gp.monster[gp.currentMap][i].sleep = false;
	                // Set the first dialogue
	                gp.ui.currentDialogue = gp.monster[gp.currentMap][i].dialogues[0];
	                gp.ui.resetDialogueState();
	                scencePhase++;
	                break;
	            }
	        }
	    }
	    else if (scencePhase == 3) {
	        if (gp.ui.currentDialogue != null) {
	            gp.ui.drawDialogueScreen();

	            if (gp.ui.textCompleted && gp.keyH.enterPressed) {
	                gp.keyH.enterPressed = false;

	                for (int i = 0; i < gp.monster[gp.currentMap].length; i++) {
	                    if (gp.monster[gp.currentMap][i] != null &&
	                        gp.monster[gp.currentMap][i].name.equals(Ice_Golem.monName)) {

	                        Ice_Golem golem = (Ice_Golem) gp.monster[gp.currentMap][i];

	                        if (golem.dialogueIndex < golem.dialogues.length - 1) {
	                            golem.dialogueIndex++;
	                            gp.ui.currentDialogue = golem.dialogues[golem.dialogueIndex];
	                            gp.ui.resetDialogueState();
	                        } else {
	                            // Dialogue finished
	                            gp.ui.currentDialogue = null;
	                            gp.ui.displayedText = "";
	                            gp.ui.textCompleted = false;
	                            scencePhase++; // ✅ Move to phase 4
	                        }
	                        break;
	                    }
	                }
	            }
	        } else {
	            // Failsafe: if dialogue is null, move on
	            scencePhase++;
	        }
	    }

	    else if(scencePhase == 4) {
	        // return to player
	        for(int i=0;i<gp.npc[1].length;i++) {
	            if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
	                gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
	                gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
	                
	                // delete dummy
	                gp.npc[gp.currentMap][i] = null;
	                break;
	            }
	        }
	        // draw player
	        gp.player.drawing = true;
	        
	        // reset
	        scenceNum = NA;
	        scencePhase = 0;
	        gp.gameState = gp.playState;
	        
	        gp.stopMusic();
//	        gp.playMusic(0);
	    }
	}
	public void scence_Ending() {
		if(scencePhase == 0) {
			gp.stopMusic();
			scencePhase++;
		}
		if(scencePhase == 1) {
			if(counterReached(300) == true) {
				scencePhase++;
			}
		}
		if (scencePhase == 2) {
		    alpha += 0.005f;
		    if (alpha >= 1f) {
		        alpha = 1f;
		        scencePhase++; // Only move to next phase when fade is complete
		    }
		    drawBlackBackground(alpha); // Keep drawing until fully black
		}
		else if (scencePhase == 3) {
		    drawBlackBackground(1f); // Ensure full black background
		    String text = "The End";
		    g2.setFont(g2.getFont().deriveFont(48f));
		    FontMetrics fm = g2.getFontMetrics();
		    int textWidth = fm.stringWidth(text);
		    int textHeight = fm.getHeight();

		    // Center horizontally and vertically
		    int x = (gp.screenWidth - textWidth) / 2;
		    int y = (gp.screenHeight - textHeight) / 2 + fm.getAscent();

		    g2.setColor(Color.white);
		    g2.drawString(text, x, y);


		    if (counterReached(180)) { // Wait ~3 seconds at 60 FPS
		        scencePhase++;
		    }
		}
		else if (scencePhase == 4) {
		    // Reset or transition
		    scenceNum = NA;
		    scencePhase = 0;
		    gp.gameState = gp.playState; // Or creditsState, etc.
		}


	}
	
	public boolean counterReached( int target) {
		boolean counterReach = false;
		counter++;
		if(counter> target) {
			counterReach = true;
			counter = 0;
		}
		return counterReach;
	}
	
	public void drawBlackBackground(float alpha) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
	}
}