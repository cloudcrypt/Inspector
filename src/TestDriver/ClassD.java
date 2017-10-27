package TestDriver;

public class ClassD implements InterfaceA
{
    public ClassD() {}
    public ClassD(int i) { val3=i; }

    public String toString() { return "ClassD"; }
    
    public int getVal3() { return val3; }

    private ClassA val = new ClassA(17);
    private static ClassA val2;
    private int val3=34;
    private ClassA[] vallarray = new ClassA[10];
    private int[] test = new int[] {1,2,3,4,5,6,7};

    public void func1(int a, double b, boolean c, String s) throws Exception {

    }

    public int func2(String s) throws Exception, ArithmeticException, IllegalMonitorStateException {
        return 0;
    }

    public void func0(int a, boolean c) throws Exception {

    }
}
