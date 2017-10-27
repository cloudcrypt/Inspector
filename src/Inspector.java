import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Inspector {

    private int indentLevel = 0;

    private ArrayList<Object> queue;

    private Object obj;

    private boolean recursive;

    public Inspector() {
        queue = new ArrayList<>();
    }

    public void inspect(Object obj, boolean recursive) {
        this.recursive = recursive;
        queue.add(obj);
        //while blah blah
        // Object next = queue.remove first one
        this.obj = obj;
        print("Inspecting object: %s %s\n", obj.toString(), System.identityHashCode(obj));
        Class cls = obj.getClass();
        print("Class: %s", cls.getName());
        print("Immediate Superclass: %s", cls.getSuperclass().getName());
        print("Implemented Interfaces: ");
        indentLevel++;
        printNames(cls.getInterfaces());
        indentLevel--;
        print("Declared Constructors:");
        Constructor[] constructors = cls.getDeclaredConstructors();
        indentLevel++;
        if (!isEmpty(constructors)) {
            Arrays.stream(constructors).forEach(this::printConstructor);
        }
        indentLevel--;
        print("Declared Methods: ");
        Method[] methods = cls.getDeclaredMethods();
        indentLevel++;
        if (!isEmpty(methods)) {
            Arrays.stream(methods).forEach(this::printMethod);
        }
        indentLevel--;
        print("Declared Fields:");
        Field[] fields = cls.getDeclaredFields();
        indentLevel++;
        if (!isEmpty(fields)) {
            Arrays.stream(fields).forEach(this::printField);
        }
        indentLevel--;
        print("Inherited Constructors:");
        ArrayList<Constructor> inheritedConstructors = new ArrayList<>();
        Class superClass = cls.getSuperclass();
        while (superClass != null) {
            inheritedConstructors.addAll(Arrays.asList(superClass.getDeclaredConstructors()));
            superClass = superClass.getSuperclass();
        }
        indentLevel++;
        if (!isEmpty(inheritedConstructors)) {
            inheritedConstructors.forEach(this::printConstructor);
        }
        indentLevel--;
        // now do same inheritance for methods, and then fields
        // is there a ned to traverse interface inheritance hierarchy???
    }

    private void printConstructor(Constructor c) {
        print("Constructor: %s", c.getName());
        printSpecificIndent("Modifiers:", indentLevel+1);
        printSpecificIndent(Modifier.toString(c.getModifiers()), indentLevel+2);
        printSpecificIndent("Parameter Types:", indentLevel+1);
        indentAndExecute(2, () -> printNames(c.getParameterTypes()));
    }

    private void printMethod(Method m) {
        print("Method: %s", m.getName());
        printSpecificIndent("Modifiers: ", indentLevel+1);
        printSpecificIndent(Modifier.toString(m.getModifiers()), indentLevel+2);
        printSpecificIndent("Return Type: ", indentLevel+1);
        printSpecificIndent(m.getReturnType().getName(), indentLevel+2);
        printSpecificIndent("Parameter Types: ", indentLevel+1);
        indentAndExecute(2, () -> printNames(m.getParameterTypes()));
        printSpecificIndent("Exceptions Thrown: ", indentLevel+1);
        indentAndExecute(2, () -> printNames(m.getExceptionTypes()));
    }

    private void printField(Field f) {
        print("Field: %s", f.getName());
        printSpecificIndent("Modifiers:", indentLevel+1);
        printSpecificIndent(Modifier.toString(f.getModifiers()), indentLevel+2);
        printSpecificIndent("Type:", indentLevel+1);
        if (!f.getType().isArray()) {
            printSpecificIndent(f.getType().getName(), indentLevel+2);
        } else {
            printSpecificIndent("Array", indentLevel+2);
            printSpecificIndent("Component Type:", indentLevel+1);
            printSpecificIndent(f.getType().getComponentType().getName(), indentLevel+2);
        }
        printSpecificIndent("Value:", indentLevel+1);
        f.setAccessible(true);
        try {
            Object value = f.get(obj);
            indentAndExecute(2, () -> printObjectValue(value));
        } catch (IllegalAccessException e) { }
    }

    private void printObjectValue(Object value) {
        if (value == null) {
            print("null");
            return;
        }
        Class cls = value.getClass();
        if (isPrimitiveOrWrapper(cls)) {
            print(value.toString());
        } else if (cls.isArray()) {
            print("Length: %s", Array.getLength(value));
            print("Contents:");
            for (int i = 0; i < Array.getLength(value); i++) {
                Object element = Array.get(value, i);
                printSpecificIndent("Value:", indentLevel+1);
                indentAndExecute(2, () -> printObjectValue(element));
            }
        } else {
//            if (recursive) {
//                  add value to queue
//            }
            print("Reference Value: %s %s", value.toString(), System.identityHashCode(value));
        }
    }

    private boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive() || (type == Double.class || type == Float.class || type == Long.class ||
                type == Integer.class || type == Short.class || type == Character.class ||
                type == Byte.class || type == Boolean.class);
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

    private <T> boolean isEmpty(ArrayList<T> list) {
        boolean empty = list.size() == 0;
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

    private void indentAndExecute(int indentAmount, Runnable action) {
        indentLevel += indentAmount;
        action.run();
        indentLevel -= indentAmount;
    }
}
