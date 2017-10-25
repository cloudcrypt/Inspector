import java.lang.reflect.Method;
import java.util.Arrays;

public class Inspector {

    private int indentLevel = 0;

    public Inspector() { }

    public void inspect(Object obj, boolean recursive) {
        System.out.printf("%sInspecting object: %s\n\n", getIndentStr(), obj.toString());
        Class cls = obj.getClass();
        System.out.printf("%sClass: %s\n", getIndentStr(), cls.getName());
        System.out.printf("%sImmediate Superclass: %s\n", getIndentStr(), cls.getSuperclass().getName());
        System.out.printf("%sImplemented Interfaces: \n", getIndentStr());
        indentLevel++;
        printNames(cls.getInterfaces());
        indentLevel--;
        System.out.printf("%sDeclared Methods: \n", getIndentStr());
        Method[] methods = cls.getDeclaredMethods();
        indentLevel++;
        if (methods.length > 0) {
            Arrays.stream(methods).forEach(m -> {
                System.out.printf("%sMethod: %s\n", getIndentStr(), m.getName());
                System.out.printf("%sExceptions Thrown: \n", getIndentStr(indentLevel+1));
                indentLevel += 2;
                printNames(m.getExceptionTypes());
                indentLevel -= 2;
            });
        } else {
            System.out.printf("%sNone\n", getIndentStr());
        }
        indentLevel--;
    }

    private void printNames(Class[] list) {
        if (list.length > 0) {
            Arrays.stream(list).forEach(c -> System.out.printf("%s%s\n", getIndentStr(), c.getName()));
        } else {
            System.out.printf("%sNone\n", getIndentStr());
        }
    }

    private String getIndentStr(int n) {
        StringBuilder indentStringBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            indentStringBuilder.append('\t');
        }
        return indentStringBuilder.toString();
    }

    private String getIndentStr() {
        StringBuilder indentStringBuilder = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            indentStringBuilder.append('\t');
        }
        return indentStringBuilder.toString();
    }

}
