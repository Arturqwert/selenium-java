package Streams;

import Streams.Model.Car;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MyFunction {

    public static List<Car> getCarsList() {
        List<String> models = List.of("mercedes", "bmw", "lexus", "mustang");

        Function<String, Car> function = s -> new Car(s);

        List<Car> cars = new ArrayList<>();

        for (String model : models) {
            cars.add(function.apply(model));
        }

       // cars.forEach(c -> System.out.println(c));
        return cars;
    }
}
