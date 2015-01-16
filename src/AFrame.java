import java.awt.*;

public class AFrame {
	private String title;
	private Dimension frameSize;
	public AFrame (String frameName, Dimension Dims) {
		title = frameName;
		frameSize = Dims;
		Color bgColor = new Color(0, 0, 0);
		Frame canvas = new Frame(title);
		canvas.setBackground(bgColor);
		int w = (int) frameSize.getWidth();
		int h = (int) frameSize.getHeight();
		canvas.setSize(w, h);
		canvas.setVisible(true);
		canvas.setResizable(false);
	}
	
	
	// Start of Getters and Setters

	public Dimension getFrameSize() {
		return frameSize;
	}

	public void setFrameSize(Dimension frameSize) {
		this.frameSize = frameSize;
	}

	public String getTitle() {
		return title;
	}

	// End of Expected Class Definitions
	
	public void main (String [] args){
		Dimension g = getFrameSize();
		AFrame frame2 = new AFrame("Canvas", g);
		frame2.setFrameSize(g);
	}
}


