package Streams;
import Streams.Model.Car;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
    public static void main(String[] args) {

        List<Car> cars = MyFunction.getCarsList();

        Stream<Car> res = cars.stream()
                .filter(c -> c.getWheels() > 0)
                ;

        System.out.println(res);
    }
}
