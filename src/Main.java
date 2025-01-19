import Notify.UserController;
import display.UserDisplay;
public class Main {
    public static void main(String[] args) {
        UserDisplay view = new UserDisplay();
        UserController controller = new UserController(view);
        controller.start();
    }
}