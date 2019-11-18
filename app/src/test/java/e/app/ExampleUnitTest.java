package e.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void reflex() {
        ReflexClass reflexClass = new ReflexClass();
        UtilsReflex.getField(reflexClass, "one", Integer.class);
        Object[] args = {"first", 2};
        Class<?>[] argTypes = {String.class, Integer.class};
        String ans = UtilsReflex.callMethod(reflexClass, String.class, "concatenate", args, argTypes);

        Class<?>[] argTypes2 = {String.class, Integer.class};
        Object[] args2 = {"private", -1};
        System.out.println(UtilsReflex.constructInstance(ReflexClass.class, args2, argTypes2, true).toString());
        System.out.println("hello");
    }
}