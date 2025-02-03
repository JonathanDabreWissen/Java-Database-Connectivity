@FunctionalInterface
interface I{
    public void abc();
    public boolean equals(Object ob);
    public int hashCode();
}

class A implements I{
    public void abc(){
        System.out.println("Abc");
    }
}

class B{
    public void demo(){
        System.out.println("Hi Everybody");
    }
}

public class FunctionalInterfaceDemo {
    public static void main(String[] args) {
        /*
        

        I i1 = new A();
        
        i1.abc();   
        
        I i2 = new I(){
            public void abc(){
                System.out.println("From Anonymous class abc() method");
            }
        };
        
        i2.abc();
        
        B b1 = new B(){
            public void demo(){
                System.out.println("From Anonymous class abc() method");
            }
        };
        
        b1.demo();
        
        I i3 = ()->{
            System.out.println("From Lamba expression");
        };
        
        i3.abc();
        
        */


        I i3 = ()->System.out.println("From Lambda Function");
        i3.abc();
    }   
}
