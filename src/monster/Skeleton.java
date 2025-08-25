package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Arrow;
import object.OBJ_Emerald;
import object.OBJ_Firecharge;
import object.OBJ_healingP;

public class Skeleton extends Entity {

    GamePanel gp;
    private int pathRecalcCounter = 0;
    private final int PATH_RECALC_COOLDOWN = 30;
    private int attackCooldown = 0;
    private final int ATTACK_COOLDOWN = 90; // 1.5 seconds at 60FPS

    public Skeleton(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_skeleton;
        name = "Skeleton";
        speed = 1;
        maxLife = 6;
        life = maxLife;
        attack = 2;

        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
        getAttackImage();
    }

    public void getImage() {
        up1 = setup("/monster/su1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/su2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/sd1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/sd2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/sl1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/sl2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/sr1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/sr2", gp.tileSize, gp.tileSize);
        upStand = setup("/monster/su", gp.tileSize, gp.tileSize);
        downStand = setup("/monster/sd", gp.tileSize, gp.tileSize);
        leftStand = setup("/monster/sl", gp.tileSize, gp.tileSize);
        rightStand = setup("/monster/sr", gp.tileSize, gp.tileSize);
    }

    public void getAttackImage() {
        aUp1 = setup("/monster/sua1", gp.tileSize, gp.tileSize);
        aUp2 = setup("/monster/sua2", gp.tileSize, gp.tileSize);
        aDown1 = setup("/monster/sda1", gp.tileSize, gp.tileSize);
        aDown2 = setup("/monster/sda2", gp.tileSize, gp.tileSize);
        aLeft1 = setup("/monster/sla1", gp.tileSize, gp.tileSize);
        aLeft2 = setup("/monster/sla2", gp.tileSize, gp.tileSize);
        aRight1 = setup("/monster/sra1", gp.tileSize, gp.tileSize);
        aRight2 = setup("/monster/sra2", gp.tileSize, gp.tileSize);
    }

    @Override
    public void update() {
        super.update();

        

        if (attacking) {
            attackAnimation();
            return;
        }
     // Handle attack cooldown
        if (attackCooldown > 0) {
            attackCooldown--;
        }

        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        if (contactPlayer && shotCounter >= 30) {
            damagePlayer(attack);
            shotCounter = 0;
        }

        int xDist = Math.abs(gp.player.worldX - worldX);
        int yDist = Math.abs(gp.player.worldY - worldY);
        int tileDist = (xDist + yDist) / gp.tileSize;

        // Stop chasing if player is too far
        if (tileDist > 15) {
            onPath = false;
            getRandomDirection(120);
        }
        // If player is within 7 tiles and attack cooldown is ready, shoot
        else if (tileDist <= 7 && attackCooldown == 0) {
            onPath = false;
            gp.pFinder.pathList.clear();

            // Face player
            int yDistSigned = gp.player.worldY - worldY;
            int xDistSigned = gp.player.worldX - worldX;
            
            if (Math.abs(yDistSigned) > Math.abs(xDistSigned)) {
                direction = yDistSigned > 0 ? "down" : "up";
            } else {
                direction = xDistSigned > 0 ? "right" : "left";
            }

            // Start attack
            attacking = true;
            spriteNum = 1;
            spriteCounter = 0;
            attackCooldown = ATTACK_COOLDOWN;
        }
        // If player is between 8 and 15 tiles, chase using pathfinding
        else {
            onPath = true;
            
            // Use pathfinding to chase player with cooldown
            pathRecalcCounter++;
            if (pathRecalcCounter >= PATH_RECALC_COOLDOWN) {
                pathRecalcCounter = 0;
                int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
                int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
                searchPath(goalCol, goalRow);
            }
        }
    }

    public void attackAnimation() {
        spriteCounter++;
        System.out.println("Attack animation - counter: " + spriteCounter + ", spriteNum: " + spriteNum); // DEBUG

        if (spriteCounter <= 20) {
            spriteNum = 1;
            System.out.println("Attack frame 1"); // DEBUG
        } 
        else if (spriteCounter <= 35) {
            spriteNum = 2;
            System.out.println("Attack frame 2"); // DEBUG
            
            if (spriteCounter == 25) {
                System.out.println("FIRING ARROW!"); // DEBUG
                OBJ_Arrow arrow = new OBJ_Arrow(gp);
                
                int arrowX = worldX;
                int arrowY = worldY;
                switch (direction) {
                    case "up": arrowY -= gp.tileSize; break;
                    case "down": arrowY += gp.tileSize; break;
                    case "left": arrowX -= gp.tileSize; break;
                    case "right": arrowX += gp.tileSize; break;
                }
                
                arrow.set(arrowX, arrowY, direction, true, this);
                
                boolean arrowPlaced = false;
                for(int i = 0; i < gp.projectile[1].length; i++) {
                    if(gp.projectile[gp.currentMap][i] == null) {
                        gp.projectile[gp.currentMap][i] = arrow;
                        arrowPlaced = true;
                        break;
                    }
                }
                System.out.println("Arrow placed: " + arrowPlaced); // DEBUG
                gp.playSE(11);
            }
        } 
        else if (spriteCounter > 45) {
            attacking = false;
            spriteNum = 1;
            spriteCounter = 0;
            System.out.println("Attack ended"); // DEBUG
        }
    }

    public void setAction() {
        if (!onPath && !attacking) {
            getRandomDirection(120);
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
        int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
        int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;
        searchPath(goalCol, goalRow);
    }
    
    public void checkDrop() {
        int i = new Random().nextInt(100)+1;
        
        if(i<50) {
            dropItem(new OBJ_Emerald(gp));
        }
        if(i >= 50 && i< 75) {
            dropItem(new OBJ_Firecharge(gp));
        }
        if(i >= 75 && i< 100) {
            dropItem(new OBJ_healingP(gp));
        }
    }
}