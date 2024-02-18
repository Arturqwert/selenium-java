package Streams.Model;

import java.util.Objects;

public class Car {
    private final int wheels = 4;
    private final String model;

    private final double horsePower;
    private static double coefficient = 1;

    public Car(String model) {
        this.model = model;
        horsePower = 100 * coefficient;
        coefficient = coefficient * 1.3;
    }

    public int getWheels() {
        return wheels;
    }

    public String getModel() {
        return model;
    }

    public double getHorsePower() {
        return horsePower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return wheels == car.wheels && horsePower == car.horsePower && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wheels, model, horsePower);
    }

    @Override
    public String toString() {
        return "Car{" +
                "wheels=" + wheels +
                ", model='" + model + '\'' +
                ", horsePower=" + horsePower +
                '}';
    }
}
