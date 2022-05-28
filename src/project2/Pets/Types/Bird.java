package project2.Pets.Types;

import project2.Pets.Pet;
import project2.Pets.PetType;

public class Bird extends Pet {

    private final int MAX_HAPPINESS = 3;
    private final int MAX_HUNGER = 6;
    private final int MAX_HEALTH = 6;

    private String name;
    private int age;
    private int happiness;
    private int hunger;
    private int health;

    // Create new pet
    public Bird(String name, int age) {
        super(name, PetType.Bird, age);

        this.name = name;
        this.age = age;
        this.happiness = MAX_HAPPINESS;
        this.hunger = MAX_HUNGER;
        this.health = MAX_HEALTH;
    }

    // Load pet from file
    public Bird(String name, int age, int happiness, int hunger, int health) {
        super(name, PetType.Bird, age);

        this.name = name;
        this.age = age;
        this.happiness = happiness;
        this.hunger = hunger;
        this.health = health;
    }

    // Get pets stats
    @Override
    public int getHappiness() {
        return happiness;
    }

    @Override
    public int getHunger() {
        return hunger;
    }

    @Override
    public int getHealth() {
        return health;
    }

    // Check if pet dies
    @Override
    public boolean endScenario() {
        if (happiness == 0 || hunger == 0 || health == 0) {
            double chance = Math.random();
            if (chance < 0.3) {
                if (happiness == 0) {
                    System.out.println("Your bird left due to lack of happiness.");
                } else {
                    System.out.println("Your bird died.");
                }
                return true;
            }
        }
        return false;
    }

    // Reset pets stats to max
    @Override
    public void feed() {
        hunger = MAX_HUNGER;
    }

    @Override
    public void play() {
        happiness = MAX_HAPPINESS;
    }

    @Override
    public void heal() {
        health = MAX_HEALTH;
    }

    // Progress pet stats by 1 cycle
    @Override
    public void tick() {
        double happinessChance = Math.random();
        double hungerChance = Math.random();
        double healthChance = Math.random();
        if (happinessChance < 0.5 && happiness > 0) {
            happiness--;
        }
        if (hungerChance < 0.5 && hunger > 0) {
            hunger--;
        }
        if (healthChance < 0.5 && health > 0) {
            health--;
        }
    }

}
