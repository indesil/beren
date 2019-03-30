package io.github.indesil.beren.test.model.fellowship;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FellowshipOfTheRing {
    private List<Hero> heroes;
    private Map<String, Inventory> heroesStuff;
    private VeryImportElrondAdvices elrondAdvices;

    public List<Hero> getHeroes() {
        return heroes;
    }

    public FellowshipOfTheRing withHeroes(Hero... heroes) {
        this.heroes = Arrays.asList(heroes);
        return this;
    }

    public Map<String, Inventory> getHeroesStuff() {
        return heroesStuff;
    }

    public FellowshipOfTheRing withHeroesStuff(Map<String, Inventory> heroesStuff) {
        this.heroesStuff = heroesStuff;
        return this;
    }

    public VeryImportElrondAdvices getElrondAdvices() {
        return elrondAdvices;
    }

    public FellowshipOfTheRing withElrondAdvices(VeryImportElrondAdvices elrondAdvices) {
        this.elrondAdvices = elrondAdvices;
        return this;
    }
}
