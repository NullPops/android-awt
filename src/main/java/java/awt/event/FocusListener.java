package java.awt.event;

import java.util.EventListener;

public interface FocusListener extends EventListener {

    /**
     * Invoked when a component gains the keyboard focus.
     *
     * @param e the event to be processed
     */
    public void focusGained(FocusEvent e);

    /**
     * Invoked when a component loses the keyboard focus.
     *
     * @param e the event to be processed
     */
    public void focusLost(FocusEvent e);
}