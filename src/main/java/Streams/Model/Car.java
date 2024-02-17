package Streams.Model;

import java.util.Objects;

public class Car {
    private final int wheels = 4;
    private final String model;

    public Car(String model) {
        this.model = model;
    }

    public int getWheels() {
        return wheels;
    }

    public String getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return wheels == car.wheels && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wheels, model);
    }

    @Override
    public String toString() {
        return "Car{" +
                "wheels=" + wheels +
                ", model='" + model + '\'' +
                '}';
    }
}
