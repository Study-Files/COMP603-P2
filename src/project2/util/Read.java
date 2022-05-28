package project2.util;

import project2.Pets.Pet;
import project2.Pets.Types.*;

import java.io.*;
import java.util.ArrayList;

public class Read {

    // Load pet from file
    public static Pet loadPet(String saveName) {
        try {
            Pet pet = null;
            BufferedReader input = new BufferedReader(new FileReader("./data/"+saveName+".txt"));
            String line;
            String name = null;
            String type = null;
            int age = 0;
            int happiness = 0;
            int hunger = 0;
            int health = 0;
            int hatchTime = 0;
            // Read in pet data and save to variables
            while ((line = input.readLine()) != null) {
                String value = line.substring(line.indexOf(":") + 1).trim();
                if (line.contains("name")) {
                    name = value;
                } else if (line.contains("type")) {
                    type = value;
                } else {
                    int valueInt = Integer.parseInt(value);
                    if (line.contains("age")) {
                        age = valueInt;
                    } else if (line.contains("happiness")) {
                        happiness = valueInt;
                    } else if (line.contains("hunger")) {
                        hunger = valueInt;
                    } else if (line.contains("health")) {
                        health = valueInt;
                    } else if (line.contains("hatchTime")) {
                        hatchTime = valueInt;
                    }
                }
            }
            // If pet type is not found, return null
            if (type == null) {
                return null;
            }
            System.out.println("Attempting to load pet...");
            // Create pet based on type
            switch (type) {
                case "Egg":
                    pet = new Egg(name, hatchTime);
                    break;
                case "Cat":
                    pet = new Cat(name, age, happiness, hunger, health);
                    break;
                case "Dog":
                    pet = new Dog(name, age, happiness, hunger, health);
                    break;
                case "Bird":
                    pet = new Bird(name, age, happiness, hunger, health);
                    break;
                case "Fish":
                    pet = new Fish(name, age, happiness, hunger, health);
                    break;
                case "Rabbit":
                    pet = new Rabbit(name, age, happiness, hunger, health);
                    break;
            }
            input.close();
            System.out.println(pet.getName() + " loaded successfully.");
            return pet;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Load cycle data from file
    public static int[] loadCycles(Pet pet) {
        try {
            BufferedReader input = new BufferedReader(new FileReader("./data/"+pet.getName()+"-cycles.txt"));
            String line;
            int currentCycle = 0;
            int totalCycles = 0;
            while ((line = input.readLine()) != null) {
                String value = line.substring(line.indexOf(":") + 1).trim();
                if (line.contains("currentCycle")) {
                    currentCycle = Integer.parseInt(value);
                } else if (line.contains("totalCycles")) {
                    totalCycles = Integer.parseInt(value);
                }
            }
            input.close();
            return new int[]{currentCycle, totalCycles};
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all pets from the data folder
    public static ArrayList getAvailableSaves() {
        File saves = new File("./data/");
        File[] files = saves.listFiles();
        if (files == null) {
            return null;
        }
        ArrayList names = new ArrayList();
        for (File file : files) {
            if (!(file.getName().contains("cycles")) && !(file.getName().equalsIgnoreCase("Past Pets.txt") || file.getName().equalsIgnoreCase("log.txt"))) {
                names.add(file.getName().replace(".txt", ""));
            }
        }
        return names;
    }

    // Check if a pet is already saved or if name of pet is a core file
    public static boolean checkName(String name) {
        File saves = new File("./data/");
        File[] files = saves.listFiles();
        if (files == null) {
            return false;
        }
        for (File file : files) {
            if (file.getName().equalsIgnoreCase(name + ".txt")) {
                return false;
            }
        }
        return true;
    }

}
