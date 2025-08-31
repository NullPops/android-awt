package java.awt;

public class Frame extends Window {
    String title = "Untitled";
    boolean resizable = true;

    /**
     * Gets the title of the frame.  The title is displayed in the
     * frame's border.
     *
     * @return the title of this frame, or an empty string ("")
     * if this frame doesn't have a title.
     * @see #setTitle(String)
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title for this frame to the specified string.
     *
     * @param title the title to be displayed in the frame's border.
     *              A {@code null} value
     *              is treated as an empty string, "".
     * @see #getTitle
     */
    public void setTitle(String title) {
        String oldTitle = this.title;
        if (title == null) {
            title = "";
        }
        synchronized (this) {
            this.title = title;
        }
    }

    /**
     * Indicates whether this frame is resizable by the user.
     * By default, all frames are initially resizable.
     *
     * @return {@code true} if the user can resize this frame;
     * {@code false} otherwise.
     * @see java.awt.Frame#setResizable(boolean)
     */
    public boolean isResizable() {
        return resizable;
    }

    /**
     * Sets whether this frame is resizable by the user.
     *
     * @param resizable {@code true} if this frame is resizable;
     *                  {@code false} otherwise.
     * @see java.awt.Frame#isResizable
     */
    public void setResizable(boolean resizable) {
        synchronized (this) {
            this.resizable = resizable;
        }
    }
}
