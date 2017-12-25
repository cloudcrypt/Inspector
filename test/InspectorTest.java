/*
 * InspectorTest
 * Daniel Dastoor
 */

import TestDriver.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Set of unit tests to verify the output of the Inspector.
 * @author Daniel Dastoor
 */
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
                "Field: val3",
                "Method: finalize, Declared in java.lang.Object",
                "Method: getClass, Declared in java.lang.Object",
                "Method: notifyAll, Declared in java.lang.Object",
                "Inherited Fields:\n\tNone",
                "Inherited Interfaces:\n\tNone"
        };
        Arrays.stream(outputValues).forEach(s -> assertTrue(outStream.toString().contains(s)));
    }

    @Test
    public void testClassARecursive() {
        // Verify output is the same as testClassA, as ClassA does not reference any object fields
        testClassA();
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
                    "Constructor: TestDriver.ClassB\n\t\tModifiers:\n\t\t\tpublic\n\t\tParameter Types:\n\t\t\tNone",
                    "Field: val",
                    "Field: val2",
                    "Field: val3",
                    "Method: func3, Declared in TestDriver.ClassC",
                    "Method: func0, Declared in TestDriver.ClassD",
                    "Method: registerNatives, Declared in java.lang.Object",
                    "Method: notifyAll, Declared in java.lang.Object",
                    "Inherited Fields:\n\tField: val2, Declared in TestDriver.ClassC",
                    "Field: vallarray, Declared in TestDriver.ClassD",
                    "Inherited Interfaces:",
                    "TestDriver.InterfaceA",
                    "TestDriver.InterfaceB"
            };
            Arrays.stream(outputValues).forEach(s -> assertTrue(outStream.toString().contains(s)));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testClassBRecursive() {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        try {
            ClassB cls = new ClassB();
            Inspector inspector = new Inspector();
            inspector.inspect(cls, true);
            String[] outputValues = new String[]{
                    "Class: TestDriver.ClassB",
                    "Immediate Superclass: TestDriver.ClassC",
                    "Implemented Interfaces: \n" +
                            "\tjava.lang.Runnable",
                    "Method: run",
                    "Method: toString",
                    "Return Type: \n\t\t\tjava.lang.String",
                    "Method: func3",
                    "Constructor: TestDriver.ClassB\n\t\tModifiers:\n\t\t\tpublic\n\t\tParameter Types:\n\t\t\tNone",
                    "Field: val",
                    "Field: val2",
                    "Field: val3",
                    "Method: func3, Declared in TestDriver.ClassC",
                    "Method: func0, Declared in TestDriver.ClassD",
                    "Method: registerNatives, Declared in java.lang.Object",
                    "Method: notifyAll, Declared in java.lang.Object",
                    "Inherited Fields:\n\tField: val2, Declared in TestDriver.ClassC",
                    "Field: vallarray, Declared in TestDriver.ClassD",
                    "Inherited Interfaces:",
                    "TestDriver.InterfaceA",
                    "TestDriver.InterfaceB",
                    "Inspecting object: TestDriver.ClassA"
            };
            Arrays.stream(outputValues).forEach(s -> assertTrue(outStream.toString().contains(s)));
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void testClassD() {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        ClassD cls = new ClassD();
        Inspector inspector = new Inspector();
        inspector.inspect(cls, false);
        String[] outputValues = new String[] {
                "Class: TestDriver.ClassD",
                "Immediate Superclass: java.lang.Object",
                "Implemented Interfaces: \n" +
                        "\tTestDriver.InterfaceA",
                "Method: func1, Declared in TestDriver.ClassD",
                "Method: func0, Declared in TestDriver.ClassD",
                "Method: toString, Declared in TestDriver.ClassD",
                "Method: getVal3, Declared in TestDriver.ClassD",
                "java.lang.IllegalMonitorStateException",
                "Field: val, Declared in TestDriver.ClassD",
                "Field: val3, Declared in TestDriver.ClassD",
                "Field: vallarray, Declared in TestDriver.ClassD",
                "Method: clone, Declared in java.lang.Object",
                "Inherited Fields:\n\tNone",
                "Inherited Interfaces:\n\tTestDriver.InterfaceB"
        };
        Arrays.stream(outputValues).forEach(s -> assertTrue(outStream.toString().contains(s)));
    }

    @Test
    public void testClassDRecursive() {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        ClassD cls = new ClassD();
        Inspector inspector = new Inspector();
        inspector.inspect(cls, true);
        String[] outputValues = new String[] {
                "Class: TestDriver.ClassD",
                "Immediate Superclass: java.lang.Object",
                "Implemented Interfaces: \n" +
                        "\tTestDriver.InterfaceA",
                "Method: func1, Declared in TestDriver.ClassD",
                "Method: func0, Declared in TestDriver.ClassD",
                "Method: toString, Declared in TestDriver.ClassD",
                "Method: getVal3, Declared in TestDriver.ClassD",
                "java.lang.IllegalMonitorStateException",
                "Field: val, Declared in TestDriver.ClassD",
                "Field: val3, Declared in TestDriver.ClassD",
                "Field: vallarray, Declared in TestDriver.ClassD",
                "Method: clone, Declared in java.lang.Object",
                "Inherited Fields:\n\tNone",
                "Inherited Interfaces:\n\tTestDriver.InterfaceB",
                "Inspecting object: TestDriver.ClassA"
        };
        Arrays.stream(outputValues).forEach(s -> assertTrue(outStream.toString().contains(s)));
    }
}
