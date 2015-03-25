import javax.swing.JFrame;
import java.awt.Color;

// Ethan Lo

///////INDEPENDENT PROJECT////////
///////////////////////////////////
/*	 I learned most of how to make games with this website:
 * http://zetcode.com/tutorials/javagamestutorial/
 * That site showed me how to do basic animations, 
 * make objects on the screen move based on keys pressed, and
 * do collision detections. These three attributes were the main 
 * aspects of the game. The other stuff are just extras.
 * 
 * 	I ran into many problems making this game. 
 * 
 * 	First, when I pressed both keys at once and moved to the boundry,
 * the amoeba would sometimes get out of bounds, even though
 * I had methods that were supposed to keep the amoeba in bounds.
 * The original was very complicated. I made it so that it set dx or dy
 * (the change in x or y) to the negative of the original direction, and then 
 * set it to 0. It was very complicated. I realized there was a much easier solution. If
 * the amoeba went out of bounds, I just set the x or y to 0(top/leftmost point on screen) or
 * to the right/bottom-most coordinate on the screen, depending on where the amoeba was going out of bounds.
 * The only downside is that the amoeba shakes when it hits the edges.
 * 
 * 	Another weird problem that i did not resolve is that sometimes, especially right after I change some code, or just start Eclipse,
 * The program doesn't run and just displays a blank screen. Sometimes I open and restart the program
 * a few times and it will still be blank. Then, without changing any code, I run it again and it works perfectly.
 * That doesn't make sense to me because running the same code should give the same result everytime.
 */
///////////////////////////////////
public class Main extends JFrame {


	public Main(){
		add(new Ocean());
		
		setSize(1000,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("RECTANGLE AMOEBA");
		setResizable(false);
		setVisible(true);
	//	setBackground(Color.cyan);
	}


	public static void main(String[] args) {
		new Main();
	}

}
