package project2.Pets.Types;

import project2.Pets.Pet;
import project2.Pets.PetType;


import java.util.Random;

public class Egg extends Pet {

    // Cycles till hatching
    private final int MaxHatchingTime = 10;
    private final int MinHatchingTime = 5;

    // Total cycles
    private int hatchTime;
    // Total cycles till hatching
    private int hatchTimeLeft;

    private String name;

    // Create a new egg
    public Egg(String name) {
        super(name, PetType.Egg, 0);
        this.name = name;
        this.hatchTime = (int) (Math.random() * (MaxHatchingTime - MinHatchingTime)) + MinHatchingTime;
        this.hatchTimeLeft = hatchTime;
    }

    // Load egg from file
    public Egg(String name, int hatchTime) {
        super(name, PetType.Egg, 0);
        this.name = name;
        this.hatchTime = hatchTime;
        this.hatchTimeLeft = hatchTime;
    }

    // Get pets hatch time remaining
    @Override
    public int getHatchTimeLeft() {
        return hatchTimeLeft;
    }

    // Progress pet stats by 1 cycle
    @Override
    public void tick() {
        hatchTimeLeft--;
    }

    public boolean isHatched() {
        return hatchTimeLeft <= 0;
    }

    // Hatch pet
    @Override
    public Pet hatch() {
        switch (new Random().nextInt(5)) {
            case 0:
                return new Dog(this.name, 1);
            case 1:
                return new Cat(this.name, 1);
            case 2:
                return new Bird(this.name, 1);
            case 3:
                return new Fish(this.name, 1);
            case 4:
                return new Rabbit(this.name, 1);
        }
        throw new RuntimeException("Something went wrong");
    }
}
