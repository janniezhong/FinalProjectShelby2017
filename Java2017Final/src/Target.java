import java.awt.Color;
import java.awt.Graphics;

public class Target {
	private int xLoc, yLoc, size;
	
	public Target (int x, int y, int size){
		this.size = 50;
		//xLoc = 725;
		//yLoc = 225;
		xLoc = x;
		yLoc = y;
	}
	
	public void drawTarget(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(xLoc, yLoc, size, size-25);
	}
}
