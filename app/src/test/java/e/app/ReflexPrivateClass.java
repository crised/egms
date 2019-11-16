package e.app;

public class ReflexPrivateClass {
    private String first;
    private int one;

    public ReflexPrivateClass() {
        this.first = "hello";
        this.one = 1;
    }

    private String concatenate(String first, Integer two) {
        return first + two;
    }



}
