package Streams;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class MySupplier {
    public static void main(String[] args) {


        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 1;
            }
        };

        Long id = getId().orElseGet(System::currentTimeMillis);
        System.out.println(id * 2);
    }

    public static Optional<Long> getId(){
        return Optional.empty();
    }
}
