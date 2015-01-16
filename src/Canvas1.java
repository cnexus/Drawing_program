import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Canvas1 extends JFrame implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// This class makes an Applet to be used as the Drawing Canvas 
	public static int side = 500;
	public static Input question;
	public static int drawType = 0;
	public String q;
	public Point start, end;
	public int width, height;
	public boolean mouseClicked;
	public static Graphics gr;
	public enum Colors {black, blue, cyan, darkgray, gray, green, lightgray, magenta, orange, pink, red, white, yellow};
	public static int colorShift;
	public static boolean once;
	public static boolean filled;


	public Canvas1() {
		//Register Mouse events on Canvas1
		this.addMouseListener(this);
		mouseClicked = false;
		start = new Point();
		end = new Point(); 
		width = 0;
		height = 0;
	}

	public static boolean isDefaultColor(String color){
		//Checks if the color parameter is part of the default colors
		if(color.equals("black") || color.equals("blue") || color.equals("cyan") || color.equals("dark gray") || color.equals("gray") 
				|| color.equals("green") || color.equals("light gray") || color.equals("magenta") || color.equals("orange") 
				|| color.equals("pink") || color.equals("red") || color.equals("yellow")){
			return true;
		}else{
			return false;
		}
	}

	public static int toColorNumber(String color){
		color = color.toLowerCase();
		if(!isDefaultColor(color)){
			//System.out.println("Getting substring");
			String rest;
			String beg;
			int pos = 0;
			for(int i = 0; i<= color.length(); i++){
				if(color.substring(i, i+1).equals(" ")){
					pos = i;
					i = color.length();
				}
			}

			rest = color.substring(pos+1);
			beg = color.substring(0, pos);
			if(beg.equals("dark")){
				colorShift = 0; //Sets the color to be changed to dark
			}else if (beg.equals("light")){
				colorShift = 1;
				//System.out.println("lighter");
			}
			color = rest;
		}

		int cNumber = 0;
		/*Variable Tracing
		 * System.out.println("toUp "+ toUp);
		 * System.out.println("rest "+ rest);
		 * System.out.println("color "+ color); */
		//public enum Colors {black, blue, cyan, darkgray, gray, green, lightgray, magents, orange, pink, red, white, yellow};

		if(color.equals("black")){
			cNumber = 1;
		}else if(color.equals("blue")){
			cNumber = 2;
		}else if(color.equals("cyan")){
			cNumber = 3;
		}else if(color.equals("dark gray")){
			cNumber = 4;
		}else if(color.equals("gray")){
			cNumber = 5;
		}else if(color.equals("green")){
			cNumber = 6;
		}else if(color.equals("light gray")){
			cNumber = 7;
		}else if(color.equals("magenta")){
			cNumber = 8;
		}else if(color.equals("orange")){
			cNumber = 9;
		}else if(color.equals("pink")){
			cNumber = 10;
		}else if(color.equals("red")){
			cNumber = 11;
		}else if(color.equals("yellow")){
			cNumber = 12;
		}else{
			return 0;
		}
		return cNumber;
	}

	public static Color strokeColor(int cNum){
		Color stroke = null;

		//public enum Colors {black, blue, cyan, darkgray, gray, green, lightgray, magents, orange, pink, red, white, yellow};
		switch(cNum){
		case 1:	
			stroke = Color.black;
			break;
		case 2:
			stroke = Color.blue;
			break;
		case 3:
			stroke = Color.cyan;
			break;
		case 4:
			stroke = Color.darkGray;
			break;
		case 5:
			stroke = Color.gray;
			break;
		case 6:
			stroke = Color.green;
			break;
		case 7:
			stroke = Color.lightGray;
			break;
		case 8:
			stroke = Color.magenta;
			break;
		case 9:
			stroke = Color.orange;
			break;
		case 10:
			stroke = Color.pink;
			break;
		case 11:
			stroke = Color.red;
			break;
		case 12:
			stroke = Color.yellow;
			break;
		default:
			return Color.black;
		}

		if(colorShift == 0){
			//The zero specifies that the base color to be darkened
			stroke = stroke.darker();
		}else if(colorShift == 1){
			//The one specifies that the base color be brightened
			stroke = stroke.brighter();
		}

		return stroke;
	}

	public void eventOutput(String eventDescription, MouseEvent e){
		System.out.println("Inside the eventoutput");
	}


	public void setStart(Point s){
		start.setLocation(s);
	}

	public void setStart(int x1, int y1){
		start.setLocation(x1, y1);
	}

	public void setEnd(Point e){
		end.setLocation(e);
	}

	public void setEnd(int x1, int y1){
		end.setLocation(x1, y1);
	}


	// Mouse Events relevant to this program
	public void mousePressed(MouseEvent e) {
		setStart(e.getPoint());
		eventOutput("mousePressed", e);
		this.notifyAll();
	}

	public void mouseReleased(MouseEvent e) {
		end = e.getPoint();
		mouseClicked = true;
		System.out.println("Released");
	}

	//public void

	public void paint (Graphics g) {
//		System.out.println("Inside paint");
//		System.out.flush();
		//Begin the actual drawing portion of the program
		Graphics2D g2d = (Graphics2D) g;
		Stroke s = new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
		g2d.setStroke(s);
		question = new Input(); //Defines a new scanner using the Input class
		
		while (Canvas1.drawType != 0) {
			//Loops drawing until the user enters a 0
			q = "Enter the color you wish to use -> ";
			question.setQ(q);
			String color = question.getStringData();
			g2d.setColor(strokeColor(toColorNumber(color))); //Sets the stroke color that the user wants
			//System.out.println("G2D color: " + g2d.getColor());

			q = "Enter the drawing mode -> ";
			question.setQ(q);
			drawType = question.getIntData();
			q = "Filled (1) or unfilled (0) -> ";
			question.setQ(q);
			if(question.getIntData() == 0){
				filled = false;
			}else if(question.getIntData() == 1){
				filled = true;
			}
			
			switch (drawType) {
			case 1:
				//Draws a line from the specified (x1,y1) coordinate to the (x2,y2) coordinate
				q = "Start x -> ";
				question.setQ(q);
				int x1 = question.getIntData();
				q = "Start y -> ";
				question.setQ(q);
				int y1 = question.getIntData();
				q = "End x -> ";
				question.setQ(q);
				int x2 = question.getIntData();
				q = "End y -> ";
				question.setQ(q);
				int y2 = question.getIntData();
				setStart(x1, y1);
				setEnd(x2, y2);
				g2d.drawLine((int) start.getX(),(int) start.getY(),(int) end.getX(),(int) end.getY());
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;

			case 2:
				//Draws a rectangle using the entered start point, width, and height
				q = "Start x -> ";
				question.setQ(q);
				int x = question.getIntData();
				q = "Start y -> ";
				question.setQ(q);
				int y = question.getIntData();
				q = "Width -> ";
				question.setQ(q);
				width = question.getIntData();
				q = "Height -> ";
				question.setQ(q);
				height = question.getIntData();
				g2d.drawRect(x, y, width, height);
				if(filled){
					g2d.fillRect(x, y, width, height);
				}
				break;
				
			case 3:
				//Draws a square using the entered start point and side length
				q = "Start x -> ";
				question.setQ(q);
				x = question.getIntData();
				q = "Start y -> ";
				question.setQ(q);
				y = question.getIntData();
				q = "Side length -> ";
				question.setQ(q);
				int sideLength = question.getIntData();
				g2d.drawRect(x, y, sideLength, sideLength);
				if(filled){
					g2d.fillRect(x, y, sideLength, sideLength);
				}
				break;
				
			case 4:
				//Draws the inputted string at the specified (x,y) coordinates
				q = "Enter the phrase to be drawn -> ";
				question.setQ(q);
				String phrase = question.getStringData();
				q = "Start x -> ";
				question.setQ(q);
				x = question.getIntData();
				q = "Start y -> ";
				question.setQ(q);
				y = question.getIntData();
				g2d.drawString(phrase, x, y);
				break;
				
			case 5:
				//Draws a circle
				q = "Start x -> ";
				question.setQ(q);
				x = question.getIntData();
				q = "Start y -> ";
				question.setQ(q);
				y = question.getIntData();
				q = "Radius -> ";
				question.setQ(q);
				int radius = question.getIntData();
				g2d.drawOval(x, y, radius, radius);
				if(filled){
					g2d.fillOval(x, y, radius, radius);
				}
				break;
				
			case 6:
				//Draws an oval
				q = "Start x -> ";
				question.setQ(q);
				x = question.getIntData();
				q = "Start y -> ";
				question.setQ(q);
				y = question.getIntData();
				q = "Width -> ";
				question.setQ(q);
				width = question.getIntData();
				q = "Height -> ";
				question.setQ(q);
				height = question.getIntData();
				g2d.drawOval(x, y, width, height);
				if(filled){
					g2d.fillOval(x, y, width, height);
				}
				break;

			default:
				g2d.drawString("ERROR: NOT A VALID OPTION", 10, 20);
				g2d.setColor(Color.BLACK);
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Graphics "+ g2d);
				break;
			}
		}

		if(drawType == 0){
			drawType = 1;
		}else if(once == true){
			//this.stop();
			//this.destroy();
		}
	}

	public static void main(String[] args) {
		Canvas1 window = new Canvas1();
		window.setResizable(false);
		window.setVisible(true);
		window.setSize(side, side);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public static void makeRectangle(){

	}

	public static void createGUI(){
		JFrame w = new JFrame("Drawing Canvas");
		//w.setBackground(Color.black);
		//w.setForeground(Color.yellow);
		w.setSize(side, side);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Container c = w.getContentPane();
		//c.add(new Canvas1());
		w.setResizable(false);
		w.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
}
