package java.awt.event;

public class FocusEvent {

    /**
     * This enum represents the cause of a {@code FocusEvent}- the reason why it
     * occurred. Possible reasons include mouse events, keyboard focus
     * traversal, window activation.
     * If no cause is provided then the reason is {@code UNKNOWN}.
     *
     * @since 9
     */
    public enum Cause {
        /**
         * The default value.
         */
        UNKNOWN,
        /**
         * An activating mouse event.
         */
        MOUSE_EVENT,
        /**
         * A focus traversal action with unspecified direction.
         */
        TRAVERSAL,
        /**
         * An up-cycle focus traversal action.
         */
        TRAVERSAL_UP,
        /**
         * A down-cycle focus traversal action.
         */
        TRAVERSAL_DOWN,
        /**
         * A forward focus traversal action.
         */
        TRAVERSAL_FORWARD,
        /**
         * A backward focus traversal action.
         */
        TRAVERSAL_BACKWARD,
        /**
         * Restoring focus after a focus request has been rejected.
         */
        ROLLBACK,
        /**
         * A system action causing an unexpected focus change.
         */
        UNEXPECTED,
        /**
         * An activation of a toplevel window.
         */
        ACTIVATION,
        /**
         * Clearing global focus owner.
         */
        CLEAR_GLOBAL_FOCUS_OWNER
    }
}