import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class TitleScreen extends Screen {

	private Image titleImg;
	
	private AllScreen as;

	public TitleScreen(AllScreen as) {
		this.as = as;
		titleImg = (new ImageIcon("FlingTitleScreen.png").getImage());
	}

	public void paintComponent(Graphics g) {
		g.drawImage(titleImg, 0, 0, 800, 600, this);

		Font newFont = new Font("Arial", Font.BOLD, 20);
		g.setFont(newFont);
		g.drawString("Click on the unicorn Pusheen to continue!", 185, 330);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int xClick = e.getX();
		int yClick = e.getY();

		if(xClick >= 460 && xClick <= 700 && yClick >= 410 && yClick <= 550) {
			//AllScreen.SCREEN = 7;
			as.changeScreen("Level");
		}
	}


}