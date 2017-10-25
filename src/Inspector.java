import java.lang.reflect.Method;
import java.util.Arrays;

public class Inspector {

    private int indentLevel = 0;

    public Inspector() { }

    public void inspect(Object obj, boolean recursive) {
        print("Inspecting object: %s\n", obj.toString());
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
        if (methods.length > 0) {
            Arrays.stream(methods).forEach(m -> {
                print("Method: %s", m.getName());
                printSpecificIndent("Exceptions Thrown: ", indentLevel+1);
                indentLevel += 2;
                printNames(m.getExceptionTypes());
                indentLevel -= 2;
            });
        } else {
            print("None");
        }
        indentLevel--;
    }

    private void printNames(Class[] list) {
        if (list.length > 0) {
            Arrays.stream(list).forEach(c -> print(c.getName()));
        } else {
            print("None");
        }
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
