import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.KeyEvent;



//Checklist/Goals for game;
/*
 *Amoeba that gets bigger as it eats smaller  amoebas
 *dies if it touches/ tries to eat a bigger amoeba
 *computer amoebas floating across screen, all edible
 *amoeba grows as it eats more
 *computer amoebas spawn automatically
 *collision detection so we kno if amoebas intersect or not (for eating)
 *different speed and sized amoebas
 *
 *
 *plan for spawning
 *spawn all at once, but have most of them not moving till later
 *
 *
 *what i learned, or at least think i learned
 *	super.paint(g):  erases previous painting??
 */


/////////////////////////////////////////////////////////
/*
 * This Ocean class is where the most of the game computations take place. I named it Ocean because it's like the game 
 * takes place in this class.
 */
public class Ocean extends JPanel implements ActionListener {
	private static Timer timer;
	private Player player; //the human controlled amoeba
	private OtherCreaturesInThePond[] otherCreatures; //an array of Objects that are the computer amoebas
	private int population = 1500;
	private int ttt = 0; //add one each time timer fires...for actions slower than original timer speed
	private int spawnCount=0; // this is here because the whole array of computer amoebas is created at once, but I dont want them all to start moving onto the screen at once..
	private static int stage = 1; // stage 1 intro; stage 2 and 3 instructions; stage 4 game; stage 5 game over
	private boolean endcry = false; // if true prints "NOO" onto the screen, happens when the amoeba gets eaten
	private int eatCount = 0; //count of how many amoebas the player has eaten
	private int finalSize = 1;
	Double rand = Math.random(); // this is to display a different Game Over message each time

//////////////THE CONSTRUCTOR///////////////
	public Ocean(){
		addKeyListener(new TAdapter()); //the key Listener was put in its own private class
		setFocusable(true); //pretty sure this makes it so the computer "sees" the keyboard input, because without it, there's no movement.
		setBackground(Color.cyan);
		setDoubleBuffered(true);
		
		player = new Player();
		otherCreatures = new OtherCreaturesInThePond[population];

		timer = new Timer(4, this); //timer fires every 8 milliseconds, 8/1000 sec
		spawnAllCreatures();
		//timer.start();
		repaint();
	}

	
	public static void stagePlusOne(){ // this happens when SPACE is pressed
		//these stages are the intro and tutorial
		if(stage == 1 ||stage == 2||stage == 3){
			stage++;
		}
		//stage 4 is when the game is playing
		if(stage == 4){
			timer.start();
		}
		//this is the game over 
		if(stage == 5){
			stage = 1;
			timer.stop();
		}
	}
	private int yo = 1; //for the "nooo" at end of game

	//////////////PAINTING////////////////////
	public void paint(Graphics g){
		if(stage == 1){ // intro
			player.revive();
			super.paint(g);

			eatCount = 0; // why is this in paint method?

			setBackground(Color.magenta);
			Font introFontBig = new Font("Impact" , Font.BOLD, 70);
			Font introFontSmall = new Font("Arial" , Font.BOLD, 20);
			g.setFont(introFontBig);
			g.setColor(Color.green);
			g.drawString("Square Amoeba 2013", 150, 300);
			g.setColor(Color.black);
			g.setFont(introFontSmall);
			g.drawString("Welcome To", 400, 210);
			g.drawString("by Ethan Lo", 700,350);
			g.drawString("--press space to continue--", 330, 420);

			repaint();
			//System.out.println("1");

		}
		if(stage == 2){ //tutorial
			super.paint(g);
			setBackground(Color.cyan);
			Font tutFont = new Font("Arial", Font.PLAIN, 20);
			g.setFont(tutFont);
			g.drawString("This is you", 240, 120);

			g.setColor(Color.white);
			g.fillRect(250, 150, 80, 80);
			g.setColor(Color.yellow); // make an egg amoeba :O
			g.fillOval(250+20, 150+20, 40, 40);

			g.setColor(Color.black);
			g.drawString("Your goal in life is to become the biggest amoeba in the pond", 190, 320);

			g.drawString("--press space to continue--", 340, 520);
			repaint();

			//System.out.println("2");
		}
		if(stage == 3){
			super.paint(g);
			Font tutFont = new Font("Arial", Font.PLAIN, 20);
			g.setFont(tutFont);

			g.drawString("These are your friends", 250, 120);

			g.setColor(Color.green);
			g.fillRect(250, 150, 40, 40);
			g.fillRect(300, 170, 30, 30);
			g.fillRect(230, 180, 50, 50);
			g.setColor(Color.YELLOW);
			g.fillOval(250+10, 150+10, 20, 20);
			g.fillOval(300+(30/4), 170+(30/4),10, 10);
			g.fillOval(230+(50/4), 180+(50/4), 25, 25);

			g.setColor(Color.black);
			g.drawString("You get bigger when you eat on of them", 250, 280);
			g.drawString("But beware, a bigger amoeba always eats a smaller amoeba!", 200, 350);
			g.drawString("This is the end of the tutorial", 250, 420);
			g.drawString("--press space to PLAY--", 340, 520);
			repaint();
			//System.out.println("3");
		}
		if(stage == 4){// game
			//System.out.println("4");
			super.paint(g);
			setBackground(Color.cyan);
			if(player.getAlive() == true){
				drawPlayer(g);
			}
			drawOthers(g);
			g.setColor(Color.black);
			g.drawString("Aboebas eaten: " + eatCount, 870, 550);
			g.drawString("Size : " + player.getSide()*player.getSide(), 870, 535);
			if(player.getSide() > 250){
				g.setColor(Color.black);
				g.drawString("Congrats, you are now the biggest amoeba in the pond", 300, 550);
			}
		}

		if(stage == 6){
			Font NOOOO = new Font("Arial", Font.BOLD, 120);
			g.setFont(NOOOO);
			g.drawString("NOOOOOOO", 200, 280);
			yo ++;
			if(yo == 5){
				try {
					Thread.sleep(200);
				} catch (InterruptedException ie) {
					//Handle exception
				}
				stage = 5;
				repaint();
			}
		}

		if(stage == 5){//game over!!!
			yo = 1;
			super.paint(g);

			timer.start();
			if(ttt == 1000){
				ttt = -350;
			}
			Font endFontBig = new Font("Impact" , Font.BOLD, 100);
			Font endFontSmall = new Font("Arial", Font.PLAIN, 25);
			Font endFontMed = new Font("Arial", Font.BOLD, 30);

			g.setFont(endFontBig);
			g.drawString("GAME OVER" , ttt, 250);

			g.setFont(endFontSmall);
			if(rand <.3){
			g.drawString("Looks like you got eaten!!", 350, 350);
			}
			else if(rand > .6){
				g.drawString("It's an amoeba eat amoeba world out there man", 350, 350);
			}
			else if(rand < .6 && rand > .5){
				g.drawString("There are always bigger amoebaa in the pond", 350, 350);
			}
			else{
				g.drawString("Looks like you bit off more than you could swallow!", 350, 350);
			}
			
			g.drawString("FINAL SCORE",400, 400);
			g.drawString("--press space to restart--", 340, 520);

			g.setColor(Color.blue);
			g.setFont(endFontMed);
			g.drawString("Aboebas eaten: " + eatCount, 250, 450);
			g.drawString("Size : " + finalSize, 540, 450);


			repaint();
			g.dispose();

			judgementDay();
			//	System.out.println("5");

		}
		Toolkit.getDefaultToolkit().sync(); // Someone said this makes the game smoother..I don't know what it does, seems to make no differnece?
		g.dispose();//I think this saves memory, but I'm not sure.
	}


	public void drawPlayer(Graphics g){
		g.setColor(Color.white);
		g.fillRect(player.getX(), player.getY(), player.getSide(), player.getSide());
		g.setColor(Color.yellow); // make an egg amoeba 
		g.fillOval(player.getX()+(player.getSide()/4), player.getY()+(player.getSide()/4), player.getSide()/2, player.getSide()/2);
	}


	public void drawOthers(Graphics g){
		for(int x = 0; x<population; x++){
			g.setColor(Color.green);
			g.fillRect(otherCreatures[x].getX(),otherCreatures[x].getY(), otherCreatures[x].getSide(), otherCreatures[x].getSide());
			g.setColor(Color.YELLOW);
			g.fillOval(otherCreatures[x].getX()+(otherCreatures[x].getSide()/4), otherCreatures[x].getY()+(otherCreatures[x].getSide()/4), otherCreatures[x].getSide()/2,otherCreatures[x].getSide()/2);
		}
	}


///////////////////COMPUTER AMOEBA METHODS//////////////
	public void spawnAllCreatures(){
		//this fills the whole array with Amoebas
		for(int x = 0; x<population; x++){
			otherCreatures[x] = new OtherCreaturesInThePond();
		}
	}

	public void actionPerformed(ActionEvent e){ // for timer
		//	System.out.println(player.getSide());

		if(player.getAlive() == true){
			checkBounds();
			checkCollision();	
			player.move();
		}
		for(int x = 0; x<spawnCount; x= x+4){ // all the cells are "alive" but only the ones that are under the spawnCount are able to move
			otherCreatures[x].move();
			otherCreatures[x+1].move(); 
			otherCreatures[x+2].move();
			otherCreatures[x+3].move();
			// we spawn 4 at a time
		}
		repaint();
		spawnIncrease();
		ttt++;
	}

	public void spawnIncrease(){
		//I dont want to spawn everytime the timer fires, so i use ttt, a variable that increases with the timer count
		if ((ttt*8)%100 == 0){ // which means every .1 second
			spawnCount++;
		}
	}

///////////////////COLLISIONS and EATING/////////////////////////////
	boolean firstContact = true; // you dont want to keep eating every pixel you move
	public void checkCollision(){
		Rectangle p = new Rectangle(player.getX(), player.getY(), player.getSide(), player.getSide()); //one bounding rectangle for the player
		// make a new rectangle and check for each computer amoeba
		for(int x = 0; x < population ; x++){
			Rectangle c = new Rectangle(otherCreatures[x].getX(), otherCreatures[x].getY(), otherCreatures[x].getSide(), otherCreatures[x].getSide());
			if (p.intersects(c)&& firstContact == true && player.getAlive() == true){
				firstContact = false;
				if(player.getSide()>=otherCreatures[x].getSide()){
					iEat(x);
					otherCreatures[x].kill();
				}

				if(player.getSide()<otherCreatures[x].getSide()){
					finalSize= player.getSide()*player.getSide();
					repaint();
					player.kill();
					stage = 6;
					ttt = 0;
					rand = Math.random();

				}
			}
			firstContact = true;
		}
	}

	public void iEat(int x){
		eatCount++;
		int newSize = 1; 
		newSize = (int)Math.sqrt((((player.getSide())*(player.getSide()))+((otherCreatures[x].getSide())*(otherCreatures[x].getSide()))));
		player.setSide(newSize);
		//this method makes it so that when u eat, ur not just adding the sides, ur actually adding the actual area.
	}


	public void checkBounds(){
		if(player.getX()<0 && player.getDirection().equals("A")){
			player.setDX(0);
		}

		if(player.getX()>993-player.getSide() && player.getDirection().equals("D")){
			player.setDX(0);
		}

		if(player.getY()<0 && player.getDirection().equals("W")){
			player.setDY(0);
		}

		if(player.getY()>572-player.getSide() && player.getDirection().equals("S")){
			player.setDY(0);
		}

		if(player.getX()<-1){ // prevents the two key pressed --> escape bounds glitch
			player.setX(0);
		}

		if(player.getX()>994-player.getSide()){ 
			player.setX(992-player.getSide());
		}

		if(player.getY()<0){ 
			player.setY(0);
		}

		if(player.getY()>573-player.getSide()){ 
			player.setY(572-player.getSide());
		}

	}


	private class TAdapter extends KeyAdapter { /// for keys

		public void keyReleased(KeyEvent e){
			player.keyReleased(e);

		}

		public void keyPressed(KeyEvent e){
			player.keyPressed(e);

			
		}
	}

	public void judgementDay(){// kills and restarts all amoebas
		spawnCount = 0;
		player = null;
		for(int x = 0; x<population; x++){
			otherCreatures[x] = null;

			player = new Player();
			for(x = 0; x<population; x++){
				otherCreatures[x] = new OtherCreaturesInThePond();
			}

		}
	}
}
