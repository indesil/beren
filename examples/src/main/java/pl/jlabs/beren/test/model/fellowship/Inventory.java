package pl.jlabs.beren.test.model.fellowship;

public class Inventory {
    private String name;
    private double value;

    public String getName() {
        return name;
    }

    public Inventory setName(String name) {
        this.name = name;
        return this;
    }

    public double getValue() {
        return value;
    }

    public Inventory withValue(double value) {
        this.value = value;
        return this;
    }
}
