import TestDriver.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
                "java.lang.Exception",
                "Field: val",
                "Field: val2",
                "Field: val3"
        };
        Arrays.stream(outputValues).forEach(s -> assertTrue(outStream.toString().contains(s)));
    }

    @Test
    public void testClassB() {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        try {
            ClassB cls = new ClassB();
            Inspector inspector = new Inspector();
            inspector.inspect(cls, false);
            String[] outputValues = new String[]{
                    "Class: TestDriver.ClassB",
                    "Immediate Superclass: TestDriver.ClassC",
                    "Implemented Interfaces: \n" +
                            "\tjava.lang.Runnable",
                    "Method: run",
                    "Method: toString",
                    "Return Type: \n\t\t\tjava.lang.String",
                    "Method: func3",
                    "Constructor: TestDriver.ClassB\n\t\t\tModifiers:\n\t\t\t\tpublic\n\t\t\tParameter Types:\n\t\t\t\tNone",
                    "Field: val",
                    "Field: val2",
                    "Field: val3"
            };
            Arrays.stream(outputValues).forEach(s -> assertTrue(outStream.toString().contains(s)));
        } catch (Exception e) {
            fail(e);
        }
    }
}
