import javax.swing.JFrame;

public class Fling {
	
	public static void main(String[] args)
	{
		JFrame w = new JFrame("Game Screen!!");
		w.setBounds(100, 100, 800, 600);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GameScreen panel = new GameScreen();
		w.add(panel);
		w.setResizable(false);
		w.setVisible(true);

		panel.addMouseListener(panel);
		panel.addMouseMotionListener(panel);

	}
	
}
