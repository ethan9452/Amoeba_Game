//defines the enemy/computer amoebas
public class OtherCreaturesInThePond {

	private int x; //top left x coordinate of amoeba
	// has to start at edge, so x<0 or x>1000
	private int y; //top left y coordinate of amoeba	
	private String startingSide; // side amoeba starts on
	private int side; //side length
	
	private int posChange;
	
	
	OtherCreaturesInThePond(){
		posChange = 3;
		
		if(Math.random()>.5){
			x = 1400;
			startingSide = "right";
		}
		else{
			x = -300;
			startingSide = "left";
		}
		y = (int)(Math.random()*600); // random int is btwn 0-1, so y is btwn 0 600



		if (Math.random()>.92){ //SPAWN THE MONSTER!
			side = 250;
		}
		else{
			side = (int)(Math.random()* 70+1); // random int btwn 0-1, so side length is btwn 1-61
		}
	
	}

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getSide(){
		return side;
	}

	
	
	public void kill(){
		y = 10000;
	}
	public void move(){
		if(startingSide.equals("right")){
			x = x-posChange;
		}
		else if (startingSide.equals("left")){
			x = x+posChange;
		}
	}
}
