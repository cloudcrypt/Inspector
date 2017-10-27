import TestDriver.ClassA;
import TestDriver.ClassB;

public class Driver {
    public static void main(String[] args) {
        Inspector inspector = new Inspector();
        try {
            inspector.inspect(new ClassB(), true);
        } catch (Exception e) {

        }
    }
}
