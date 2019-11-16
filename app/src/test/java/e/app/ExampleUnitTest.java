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
        ReflexPrivateClass reflexPrivateClass = new ReflexPrivateClass();
        UtilsReflex.getField(reflexPrivateClass, "one", Integer.class);
        Object[] args = {"first", 2};
        Class<?>[] argTypes = {String.class, Integer.class};
        String ans = UtilsReflex.callMethod(reflexPrivateClass, String.class, "concatenate", args, argTypes);

        System.out.println("hello");
    }
}