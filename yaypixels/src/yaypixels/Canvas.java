package yaypixels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

import yaypixels.Toolbar.Tools.ToolHandler;

public class Canvas extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
	int pixelSize = 8;
	private int mouseX = -1;
	private int mouseY = -1;
	private Color dPColor1 = new Color(220, 220, 220);
	private Color dPColor2 = new Color(180, 180, 180);
	private Color mouseOverColor = new Color(120, 120, 120);

	private Color[][] colorArray; // tracks current color of each pixel
	private boolean[][] isPainted;

	Canvas() {
		setBackground(new Color(40, 40, 40));
		colorArray = new Color[Window.frameWidth / pixelSize][Window.frameHeight / pixelSize];
		isPainted = new boolean[Window.frameWidth / pixelSize][Window.frameHeight / pixelSize];
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int k = 0; k < this.getHeight() - (this.getHeight() % pixelSize); k += pixelSize) {
			for (int i = 0; i < this.getWidth() - (this.getWidth() % pixelSize); i += pixelSize) {
				int ip = i / pixelSize;
				int kp = k / pixelSize;
				colorAssigner(ip, kp);
				g.setColor(colorArray[ip][kp]);
				if (ip == mouseX && kp == mouseY)
					g.setColor(mouseOverColor);
				g.fillRect(i, k, pixelSize, pixelSize);
			}
		}
	}

	private void colorAssigner(int i, int k) {
		if (!isPainted[i][k])
			defaultColorAssigner(i, k);
	}

	private void defaultColorAssigner(int i, int k) {
		if ((i + k) % 2 == 0)
			colorArray[i][k] = dPColor1;
		else
			colorArray[i][k] = dPColor2;
	}

	private void updateCursor(Point p) {
		mouseX = p.x / pixelSize;
		mouseY = p.y / pixelSize;
		this.setToolTipText(mouseX + "," + mouseY);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		if (p.x >= 0 && p.y >= 0) {
			ToolHandler.update(colorArray, isPainted);
			ToolHandler.toolClicked(p.x/pixelSize, p.y/pixelSize);
			colorArray = ToolHandler.getColorArray();
			isPainted = ToolHandler.getIsPainted();
			repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		if (p.x >= 0 && p.y >= 0) {
			ToolHandler.update(colorArray, isPainted);
			ToolHandler.toolClicked(p.x/pixelSize, p.y/pixelSize);
			colorArray = ToolHandler.getColorArray();
			isPainted = ToolHandler.getIsPainted();
			updateCursor(p);
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) { // Creates fill cursor when mouse hovers over pixel
		Point p = e.getPoint();
		if (p.x >= 0 && p.y >= 0) {
			updateCursor(p);
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'b' || e.getKeyChar() == 'B')
			ToolHandler.setTool('b');
		if (e.getKeyChar() == 'e' || e.getKeyChar() == 'E')
			ToolHandler.setTool('e');
		if (e.getKeyChar() == 'f' || e.getKeyChar() == 'F')
			ToolHandler.setTool('f');
		if (e.getKeyChar() == 'i' || e.getKeyChar() == 'I')
			ToolHandler.setTool('i');
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
