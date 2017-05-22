import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class LevelScreen extends Screen {

	private Image level1, level2, level3, level4, level5;

	public LevelScreen() {
		Color SKYBLUE = new Color(175, 238, 238);
		setBackground(SKYBLUE);

		level1 = (new ImageIcon("pusheen1.png").getImage());
		level2 = (new ImageIcon("pusheen2.png").getImage());
		level3 = (new ImageIcon("pusheen3.png").getImage());
		level4 = (new ImageIcon("pusheen4.png").getImage());
		level5 = (new ImageIcon("cheesecakeImg.png").getImage());		
	}

	public void paintComponent(Graphics g) {
		Font newFont = new Font("Arial", Font.PLAIN, 20);
		g.setFont(newFont);
		g.drawImage(level1, 50, 200, 200, 150, this);
		g.drawString("Level 1", 100, 300);
		g.drawImage(level2, 180, 80, 200, 200, this);
		g.drawString("Level 2", 228, 180);
		g.drawImage(level3, 250, 400, 200, 150, this);
		g.drawString("Level 3", 320, 475);
		g.drawImage(level4, 390, 230, 200, 180, this);
		g.drawString("Level 4", 440, 355); 
		g.drawImage(level5, 550, 120, 200, 150, this);
		g.drawString("Level 5", 630, 225);

	}

	public void mousePressed(MouseEvent e) {
		int xClick = e.getX();
		int yClick = e.getY();

		if(xClick>=50 && xClick<=250 && yClick>=200 && yClick<=350)
			Fling.Screen = 1;		
		else if(xClick>=180 && xClick<=380 && yClick>=90 && yClick<=280)
			Fling.Screen = 2;
		else if(xClick>=250 && xClick<=450 && yClick>=400 && yClick<=650)
			Fling.Screen = 3;
		else if(xClick>=390 && xClick<=590 && yClick>=230 && yClick<= 410)
			Fling.Screen = 4;
		else if(xClick>=550 && xClick<= 750 && yClick>=120 && yClick<=270)
			Fling.Screen = 5;
	}
}