import TestDriver.ClassA;
import TestDriver.ClassB;
import TestDriver.ClassD;

public class Driver {
    public static void main(String[] args) {
        Inspector inspector = new Inspector();
        try {
            inspector.inspect(new ClassD(), true);
        } catch (Exception e) {
            throw e;
        }
    }
}
