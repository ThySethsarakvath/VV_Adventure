package main;

import java.awt.*;
import javax.swing.*;

public class Main {

	public static JFrame window;
	//VV Adventure main program
    public static void main(String[] args) {
    	
    	
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("VV_Adventure");
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        gamePanel.config.loadConfig();
        if(gamePanel.fullScreenOn == true) {
        	window.setUndecorated(true);
        }
        
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}