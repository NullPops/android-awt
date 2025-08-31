package java.awt;

import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Component {
    int x;
    int y;

    public boolean prepareImage(Image img, int w, int h, MediaTracker.TrackingImage trackingImage) {

        return true;
    }

    public int checkImage(Image img, int w, int h, MediaTracker.TrackingImage trackingImage) {
        return 0;
    }

    public void resize(int width, int height) {
        setBounds(x, y, width, height);
    }

    public void setBounds(int x, int y, int width, int height) {
        reshape(x, y, width, height);
    }

    public void reshape(int x, int y, int width, int height) {

    }

    public Graphics getGraphics() {
        return null;
    }

    public synchronized void addMouseListener(MouseListener l) {

    }

    public synchronized void addMouseMotionListener(MouseMotionListener l) {

    }

    public synchronized void addKeyListener(KeyListener l) {

    }

    public synchronized void addFocusListener(FocusListener l) {

    }

    public FontMetrics getFontMetrics(Font font) {
        return Toolkit.getDefaultToolkit().getFontMetrics(font);
    }
}
