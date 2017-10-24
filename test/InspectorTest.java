import TestDriver.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InspectorTest {
    @Test
    public void testClassA() {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        ClassA cls = new ClassA();
        Inspector inspector = new Inspector();
        inspector.inspect(cls, false);
        String[] outputValues = new String[] {
                "Class: TestDriver.ClassA",
                "Immediate Superclass: java.lang.Object",
                "Implemented Interfaces: \n" +
                "\tjava.io.Serializable\n" +
                "\tjava.lang.Runnable",
                "Method: run",
                "Method: toString",
                "Method: setVal",
                "Method: getVal",
                "Method: printSomething",
                "java.lang.Exception"
        };
        Arrays.stream(outputValues).forEach(s -> assertTrue(outStream.toString().contains(s)));
    }
}
