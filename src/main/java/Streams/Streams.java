package Streams;

import Streams.Model.Car;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) {

        List<Car> cars = MyFunction.getCarsList();

        var res = cars.stream()
                .filter(c -> c.getWheels() > 0)
                .sorted((i1, i2) -> (int)(i2.getHorsePower() - i1.getHorsePower()))
                .map(i -> i.getModel())
                .filter(m -> m.startsWith("m"))
                .map(m -> new Car(m))
                .filter(c -> c.getHorsePower() > 100)
                .collect(Collectors.toMap(
                        m -> "Model " + m.getModel(),
                        hp -> + hp.getHorsePower()
                ))
                ;

        System.out.println(res);
    }
}