package com.timoteus3000.logic;

/**
 * GOFLogic - Klasse
 * 
 * @author T.Holzapfel
 *
 */
public class GOFLogic {
	
	//Deklaration
	private int numRows;
	private int numColumns;
	private byte[][] currentArea;
	private byte[][] newArea;
	
	/**
	 * Konstruktor der GOFLogic - Klasse
	 */
	public GOFLogic() {
		setUpAreas();
	}
	
	/**
	 * Setzt die Standardwerte die benoetigt werden. <p>
	 * (Zeilen- und Spaltenanzahl). <p>
	 * 
	 */
	public void setUpAreas() {
		
		numRows = 50;
		numColumns = 50;
		
		clearAreas();
	}
	
	/**
	 * Setzt die Standardwerte die benoetigt werden. <p>
	 * (Zeilen- und Spaltenanzahl). <p>
	 * 
	 * @param numRows - Anzahl der Zeilen.
	 * @param numColumns - Anzahl der Spalten.
	 */
	public void setUpAreas(int numRows, int numColumns) {

		this.numRows = numRows;
		this.numColumns = numColumns;

		clearAreas();
	}
	
	/**
	 * Absolviert einen Zyklus (Generation, Runde), indem alle Regeln auf die Zellen des byte-Arrays - newArea angewendet werden. <p>
	 * 
	 */
	public void completeOneCycle() {
		
		for(int y = 0; y < newArea.length; y++) {
			for(int x = 0; x < newArea[y].length; x++) {
				
				int currentNeighborhood = getNeighborhood(y, x);
				
				if(isAlive(y, x) && currentNeighborhood < 2) { // Zelle stirbt an Einsamkeit REGEL.1
					newArea[y][x] = 0;
				}else if(isAlive(y, x) && (currentNeighborhood == 2 || currentNeighborhood == 3)) { // Bleibt am Leben REGEL.2
					newArea[y][x] = 1;
				}else if(isAlive(y, x) && currentNeighborhood > 3) { // Stirbt an Ueberbevoelkerung REGEL.3
					newArea[y][x] = 0;
				}else if(!isAlive(y, x) && currentNeighborhood == 3) { // Neu geboren REGEL.4
					newArea[y][x] = 1;
				}
			}
		}
		
		currentArea = getCopy(newArea);
		
		waitAMoment(150);
	}
	
	/**
	 * Gibt die Anzahl der lebenden Nachbarn, der aktuellen Zelle, zurueck. <p>
	 * (Exklusive des Status der aktuellen Zelle. <p>
	 * 
	 * @param yPos - y-Koordinate der aktuellen Zelle im aktuellen Spielfeld (currentArea).
	 * @param xPos - x-Koordinate der aktuellen Zelle im aktuellen Spielfeld (currentArea).
	 * @return Anzahl der lebendigen Nachbarn.
	 */
	private int getNeighborhood(int yPos, int xPos) {
		int neighborhood = 0;
		
		byte yArPos[] = {-1, -1, -1, 0, 0, 1, 1, 1};
		byte xArPos[] = {-1, 0, 1, -1, 1, -1, 0, 1};
		
		for(int i = 0; i < yArPos.length; i++) {
			if(isAlive(yPos+yArPos[i], xPos+xArPos[i]))
				neighborhood++;
		}

		return neighborhood;
	}
	
	/**
	 * Prueft ob die jeweilige Zelle, im aktuellen Spielfeld (currentArea), am leben ist. <p>
	 * 
	 * @param yPos - y-Koordinate im aktuellen Spielfeld (currentArea).
	 * @param xPos - x-Koordinate im aktuellen Spielfeld (currentArea).
	 * @return Wahrheitswert, je nach Status der Zelle.
	 */
	private boolean isAlive(int yPos, int xPos) {
		if(yPos < 0)
			yPos = currentArea.length-1;
		else if(yPos >= currentArea.length)
			yPos = 0;
		
		if(xPos < 0)
			xPos = currentArea[0].length-1;
		else if(xPos >= currentArea[0].length)
			xPos = 0;
		
		if(currentArea[yPos][xPos] == 1)
			return true;
		return false;
	}
	
	/**
	 * Gibt eine Kopie des uebergebenen Arrays zurueck. <p>
	 * 
	 * @param area - Array welches kopiert werden soll.
	 * @return Kopie des uebergebenen Arrays
	 */
	private byte[][] getCopy(byte[][] area){
		byte[][] copyArea = new byte[area.length][area[0].length];
		for(int yPos = 0; yPos < copyArea.length; yPos++) {
			for(int xPos = 0; xPos < copyArea[yPos].length; xPos++) {
				copyArea[yPos][xPos] = area[yPos][xPos];
			}
		}
		return copyArea;
	}
	
	/**
	 * Hilfsmethode, damit das Programm die jeweilige Millisekundenzahl wartet. <p>
	 * 
	 * @param millis - Wartezeit in Millisekunden.
	 */
	private void waitAMoment(int millis) {
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() <= startTime + millis) {
		}
	}
	
	/**
	 * Wechselt das angegebene Feld (im aktuellen und im neuen Spielfeld) auf den jeweils kontrahierenden Status.<p>
	 *  
	 * @param yPos - y-Koordinate im aktuellen und neuen Spielfeld (currentArea, newArea).
	 * @param xPos - x-Koordinate im aktuellen und neuen Spielfeld (currentArea, newArea).
	 */
	public void changeCurrentCellState(int yPos, int xPos) {
		if(currentArea[yPos][xPos] == 1) {
			currentArea[yPos][xPos] = 0;
		}else {
			currentArea[yPos][xPos] = 1;
		}
		
		newArea[yPos][xPos] = currentArea[yPos][xPos];
	}
	
	/**
	 * Initialisiert die beiden Arrays (currentArea, newArea) neu. <p>
	 * (Standardfuellwert ist 0.) <p>
	 */
	public void clearAreas() {
		currentArea = new byte[numRows][numColumns];
		newArea = new byte[numRows][numColumns];
	}
	
	/**
	 * Gibt das zweidimensionale byte-Array, mit dem aktuellen Status der enthaltenen Zellen, zurueck. <p>
	 * 
	 * @return byte - Zweidimensionales byte-Array (aktuelles Spielfeld).
	 */
	public byte[][] getCurrentArea() {
		return currentArea;
	}
}