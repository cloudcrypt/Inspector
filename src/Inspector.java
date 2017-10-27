import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class Inspector {

    private int indentLevel = 0;

    public Inspector() { }

    public void inspect(Object obj, boolean recursive) {
        print("Inspecting object: %s %s\n", obj.toString(), System.identityHashCode(obj));
        Class cls = obj.getClass();
        print("Class: %s", cls.getName());
        print("Immediate Superclass: %s", cls.getSuperclass().getName());
        print("Implemented Interfaces: ");
        indentLevel++;
        printNames(cls.getInterfaces());
        indentLevel--;
        print("Declared Methods: ");
        Method[] methods = cls.getDeclaredMethods();
        indentLevel++;
        if (!isEmpty(methods)) {
            Arrays.stream(methods).forEach(m -> {
                print("Method: %s", m.getName());
                printSpecificIndent("Exceptions Thrown: ", indentLevel+1);
                indentLevel += 2;
                printNames(m.getExceptionTypes());
                indentLevel -= 2;
                printSpecificIndent("Parameter Types: ", indentLevel+1);
                indentLevel += 2;
                printNames(m.getParameterTypes());
                indentLevel -= 2;
                printSpecificIndent("Return Type: ", indentLevel+1);
                printSpecificIndent(m.getReturnType().getName(), indentLevel+2);
                printSpecificIndent("Modifiers: ", indentLevel+1);
                printSpecificIndent(Modifier.toString(m.getModifiers()), indentLevel+2);
            });
        }
        indentLevel--;
        print("Declared Constructors:");
        Constructor[] constructors = cls.getDeclaredConstructors();
        indentLevel++;
        if (!isEmpty(constructors)) {
            Arrays.stream(constructors).forEach(c -> {
               printSpecificIndent("Constructor:", indentLevel+1);
               printSpecificIndent("Parameter Types:", indentLevel+2);
               indentLevel += 3;
               printNames(c.getParameterTypes());
               indentLevel -= 3;
               printSpecificIndent("Modifiers:", indentLevel+2);
               printSpecificIndent(Modifier.toString(c.getModifiers()), indentLevel+3);
            });
        }
        indentLevel--;
        print("Declared Fields:");
        Field[] fields = cls.getDeclaredFields();
        indentLevel++;
        if (!isEmpty(fields)) {
            Arrays.stream(fields).forEach(f -> {
                print("Field: %s", f.getName());
                printSpecificIndent("Type:", indentLevel+1);
                printSpecificIndent(f.getType().getName(), indentLevel+2);
                printSpecificIndent("Modifiers:", indentLevel+1);
                printSpecificIndent(Modifier.toString(f.getModifiers()), indentLevel+2);
                printSpecificIndent("Value:", indentLevel+1);
                f.setAccessible(true);
                try {
                    Object value = f.get(obj);
                    if (value == null) {
                        printSpecificIndent("null", indentLevel+2);
                    } else if (f.getType().isPrimitive()) {
                        printSpecificIndent(value.toString(), indentLevel+2);
                    } else {
                        if (recursive) {

                        }
                        printSpecificIndent("Reference Value: %s %s", indentLevel+2, value.toString(), System.identityHashCode(value));
                    }
                } catch (IllegalAccessException e) { }
            });
        }
        indentLevel--;
        //print("Inherited Constructors:");

    }

    private void printNames(Class[] list) {
        if (!isEmpty(list)) {
            Arrays.stream(list).forEach(c -> print(c.getName()));
        }
    }

    private <T> boolean isEmpty(T[] list) {
        boolean empty = list.length == 0;
        if (empty) print("None");
        return empty;
    }

    private void print(String format, Object ... args) {
        System.out.print(getIndentStr());
        System.out.printf(format + "\n", args);
    }

    private void printSpecificIndent(String format, int indentLevel, Object ... args) {
        System.out.print(getIndentStr(indentLevel));
        System.out.printf(format + "\n", args);
    }

    private String getIndentStr() {
        return createIndentStr(indentLevel);
    }

    private String getIndentStr(int n) {
        return createIndentStr(n);
    }

    private String createIndentStr(int n) {
        StringBuilder indentStringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            indentStringBuilder.append('\t');
        }
        return indentStringBuilder.toString();
    }
}
