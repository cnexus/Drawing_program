//The remains of the original shape class for the drawing project
public class Shape {
/*	// Fields for the shape
	private String shapeType; // The type of shape: triangle, square....
	private int vertices;
	private Point center = new Point(0, 0); // Coordinates for the center of the shape
	private int width; // Measured from center
	private int height; // Measured from center
	private String borderColor; // Color of the shapes border
	private String fillColor; // Fill color for the shape
	private int baseSideLength; //Specifies the length of the "base" side from which the rest of the shape will be built

	// Constructor
	public Shape(String type, int corners, String borderC, String fill) {
		shapeType = type;
		vertices = corners;
		borderColor = borderC;
		fillColor = fill;
	}

	// Getters and Setters
	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getBaseSideLength() {
		return baseSideLength;
	}

	public void setBaseSideLength(int baseSideLength) {
		this.baseSideLength = baseSideLength;
	}

	public String getShapeType() {
		return shapeType;
	}

	public int getVertices() {
		return vertices;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public String getFillColor() {
		return fillColor;
	}

	public static int checkWithinFrame(int coord, int width) {
		if(coord > width) {
			coord = coord - 800;
		}
		return coord;
	}

	// Main shape drawing function

	public static void userDrawLine() {
		int side = 400;
		Dimension canvasSize = new Dimension();
		canvasSize.setSize(side, side);
		AFrame canvasFrame = new AFrame("Drawing Canvas", canvasSize);
		Input question = new Input();
		String q = "Start x -> ";
		question.setQ(q);
		int x1 = question.getData();
		q = "Start y -> ";
		question.setQ(q);
		int y1 = question.getData();
		q = "End x -> ";
		question.setQ(q);
		int x2 = question.getData();
		q = "End y -> ";
		question.setQ(q);
		int y2 = question.getData();
		checkWithinFrame(x1, side);
		checkWithinFrame(y1, side);
		checkWithinFrame(x2, side);
		checkWithinFrame(y2, side);
	}*/
}