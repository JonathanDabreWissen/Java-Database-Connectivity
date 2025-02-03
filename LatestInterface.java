
interface I{
    public void abc();
    public default void xyz(){
        System.out.println("Default in interface");
    }

    public static void pqr(){
        System.out.println("Static in interface");
    }

    private  void demo(){
        System.out.println("private method in interface");
    }
}

class A implements I{
    public void abc(){
        System.out.println("From A class abc() method");
    }
}

interface X{
    public default void hello(){
        System.out.println("hehehe x");
    }
}

interface Y{
    public default void hello(){
        System.out.println("hehehe y");
    }
}

class B implements X, Y{
    public void hello(){
        System.out.println("B class hello");
        X.super().hello();
        Y.super().hello();
    }
}
public interface LatestInterface {
    public static void main(String[] args) {
        System.out.println("Hello world");
        I.pqr();
        A a1 = new A();

        a1.xyz();
        a1.abc();

        B b1 = new B();

        b1.hello();
    }
}
