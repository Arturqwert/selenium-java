package Streams;

import java.util.List;
import java.util.function.Predicate;

public class MyPredicate {
    public static void main(String[] args) {
        List<Integer> list = List.of(1, -1, 3);

        Predicate<Integer> predicate = integer -> integer > 0;

        for (Integer i : list) {
            if(predicate.test(i)){
                System.out.println(i);
            }
        }
    }
}
