package io.github.indesil.beren.test.model.fellowship;

public class Hero {
    private String name;
    private Integer age;
    private Integer height;
    private Integer weight;
    private String homeland;
    private Race race;
    private Object weapon;

    public String getName() {
        return name;
    }

    public Hero withName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public Hero withAge(Integer age) {
        this.age = age;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public Hero withHeight(Integer height) {
        this.height = height;
        return this;
    }

    public Integer getWeight() {
        return weight;
    }

    public Hero withWeight(Integer weight) {
        this.weight = weight;
        return this;
    }

    public String getHomeland() {
        return homeland;
    }

    public Hero withHomeland(String homeland) {
        this.homeland = homeland;
        return this;
    }

    public Race getRace() {
        return race;
    }

    public Hero withRace(Race race) {
        this.race = race;
        return this;
    }

    public Object getWeapon() {
        return weapon;
    }

    public Hero withWeapon(Object weapon) {
        this.weapon = weapon;
        return this;
    }
}
