import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by unlue on 22/08/14.
 */
public class GeneralTests {

    @Test
    public void checkIterableNull() {
        List<Integer> list = new ArrayList<Integer>();

        Iterable<Integer> iterable = list;

        for (int hede : iterable) {
            System.out.println(hede);
        }

        System.out.println(iterable.toString());
    }
}
