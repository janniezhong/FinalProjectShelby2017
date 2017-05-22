import java.awt.CardLayout;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

public class AllScreen extends JPanel{
	
	public static CardLayout cards;
	private static TitleScreen titlePanel;
	private static ResultScreen resultPanel;
	private static LevelScreen levelPanel;
	public static GameScreen panel;
	public static int SCREEN;
	public static int LEVEL;

	public AllScreen(){
		LEVEL = 1;
		
		cards = new CardLayout();
		
		titlePanel = new TitleScreen(this);
		resultPanel = new ResultScreen(this);
		levelPanel = new LevelScreen(this);
		
		panel = new GameScreen(this);
		panel.setLevel(LEVEL);
		System.out.println(LEVEL + "");
		
		this.setLayout(cards);
	    this.add(titlePanel, "Title");
	    this.add(levelPanel, "Level");
	    this.add(panel, "Game");
	    this.add(resultPanel, "Results");
	    
	    // cards.show(this, "Title");

		panel.addMouseListener(panel);
		panel.addMouseMotionListener(panel);
		titlePanel.addMouseListener(titlePanel);
		resultPanel.addMouseListener(resultPanel);
		levelPanel.addMouseListener(levelPanel);
	
		
	}
	
	public void changeScreen(String name) {
		cards.show(this, name);
		
	}

	
}
