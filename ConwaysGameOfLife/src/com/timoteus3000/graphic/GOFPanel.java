package com.timoteus3000.graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.timoteus3000.logic.GOFLogic;

public class GOFPanel extends JPanel implements MouseListener, KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5270649626122315387L;
	
	private GOFLogic gofLogic;
	
	private int cellWidth;
	private int cellHeight;
	
	private boolean isRunning;
	
	private JLabel numOfGenerations;
	private int numOfGenerationsInt;
	
	private Rectangle clearButton;
	
	public GOFPanel() {
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		
		isRunning = false;
		
		cellWidth = 15;
		cellHeight = 15;
		
		gofLogic = new GOFLogic();
		
		numOfGenerations = new JLabel("Anzahl Gen.: "+numOfGenerationsInt);
		numOfGenerations.setForeground(Color.WHITE);
		numOfGenerations.setBounds(cellWidth*gofLogic.getCurrentArea()[0].length+10, 10, 150, 25);
		this.add(numOfGenerations);
		
		clearButton = new Rectangle(cellWidth*gofLogic.getCurrentArea()[0].length+10, 40, 150, 30);
		
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		numOfGenerations.setText("Anzahl Gen.: "+numOfGenerationsInt);
		if (isRunning) {
			gofLogic.completeOneCycle();
			numOfGenerationsInt++;
			
			repaint();
		}
		
		g.setColor(Color.WHITE);
		for(int y = 0; y < gofLogic.getCurrentArea().length; y++) {
			for(int x = 0; x < gofLogic.getCurrentArea()[y].length; x++) {
				if(gofLogic.getCurrentArea()[y][x] == 1) {
					g.setColor(Color.GREEN);
				}else {
					g.setColor(Color.BLACK);
				}
				g.fillRect(x*cellWidth, y*cellHeight, cellWidth, cellHeight);
				g.setColor(Color.WHITE);
				g.drawRect(x*cellWidth, y*cellHeight, cellWidth, cellHeight);
			}
		}
		
		drawButtonWithText(g, clearButton, "Clear");
		
	}
	
	public void drawButtonWithText(Graphics g, Rectangle button, String text) {
		drawButton(g, button);
		g.setColor(Color.BLACK);
		g.drawString(text, (int)((button.getX()+button.getWidth()/2)-text.length()*3), (int)(button.getY()+button.getHeight()/2)+3);
	}
	public void drawButton(Graphics g, Rectangle button) {
		g.setColor(Color.WHITE);
		g.fillRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight());
	}
	
	public boolean isButtonClicked(int y, int x, Rectangle button) {
		return(y>button.getY() && y<(button.getY()+button.getHeight()) && x>button.getX() && x<(button.getX()+button.getWidth()));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int xPos = e.getX();
		int yPos = e.getY();
		
		if(isButtonClicked(yPos, xPos, clearButton)) {
			numOfGenerationsInt = 0;
			gofLogic.clearAreas();
			
			repaint();
		}else if(xPos >= 0 && yPos >= 0 && yPos/cellHeight < gofLogic.getCurrentArea().length && xPos/cellWidth < gofLogic.getCurrentArea()[0].length) {
			gofLogic.changeCurrentCellState(yPos/cellHeight, xPos/cellWidth);
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!isRunning && e.getKeyCode() == KeyEvent.VK_SPACE) { //32
			isRunning = true;
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(isRunning && e.getKeyCode() == KeyEvent.VK_SPACE) {
			isRunning = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}