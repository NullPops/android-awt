package java.awt;

public class Container extends Component {
    public Insets getInsets() {
        return insets();
    }

    public Insets insets() {
        return new Insets(0, 0, 0, 0);
    }
}