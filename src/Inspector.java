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
        Class[] interfaces = cls.getInterfaces();
        if (interfaces.length > 0) {
            Arrays.stream(interfaces).forEach(c -> System.out.printf("\t%s\n", c.getName()));
        } else {
            System.out.printf("\tNone\n");
        }
        System.out.printf("Declared Methods: \n");
        Method[] methods = cls.getDeclaredMethods();
        if (methods.length > 0) {
            Arrays.stream(methods).forEach(m -> {
                System.out.printf("\tMethod: %s\n", m.getName());
                System.out.printf("\t\tExceptions Thrown: \n");
                Class[] exceptions = m.getExceptionTypes();
                if (exceptions.length > 0) {
                    Arrays.stream(exceptions).forEach(e -> System.out.printf("\t\t\t%s\n", e.getName()));
                } else {
                    System.out.printf("\t\t\tNone\n");
                }
            });
        } else {
            System.out.println("\tNone\n");
        }
    }

}
