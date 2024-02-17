package Streams;

import java.util.List;
import java.util.function.Consumer;

public class MyConsumer {
    public static void main(String[] args) {

        List<Integer> list = List.of(1, 2, 3);

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println(integer * 2);

            }
        };

        list.forEach(consumer);
        System.out.println(list);


        list.forEach(item -> System.out.println(item + 2));
    }
}
