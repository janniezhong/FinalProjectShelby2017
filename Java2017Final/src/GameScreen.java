import java.awt.Color.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

//graphics of the game screen, background 
//has character, slingshot, target, + the menu w/ the helper blocks

public class GameScreen extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

	private Slingshot slingshot;
	protected Character character;
	private Target target;
	private Image charImg;
	private int charSize;
	private TimeTracker time;

	private boolean helperObj;
	private int xClick, yClick;
	private int objWidth, objHeight;
	private int slingX, slingY, dragX, dragY;
	private static boolean slingPressed, slingReleased=false, slingInitTimeSet;  // slingReleased = sling clicked, then released 
	
	protected ArrayList<HelperObject> helpers;
	protected ArrayList<Obstacle> obstacles;
	private int level;
	private LevelOne level1;
	private LevelTwo level2;
	private LevelThree level3;
	private LevelFour level4;
	private LevelFive level5;
	
	private int t;
	
	private int x, y;
	
	private boolean isEditable;
	private AllScreen as;
	// still need to call as.changeScreen("Results") somewhere



	public GameScreen (AllScreen as) {
		
		t = 0;
		Timer clock = new Timer(16, this);
		clock.start();
		
		this.as = as;
		helperObj = false;
		objWidth = 60;
		objHeight = 5;

		slingPressed = false;
		slingX = 65;
		slingY = 370;
		dragX = slingX;
		dragY = slingY;

		charImg = (new ImageIcon("shelbyface.png").getImage());
		slingshot = new Slingshot();
		character = new Character(40, 340, charSize, charSize + 10, charImg, slingshot);
		time = new TimeTracker(character);
		target = new Target(550, 315, 80);		
		charSize = 50;

		isEditable = true;

		Color SKYBLUE = new Color(175, 238, 238);
		setBackground(SKYBLUE);

		level1 = new LevelOne();
		level2 = new LevelTwo();
		level3 = new LevelThree();
		level4 = new LevelFour();
		level5 = new LevelFive();

		helpers = new ArrayList<>();

	}

	public void paint(Graphics g) {
		super.paint(g);

		int width = getWidth();
		int height = getHeight();

		//platform for character
		Color PALEGREEN = new Color(160, 255, 100);
		g.setColor(PALEGREEN);
		g.fillRect(0, 400, 150, 200);	

		System.out.print("pai....xxted\n");

		//character
		if(slingPressed == true) {
			character.draw(g, dragX - 23, dragY - 23, charSize, charSize + 10);
		}
		else{
			x = character.getX();
			y = character.getY();
			character.draw(g, x, y, charSize, charSize+10);

			System.out.print("draw x " + x + "  y " + y  + "\n");
			
		}
		//slingshot
		//g.drawImage(slingImg, 60, 300, 70, 100, this);
		Color NEWYELLOW = new Color(241, 221, 56);
		g.setColor(NEWYELLOW);
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(5));
		if(slingPressed == true) {
			g2.drawLine(95,  340, dragX, dragY);
		}
		else
			g2.drawLine(95, 340, slingX, slingY	);
		//slingshot body
		Color BROWN = new Color(185, 155, 75);
		g.setColor(BROWN);
		g.fillRect(90, 330, 10, 70);

		//platform for target
		g.setColor(PALEGREEN);
		g.fillRect(500, 300, 150, 300);

		//Target
		target.drawTarget(g);

		//screen w/ all the thing
		Color LIGHTGRAY = new Color(211, 211, 211);
		g.setColor(LIGHTGRAY);
		g.fillRect(650, 0,  150, height);
		g.setColor(Color.GRAY);
		Font newFont = new Font("SansSerif", Font.PLAIN, 15);
		g.setFont(newFont);
		g.drawString("Click and drop", 660, 25);
		g.drawString("to use the blocks", 658, 40);



		//Obstacles

		for (int i = 0; i < obstacles.size(); i++){
			Obstacle obstacle = obstacles.get(i);
			obstacle.drawObstacle(g);
		}


		//button to change editability
		g.setColor(Color.GREEN);
		g.fillRect(700, 400, 60, 30);
		g.setColor(Color.BLACK);
		g.drawString("Editing", 705, 420);

		if (isEditable){

			//Helper Objects
			if (helperObj){
				g.setColor(Color.CYAN);
				g.fillRect(700, 150, objWidth, objHeight);

			}
			else {
				g.setColor(Color.WHITE);
				g.fillRect(700, 150, objWidth, objHeight);

			}

		} else {
			g.setColor(Color.RED);
			g.fillRect(690, 400, 80, 30);
			g.setColor(Color.BLACK);
			g.drawString("No Editing", 695, 420);
		}
		for (int i = 0; i < helpers.size(); i++){
			HelperObject obj = helpers.get(i);
			obj.draw(g, Color.WHITE);

		}

	}

	public int setLevel(int lvl){

		level = lvl;

		if (level == 1){

			obstacles = level1.typeOfObstacles();

		} else if (level == 2){

			obstacles = level2.typeOfObstacles();

		} else if (level == 3){

			obstacles = level3.typeOfObstacles();

		} else if (level == 4){

			obstacles = level4.typeOfObstacles();

		} else {

			obstacles = level5.typeOfObstacles();

		}
		return level;

	}
	
	
	public void run() {
		
		while(true){
		
			// CHANGE
			if (AllScreen.panel.getSlingReleased()) {
				
				if (!slingInitTimeSet) {
					// set start time of fling, so that we can compute diff correctly
					AllScreen.panel.character.setInitialTime(TimeTracker.getTime());
					slingInitTimeSet = true;
				}
								
				System.out.println("launch....");

				AllScreen.panel.character.launch(TimeTracker.getTime());
				AllScreen.panel.character.checkHasCollided(AllScreen.panel.helpers, AllScreen.panel.obstacles, 800, 600);
				
				if(character.getHasDied() == true) {
					AllScreen.panel.setSlingReleased(false);  // only release once
					as.changeScreen("Results");
				}
			}
			
			repaint();
			System.out.println("repainting....");
			
			try {
				Thread.sleep(160);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
	}
	

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	//right click to rotate? or have different helper obj? ignore rotation for now
	public void mousePressed(MouseEvent e) {

		xClick = e.getX();
		yClick = e.getY();
		int button = e.getButton();

		if(button == MouseEvent.BUTTON1) {
			if(helperObj == false) {

				if(xClick >= 700 && xClick <= 700 + objWidth && yClick >= 150-10 && yClick <= 150 + objHeight + 10){

					helperObj = true;
				}


			}
			//if true, which means have already clicked on a block
			else {
				if (xClick < 650){
					if (helpers.size() < 10){
						helpers.add(new HelperObject(xClick, yClick, objWidth, objHeight));
						helperObj = false;
					}

				}

			}


			if (isEditable == false){
				if(slingPressed == false) {
					//if it's approximately near the slingshot bc too lazy for precise coordinates lol
					if(xClick>=50 && xClick<=120 && yClick>=300 && yClick<=400) {
						slingPressed = true;
						dragX = xClick;
						dragY = yClick;
					}
					else {

					}
				}
			}

		}
		

		if (xClick >= 670 && xClick <= 700 + 60 && yClick >= 400 && yClick <= 400 + 30){
			isEditable = false;

			repaint();
		}


	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (slingPressed){
			slingPressed = false;	
			slingInitTimeSet = false;  // mark if we have set initial time yet
			int x = e.getX() - 23;
			int y = e.getY() - 23;
			slingshot.setXY(x, y);
			character.setXY(x,  y);
			
			slingReleased = true;
			
//			int heightI = e.getY();
//			int heightF = helpers.get(character.getIndexOfCurrObj()).getY();
//			
//			slingshot.setObjectHeight(heightI, heightF);
//			
//			
//			
//			
//			character.launch(time.getTime());

		
		}

		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (isEditable == false){
			if (slingPressed == true) {
				if (e.getX() < 90) {
					dragX = e.getX();
					dragY= e.getY();
					repaint();
				}
			}
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public static boolean getSlingReleased() {
		return slingReleased;
	}
	public static void setSlingReleased(Boolean b) {
		slingReleased = false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		t++;
		//while(true){
			
			// CHANGE
			if (AllScreen.panel.getSlingReleased()) {
				
				if (!slingInitTimeSet) {
					// set start time of fling, so that we can compute diff correctly
					AllScreen.panel.character.setInitialTime(TimeTracker.getTime());
					slingInitTimeSet = true;
				}
								
				System.out.println("launch....");

				AllScreen.panel.character.launch(TimeTracker.getTime());
				AllScreen.panel.character.checkHasCollided(AllScreen.panel.helpers, AllScreen.panel.obstacles, 800, 600);
				
				if(character.getHasDied() == true) {
					AllScreen.panel.setSlingReleased(false);  // only release once
					as.changeScreen("Results");
				}
			}
			
			repaint();
			System.out.println("repainting....");
			
			try {
				Thread.sleep(160);
			} catch (InterruptedException o) {
				// TODO Auto-generated catch block
				o.printStackTrace();
			}
		
		}
		
	//}

}
