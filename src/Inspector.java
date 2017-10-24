import java.lang.reflect.Method;
import java.util.Arrays;

public class Inspector {

    public Inspector() { }

    public void inspect(Object obj, boolean recursive) {
        System.out.printf("Inspecting object: %s\n\n", obj.toString());
        Class cls = obj.getClass();
        System.out.printf("Class: %s\n", cls.getName());
        System.out.printf("Immediate Superclass: %s\n", cls.getSuperclass().getName());
        System.out.printf("Implemented Interfaces: \n");
        print(cls.getInterfaces(), 1);
        System.out.printf("Declared Methods: \n");
        Method[] methods = cls.getDeclaredMethods();
        if (methods.length > 0) {
            Arrays.stream(methods).forEach(m -> {
                System.out.printf("\tMethod: %s\n", m.getName());
                System.out.printf("\t\tExceptions Thrown: \n");
                print(m.getExceptionTypes(), 3);
            });
        } else {
            System.out.println("\tNone\n");
        }
    }

    private void print(Class[] list, int indentLevel) {
        StringBuilder indentStringBuilder = new StringBuilder();
        for (int i = 0; i < indentLevel; i++) {
            indentStringBuilder.append('\t');
        }
        if (list.length > 0) {
            Arrays.stream(list).forEach(c -> System.out.printf("%s%s\n", indentStringBuilder.toString(), c.getName()));
        } else {
            System.out.printf("%sNone\n", indentStringBuilder.toString());
        }
    }

}
