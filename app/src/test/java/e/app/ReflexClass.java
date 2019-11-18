package e.app;

public class ReflexClass {
    private String first;
    private int one;

    public ReflexClass() {
        this.first = "hello";
        this.one = 1;
    }

    private ReflexClass(String priv, Integer privInt) {
        this.first = priv;
        this.one = privInt;
    }

    private String concatenate(String first, Integer two) {
        return first + two;
    }


}
