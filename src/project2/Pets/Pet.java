package project2.Pets;

public class Pet {

    private String name;
    private PetType type;
    private int age;


    public Pet(String name, PetType type, int age) {
        this.name = name;
        this.type = type;
        this.age = age;
    }

    // Get pets name
    public String getName() {
        return name;
    }

    // Get pets type
    public PetType getType() {
        return type;
    }

    // Get pets age
    public int getAge() {
        return age;
    }

    // Sets a pets name
    public void setName(String name) {
        this.name = name;
    }

    // Increase pet age
    public void increaseAge() {
        age++;
    }

    // Override in Egg class
    public Pet hatch() {
        return null;
    }
    public int getHatchTimeLeft() {
        return 0;
    }

    // Override get pet stats
    public int getHappiness() {
        return 0;
    }
    public int getHunger() {
        return 0;
    }
    public int getHealth() {
        return 0;
    }

    public void play() {}
    public void feed() {}
    public void heal() {}

    public void tick() {}
    public boolean endScenario() {
        return false;
    }

}
