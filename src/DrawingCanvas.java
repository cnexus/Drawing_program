import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;


public class DrawingCanvas{
	public static Dimension frameSize = new Dimension(962, 600);
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI(){
		DrawingArea drawingArea = new DrawingArea();
		ButtonPanel buttonPanel = new ButtonPanel(drawingArea);
		//buttonPanel.setBackground(Color.DARK_GRAY.brighter());
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Drawing Canvas v1.3.5");
		Image icon = Toolkit.getDefaultToolkit().createImage("images/icon_small.png");
		frame.setIconImage(icon);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = frame.getContentPane();
		c.add(drawingArea);
		//		JMenu menu = new JMenu();
		//		menu.setVisible(true);
		//		menu.setBackground(Color.RED);
		//		menu.setSize(200, frameSize.height);
		//		c.add(menu, BorderLayout.CENTER);
		c.add(buttonPanel, BorderLayout.SOUTH);
		frame.setSize(frameSize);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	static class ButtonPanel extends JPanel implements ActionListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private DrawingArea drawingArea;
		//		private static Color fillColor;
		private static Color lastColor;
		private static int lastThickness = 1;
		private JButton lastButton;
		private JButton blackButton;
		private boolean skipFirstTime = true;
		public static boolean filled = false;
		private static Color ORANGE = new Color(255, 145, 0);
		private static Color PURPLE = new Color(102, 51, 153);
		private static Color INDIGO = new Color(51, 6, 150);
		private Random generator = new Random();
		private int max = 256;
		public ImageIcon eraserIcon;
		public JButton eraser;
		public boolean done;

		public ButtonPanel(DrawingArea drawingArea){
			this.drawingArea = drawingArea;
			this.setLayout(new GridLayout(2, 5));
			eraserIcon = new ImageIcon("images/eraser-img.jpg", "icon");
			blackButton = createColorButton(Color.BLACK);
			add(createTextButton("Darker"));
			add(createTextButton("Lighter"));
			add(createTextButton("Thicker"));
			add(createTextButton("Thinner"));
			add(createTextButton("Random Color!!"));

			JPanel colorPanel1 = new JPanel();
			JPanel colorPanel2 = new JPanel();
			colorPanel1.setLayout(new GridLayout(1,4));
			colorPanel2.setLayout(new GridLayout(1,4));
			colorPanel1.add(blackButton);
			colorPanel1.add(createColorButton(Color.RED));
			colorPanel1.add(createColorButton(ORANGE));
			colorPanel1.add(createColorButton(Color.YELLOW));
			colorPanel2.add(createColorButton(Color.GREEN));
			colorPanel2.add(createColorButton(Color.BLUE));
			colorPanel2.add(createColorButton(INDIGO));
			colorPanel2.add(createColorButton(PURPLE));
			add(colorPanel1);
			add(colorPanel2);

			add(createShapeButton("Rectangle"));
			add(createShapeButton("Square"));
			add(createShapeButton("Oval"));
			add(createShapeButton("Circle"));
			add(createShapeButton("Line"));
			//JPanel lastTwoPlusEraser = new JPanel();
			//lastTwoPlusEraser.setLayout(new GridLayout(1,));
			add(createTextButton("Filled"));
			add(createTextButton("Clear Drawing"));
			eraser = new JButton();
			eraser.setActionCommand("eraser");
			eraser.addActionListener(this);
			//lastTwoPlusEraser.add(eraser);

			//add(lastTwoPlusEraser);
		}

		public static int getLastThickness() {
			return lastThickness;
		}

		public static void setLastThickness(int lastThickness) {
			ButtonPanel.lastThickness = lastThickness;
		}

		public static Color getLastColor(){
			return lastColor;
		}

		private JButton createColorButton(Color background){
			JButton button = new JButton("	");
			button.setBackground(background);
			button.addActionListener(this);
			this.setSize(100,50);

			return button;
		}

		private JButton createTextButton(String text){
			JButton button = new JButton(text);
			button.setBackground(null);
			button.addActionListener(this);

			return button;
		}

		private JButton createShapeButton(String text){
			JButton button = new JButton(text);
			button.setBackground(Color.darkGray);
			button.setForeground(Color.white);
			button.addActionListener(this);

			return button;
		}

		public void actionPerformed(ActionEvent e){
			JButton button = (JButton) e.getSource();
			String buttonText = button.getActionCommand();
			if(button == eraser){
				DrawingArea.erasing = true;
			}
			//System.out.println("buttonText "+ buttonText);

			if(!skipFirstTime || (skipFirstTime && !(buttonText.equals("Clear Drawing") || buttonText.equals("Darker")
					|| buttonText.equals("Darker") || buttonText.equals("Lighter") || buttonText.equals("Thicker") || buttonText.equals("Thinner")
					|| buttonText.equals("Filled")))){
				//Blank for clarity
				if(skipFirstTime){
					skipFirstTime = false;
					lastButton = blackButton;
					lastColor = blackButton.getBackground();
					done = true;
				}
				if ("Clear Drawing".equals(e.getActionCommand())){
					drawingArea.clear();
				}else if("Darker".equals(e.getActionCommand())){
					if(lastColor.equals(lastButton.getBackground())){
						lastColor = lastButton.getBackground().darker();
						//System.out.println("lastButton: " + lastButton.getBackground());
						//System.out.println("Darker: " + lastColor);
					}else{
						lastColor = lastColor.darker();
						//System.out.println("Making the darker color darker" + lastColor);
					}
				}else if("Lighter".equals(e.getActionCommand())){
					if(lastColor.equals(lastButton.getBackground())){
						lastColor = lastButton.getBackground().brighter();
						//System.out.println("lastButton: " + lastButton.getBackground());
						//System.out.println("Lighter: " + lastColor);
					}else{
						lastColor = lastColor.brighter();
						//System.out.println("Making the light color lighter" + lastColor);
					}
				}else if("Thicker".equals(e.getActionCommand())){
					setLastThickness(getLastThickness() + 1);
				}else if("Thinner".equals(e.getActionCommand())){
					if(getLastThickness() > 1){
						setLastThickness(getLastThickness() - 1);
					}
				}else if("Filled".equals(buttonText)){
					if(filled){
						filled = false;
					}else{
						filled = true;
					}
				}else if("Random Color!!".equals(buttonText)){
					Color randomCol = new Color(generator.nextInt(max), generator.nextInt(max), generator.nextInt(max));
					lastColor = randomCol;
					//				}else if(button.getIcon().equals(eraser)){
					//					lastColor = Color.white;
					//					lastThickness = 20;
					//					
				}else{
					if(buttonText.equals("Rectangle") || buttonText.equals("Oval") || buttonText.equals("Line") 
							|| buttonText.equals("Circle") || buttonText.equals("Square")){
						DrawingArea.shape = buttonText;
						return;					
					}

					lastColor = button.getBackground();
					lastButton = button;
					//System.out.println("Last button (else): "+ lastButton.getBackground());
				}
				return;
			}
			if(!done){
				//System.out.println("After the if in action handler");
				skipFirstTime = false;
				lastButton = blackButton;
				lastColor = blackButton.getBackground();
			}
		}
	}

	static class DrawingArea extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		//		private ArrayList<ColoredRectangle> coloredRectangles = new ArrayList<ColoredRectangle>();
		//		private ArrayList<ColoredOval> coloredOvals = new ArrayList<ColoredOval>();
		//		private ArrayList<ColoredLine> coloredLines = new ArrayList<ColoredLine>();
		//		private ArrayList<ColoredCircle> coloredCircles = new ArrayList<ColoredCircle>();
		//		private ArrayList<ColoredSquare> coloredSquares = new ArrayList<ColoredSquare>();
		private ArrayList<Shape> shapesArray = new ArrayList<Shape>(40);
		private Point startPoint = null;
		private Point endPoint = null;
		public static String shape = "Line";
		public static boolean erasing = false;

		public DrawingArea(){
			setBackground(Color.WHITE);
			CustomListener ml = new CustomListener();
			addMouseListener(ml);
			addMouseMotionListener(ml);
		}

		//		public void repaintRectangles(Graphics2D g2d){
		//			for (ColoredRectangle cr : coloredRectangles){
		//				g2d.setColor(cr.getForeground());
		//
		//				//Sets the stroke according to the thickness that the user has specified
		//				Rectangle r = cr.getRectangle();
		//				g2d.setStroke(new BasicStroke(cr.getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		//				g2d.drawRect(r.x, r.y, r.width, r.height);
		//				if(cr.filled){
		//					g2d.fillRect(r.x, r.y, r.width, r.height);
		//				}
		//			}
		//		}
		//
		//		public void repaintOvals(Graphics2D g2d){
		//			for (ColoredOval co : coloredOvals){
		//				g2d.setColor(co.getForeground());
		//
		//				//Sets the stroke according to the thickness that the user has specified
		//				Oval o = co.getOval();
		//				g2d.setStroke(new BasicStroke(co.getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		//				g2d.drawOval(o.x, o.y, o.width, o.height);
		//				if(co.filled){
		//					g2d.fillOval(o.x, o.y, o.width, o.height);
		//				}
		//			}
		//		}
		//
		//		public void repaintCircles(Graphics2D g2d){
		//			for (ColoredCircle cc : coloredCircles){
		//				g2d.setColor(cc.getForeground());
		//
		//				//Sets the stroke according to the thickness that the user has specified
		//				Circle c = cc.getCircle();
		//				g2d.setStroke(new BasicStroke(cc.getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		//				g2d.drawOval(c.x, c.y, c.radius, c.radius);
		//				if(cc.filled){
		//					g2d.fillOval(c.x, c.y, c.radius, c.radius);
		//				}
		//			}
		//		}
		//
		//		public void repaintLines(Graphics2D g2d){
		//			for (ColoredLine cl : coloredLines){
		//				g2d.setColor(cl.getForeground());
		//
		//				//Sets the stroke according to the thickness that the user has specified
		//				Line l = cl.getLine();
		//				g2d.setStroke(new BasicStroke(cl.getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		//				g2d.drawLine(l.start.x, l.start.y, l.end.x, l.end.y);
		//			}
		//		}
		//
		//		public void repaintSquares(Graphics2D g2d){
		//			for (ColoredSquare cs : coloredSquares){
		//				g2d.setColor(cs.getForeground());
		//
		//				//Sets the stroke according to the thickness that the user has specified
		//				Square s = cs.getSquare();
		//				g2d.setStroke(new BasicStroke(cs.getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		//				g2d.drawRect(s.x, s.y, s.side, s.side);
		//				if(cs.filled){
		//					g2d.fillRect(s.x, s.y, s.side, s.side);
		//				}
		//			}
		//		}

		public void repaintAllShapes(Graphics2D g2d){
			for(Shape s: shapesArray){
				g2d.setColor(s.getForeground());
				s.draw(g2d);
				//				if(s instanceof ColoredOval){
				//					
				//				}else if(s instanceof ColoredLine){
				//					
				//				}else if(s instanceof ColoredCircle){
				//					
				//				}else if(s instanceof ColoredSquare){
				//					
				//				}
			}
		}

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			// Set the stroke to be thicker according to the value of the lastThickness variable
			g2d.setStroke(new BasicStroke((float) ButtonPanel.getLastThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));

			g2d.setColor(Color.BLACK);
			g2d.drawString("Drag and release to draw.", 40, 15);

			//  Paints all the shapes in the five lists
			//			repaintRectangles(g2d);
			//			g2d.setColor(ButtonPanel.getLastColor());
			//			
			//			repaintOvals(g2d);
			//			g2d.setColor(ButtonPanel.getLastColor());
			//			
			//			repaintCircles(g2d);
			//			g2d.setColor(ButtonPanel.getLastColor());
			//			
			//			repaintLines(g2d);
			//			g2d.setColor(ButtonPanel.getLastColor());
			//			
			//			repaintSquares(g2d);
			//			g2d.setColor(ButtonPanel.getLastColor());

			repaintAllShapes(g2d);


			//Set the stroke and color to the previous ones
			g2d.setStroke(new BasicStroke((float) ButtonPanel.getLastThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
			if(!g2d.getColor().equals(ButtonPanel.getLastColor())){
				g2d.setColor(ButtonPanel.getLastColor());
			}

			//  Paint the shape as the mouse is being dragged, if the eraser is not selected
			if(shape.equals("Rectangle")){
				//System.out.println("Rect");
				//System.out.println("Drawing Rect, shape: " +shape);
				if (startPoint != null && endPoint != null){
					g2d.setStroke(new BasicStroke((float) ButtonPanel.getLastThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
					int x = Math.min(startPoint.x, endPoint.x);
					int y = Math.min(startPoint.y, endPoint.y);
					int width = Math.abs(startPoint.x - endPoint.x);
					int height = Math.abs(startPoint.y - endPoint.y);
					g2d.drawRect(x, y, width, height);
					if(ButtonPanel.filled){
						g2d.fillRect(x, y, width, height);
					}
				}
			}else if(shape.equals("Oval")){
				if (startPoint != null && endPoint != null){
					//System.out.println("Drawing Oval, shape" + shape);
					g2d.setStroke(new BasicStroke((float) ButtonPanel.getLastThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
					int x = Math.min(startPoint.x, endPoint.x);
					int y = Math.min(startPoint.y, endPoint.y);
					int width = Math.abs(startPoint.x - endPoint.x);
					int height = Math.abs(startPoint.y - endPoint.y);
					g2d.drawOval(x, y, width, height);
					if(ButtonPanel.filled){
						g2d.fillOval(x, y, width, height);
					}
				}
			}else if(shape.equals("Line")){
				if (startPoint != null && endPoint != null){
					//System.out.println("Drawing Line, shape" + shape);
					g2d.setStroke(new BasicStroke((float) ButtonPanel.getLastThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
					int x1 = startPoint.x;
					int y1 = startPoint.y;
					int x2 = endPoint.x;
					int y2 = endPoint.y;
					g2d.drawLine(x1, y1, x2, y2);
				}
			}else if(shape.equals("Circle")){
				if (startPoint != null && endPoint != null){
					//System.out.println("Drawing Oval, shape" + shape);
					g2d.setStroke(new BasicStroke((float) ButtonPanel.getLastThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
					int x = Math.min(startPoint.x, endPoint.x);
					int y = Math.min(startPoint.y, endPoint.y);
					int width = Math.abs(startPoint.x - endPoint.x);
					g2d.drawOval(x, y, width, width);
					if(ButtonPanel.filled){
						g2d.fillOval(x, y, width, width);
					}
				}
			}else if(shape.equals("Square")){
				//System.out.println("Drawing Rect, shape: " +shape);
				if (startPoint != null && endPoint != null){
					g2d.setStroke(new BasicStroke((float) ButtonPanel.getLastThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
					int x = Math.min(startPoint.x, endPoint.x);
					int y = Math.min(startPoint.y, endPoint.y);
					int width = Math.abs(startPoint.x - endPoint.x);
					g2d.drawRect(x, y, width, width);
					if(ButtonPanel.filled){
						g2d.fillRect(x, y, width, width);
					}
				}
			}
		}

		public void clear(){
			//			coloredRectangles.clear();
			//			coloredOvals.clear();
			//			coloredLines.clear();
			//			coloredCircles.clear();
			//			coloredSquares.clear();
			shapesArray.clear();

			ButtonPanel.lastColor = Color.BLACK;
			ButtonPanel.lastThickness = 1;
			ButtonPanel.filled = false;
			repaint();
		}

		class CustomListener extends MouseInputAdapter{
			private int xMin;
			private int xMax;
			private int yMin;
			private int yMax;

			public void mousePressed(MouseEvent e){
				startPoint = e.getPoint();
				endPoint = startPoint;
				xMin = startPoint.x;
				xMax = startPoint.x;
				yMin = startPoint.y;
				yMax = startPoint.y;
			}

			public void mouseDragged(MouseEvent e){
				//  Repaint only the area affected by the mouse dragging

				endPoint = e.getPoint();
				xMin = Math.min(xMin, endPoint.x);
				xMax = Math.max(xMax, endPoint.x);
				yMin = Math.min(yMin, endPoint.y);
				yMax = Math.max(yMax, endPoint.y);
				repaint();

				/*				The following code is commented out because the call to the repaint() method will not
				 *				account for the offset caused if a thickness > 1 is set, so a generic call is required.
				 *
				 *				int paintOffset = ButtonPanel.getLastThickness() + 10;
				 *				repaint(xMin - paintOffset, yMin - paintOffset, xMax - xMin + 1 + paintOffset, yMax - yMin + 1 + paintOffset);
				 */
			}

			public void mouseReleased(MouseEvent e){
				//  Save the drawing information to the List
				int x = Math.min(startPoint.x, endPoint.x);
				int y = Math.min(startPoint.y, endPoint.y);
				int width = Math.abs(startPoint.x - endPoint.x);
				int height = Math.abs(startPoint.y - endPoint.y);
				Rectangle r = new Rectangle(x, y, width, height);
				Oval o = new Oval(x, y, width, height);
				Line l = new Line(startPoint, endPoint);
				Circle c = new Circle(x, y, width);
				Square s = new Square(x, y, width);
				boolean f = false;
				if(ButtonPanel.filled){
					f = true;
				}

				Shape sh = null;
				//System.out.println("SHAPPPEE = " + shape);
				if(shape.equals("Oval")){
					if (o.height != 0 || o.width != 0){
						sh = new ColoredOval(ButtonPanel.getLastColor(), o, (float) ButtonPanel.getLastThickness(), f);
						//coloredOvals.add(co);
					}
				}else if(shape.equals("Rectangle")){
					//System.out.println("Re123");
					if (r.width != 0 || r.height != 0){
						sh = new ColoredRectangle(ButtonPanel.getLastColor(), r, (float) ButtonPanel.getLastThickness(), f);
						//coloredRectangles.add(cr);
					}
				}else if(shape.equals("Line")){
					if(width != 0 || height != 0){
						sh = new ColoredLine(ButtonPanel.getLastColor(), l, (float) ButtonPanel.getLastThickness());
						//coloredLines.add(cl);
					}
				}else if(shape.equals("Circle") && !erasing){
					if (c.radius != 0){
						sh = new ColoredCircle(ButtonPanel.getLastColor(), c, (float) ButtonPanel.getLastThickness(), f);
						//coloredCircles.add(cc);
					}
				}else if(shape.equals("Square")){
					if (s.side != 0){
						sh = new ColoredSquare(ButtonPanel.getLastColor(), s, (float) ButtonPanel.getLastThickness(), f);
						//coloredSquares.add(cs);
					}
				}
				if(sh != null)
					shapesArray.add(sh);
				
				for(Shape sha : shapesArray){
					String cls = sha.getClass().toString();
					System.out.print(cls.substring(cls.lastIndexOf("$")+1) + ", ");
				}

				startPoint = null;
				repaint();
			}
		}

		class ColoredRectangle implements Shape{
			private Color foreground;
			private Rectangle rectangle;
			private float thickness;
			private boolean filled;

			public ColoredRectangle(Color foreground, Rectangle rectangle, float thickness, boolean filled){
				this.foreground = foreground;
				this.rectangle = rectangle;
				this.thickness = thickness;
				this.filled = filled;
			}

			public Color getForeground(){
				return foreground;
			}

			public void setForeground(Color foreground){
				this.foreground = foreground;
			}

			public Shape getShape(){
				return (Shape) rectangle;
			}

			public float getThickness(){
				return thickness;
			}

			public void draw(Graphics2D g2d) {
				g2d.setStroke(new BasicStroke(getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
				g2d.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
				if(filled)
					g2d.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			}

		}

		class ColoredOval implements Shape{
			private Oval oval;
			private Color foreground;
			private float thickness;
			private boolean filled;

			public ColoredOval(Color foreground, Oval oval, float thickness, boolean filled){
				this.foreground = foreground;
				this.oval = oval;
				this.thickness = thickness;
				this.filled = filled;
			}

			public Color getForeground(){
				return foreground;
			}

			public void setForeground(Color foreground){
				this.foreground = foreground;
			}

			public Shape getShape(){
				return oval;
			}

			public float getThickness(){
				return thickness;
			}

			public boolean getFilled(){
				return filled;
			}

			public void draw(Graphics2D g2d) {
				g2d.setStroke(new BasicStroke(getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
				g2d.drawOval(oval.x, oval.y, oval.width, oval.height);
				if(filled)
					g2d.fillOval(oval.x, oval.y, oval.width, oval.height);

			}
		}

		class Oval implements Shape{
			public int height;
			public int width;
			public int x;
			public int y;

			public Oval(int x, int y, int width, int height){
				this.width = width;
				this.height = height;
				this.x = x;
				this.y = y;
			}

			public Shape getShape() {
				return null;
			}

			public Color getForeground() {
				return null;
			}

			public void draw(Graphics2D g2d) {
			}

			public float getThickness() {
				return 0;
			}
		}

		class ColoredLine implements Shape{
			private Line line;
			private Color foreground;
			private float thickness;

			public ColoredLine(Color foreground, Line line, float thickness){
				this.foreground = foreground;
				this.line = line;
				this.thickness = thickness;
			}

			public Color getForeground(){
				return foreground;
			}

			public void setForeground(Color foreground){
				this.foreground = foreground;
			}

			public Shape getShape(){
				return line;
			}

			public float getThickness(){
				return thickness;
			}

			public void draw(Graphics2D g2d) {
				g2d.setStroke(new BasicStroke(getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
				g2d.setColor(foreground);
				g2d.drawLine(line.start.x, line.start.y, line.end.x, line.end.y);
			}
		}

		class Line implements Shape{
			public Point start;
			public Point end;

			public Line(Point start, Point end){
				this.start = start;
				this.end = end;
			}

			public Shape getShape() {
				return null;
			}

			public Color getForeground() {
				return null;
			}

			public void draw(Graphics2D g2d) {
			}

			public float getThickness() {
				return 0;
			}
		}

		class ColoredCircle implements Shape{
			private Circle circle;
			private Color foreground;
			private float thickness;
			private boolean filled;

			public ColoredCircle(Color foreground, Circle circle, float thickness, boolean filled){
				this.foreground = foreground;
				this.circle = circle;
				this.thickness = thickness;
				this.filled = filled;
			}

			public Color getForeground(){
				return foreground;
			}

			public void setForeground(Color foreground){
				this.foreground = foreground;
			}

			public Shape getShape(){
				return circle;
			}

			public float getThickness(){
				return thickness;
			}

			public boolean getFilled(){
				return filled;
			}

			public void draw(Graphics2D g2d) {
				g2d.setStroke(new BasicStroke(getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
				g2d.setColor(foreground);
				g2d.drawOval(circle.x, circle.y, circle.radius, circle.radius);
				if(filled)
					g2d.fillOval(circle.x, circle.y, circle.radius, circle.radius);
			}
		}

		class Circle implements Shape{
			public int x;
			public int y;
			public int radius;

			public Circle(int x, int y, int radius){
				this.radius = radius;
				this.x = x;
				this.y = y;
			}

			public Shape getShape() {
				return null;
			}

			public Color getForeground() {
				return null;
			}

			public void draw(Graphics2D g2d) {
			}

			public float getThickness() {
				return 0;
			}
		}

		class ColoredSquare implements Shape{
			private Color foreground;
			private Square square;
			private float thickness;
			private boolean filled;

			public ColoredSquare(Color foreground, Square square, float thickness, boolean filled){
				this.foreground = foreground;
				this.square = square;
				this.thickness = thickness;
				this.filled = filled;
			}

			public Color getForeground(){
				return foreground;
			}

			public void setForeground(Color foreground){
				this.foreground = foreground;
			}

			public Shape getShape(){
				return square;
			}

			public float getThickness(){
				return thickness;
			}

			public void draw(Graphics2D g2d) {
				g2d.setStroke(new BasicStroke(getThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
				g2d.setColor(foreground);
				g2d.drawRect(square.x, square.y, square.width, square.height);
				if(filled){
					g2d.fillRect(square.x, square.y, square.width, square.height);
				}
			}

		}

		class Square extends Rectangle implements Shape{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public int side;

			public Square(int x, int y , int side){
				super(side, side);
				this.side = side;
				this.x = x;
				this.y = y;
			}

			public Shape getShape() {
				return null;
			}

			public Color getForeground() {
				return null;
			}

			public void draw(Graphics2D g2d) {
			}

			public float getThickness() {
				return 0;
			}
		}

		interface Shape{
			Shape getShape();
			Color getForeground();
			float getThickness();
			void draw(Graphics2D g2d);
		}
	}
}
