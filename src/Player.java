
import java.awt.event.KeyEvent;




public class Player {// defines player amoeba
	private int x; //top left x coordinate of amoeba
	private int y; //top left y coordinate of amoeba	
	private int side; //side length
	private boolean alive; // live of dead?

	private int dx = 0; //change in x and y
	private int dy = 0;
	private String direction = "blahblahblahhahahahahahahaha"; // direction, part of the boundry methods
	
	private int posChange;

	Player(){
		x=34656346;
		y=35535356;
		side=10;
		alive = true;
		
		posChange = 5;//can change if different computers have different graphics/pixels
	}

	//getting methods
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getSide(){
		return side;
	}

	public String getDirection(){
		//System.out.println(direction);
		return direction;
	}

	public boolean getAlive(){
		return alive;
	}


	//setting methods
	public void setDX(int x){// set change
		dx = x;
	}

	public void setDY(int y){
		dy = y;
	}

	public void setX(int c){ // set actual location
		x = c;
	}

	public void setY(int c){ // set actual location
		y = c;
	}

	public void setSide(int c){
		side = c;
	}
	public void kill(){
		alive = false;
		side = 0;
		x = 1000000;
		y = 100000;
	}
	public void revive(){
		alive = true;
		side = 10;
		x = 300;
		y = 300;
	}


	//movement methods
	public void move(){
		x = x+dx;
		y = y+dy;
		//System.out.println("X");
		//	System.out.println(x);

	}

	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE){
			Ocean.stagePlusOne();	
		}

		if (key == KeyEvent.VK_W ||key == KeyEvent.VK_UP ){
			dy =-posChange;
			direction = "W";
		}

		if (key == KeyEvent.VK_A||key == KeyEvent.VK_LEFT){
			dx =-posChange;
			direction = "A";
		}

		if (key == KeyEvent.VK_S||key == KeyEvent.VK_DOWN){
			dy = posChange;
			direction = "S";
		}

		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
			dx = posChange;
			direction = "D";
		}


	}

	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W||key == KeyEvent.VK_UP){
			dy = 0;
		}

		if (key == KeyEvent.VK_A||key == KeyEvent.VK_LEFT){
			dx = 0;
		}

		if (key == KeyEvent.VK_S||key == KeyEvent.VK_DOWN){
			dy = 0;
		}

		if (key == KeyEvent.VK_D|| key == KeyEvent.VK_RIGHT){
			dx = 0;
		}

	}

}
