package com.timoteus3000.graphic;

import javax.swing.JFrame;

public class GOFFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1704967331989257360L;
	
	private GOFPanel gofPanel;
	
	public GOFFrame(String title) {
		this.setTitle(title);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(950, 790);
        this.setLocation(this.getX()-this.getWidth()/2,
                this.getY()-this.getHeight()/2);
        this.setResizable(false);
        this.setLayout(null);
        
        gofPanel = new GOFPanel();
        gofPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(gofPanel);
        
        this.setVisible(true);
	}
}