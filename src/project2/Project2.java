package project2;

import project2.Pets.Pet;
import project2.Pets.PetType;
import project2.Pets.Types.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import static project2.util.Read.*;
import static project2.util.Write.*;

public class Project2 {

    private static Pet pet;

    // Log file
    private static PrintWriter logFile;


    private static int currentCycle = 0;
    private static int totalCycles = 0;
    private static Scanner scanner;

    public static void main(String[] args) {
        // Make sure the data folder exists
        if (!(new File("./data/").exists())){
            new File("./data/").mkdir();
        }
        // Create log file
        try {
            logFile = new PrintWriter("./data/log.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writeToLog("Log file created");
        scanner = new Scanner(System.in);
        writeToLog("Getting pet...");
        // Get pet from file or create new pet
        Pet pet = getPet();
        // Load cycles from file if it exists
        int[] savedCycles = loadCycles(pet);
        if (savedCycles != null) {
            currentCycle = savedCycles[0];
            totalCycles = savedCycles[1];
        }

        // Game logic loop
        while (true) {
            printPetInfo(pet);
            boolean success;
            // Check if action was successful
            success = actionLogic(pet);
            if (success) {
                currentCycle++;
                totalCycles++;
                // Check if a year has passed
                if (currentCycle == 10 || (pet.getHatchTimeLeft() == 0 && pet.getType() == PetType.Egg)) {
                    currentCycle = 0;
                    writeToLog("Year passed!");
                    System.out.println("Year passed!");
                    if (pet.getType() == PetType.Egg) {
                        writeToLog("Hatching pet...");
                        pet = pet.hatch();
                        System.out.println("Congratulations! You have hatched a " + pet.getType().toString() + "!");
                    } else {
                        pet.increaseAge();
                    }
                }
                if (pet.endScenario()) {
                    pet = end(pet);
                }
            }
        }
    }

    // Gets a pet from a save or creates a new one
    public static Pet getPet(){
        writeToLog("Getting saves...");
        // Get list of saves
        ArrayList savedPets = getAvailableSaves();
        if (savedPets.size() > 0) {
            while (true) {
                writeToLog("Saves found!");
                System.out.println("Available saves: ");
                for (int i = 0; i < savedPets.size(); i++) {
                    System.out.println(i+1 + ": " + savedPets.get(i));
                }
                System.out.println("Please enter the number of the save you would like to load: ('0' for new save. 'quit' to exit)");
                String input = scanner.nextLine();
                // Allow user to quit
                if (input.equalsIgnoreCase("quit")) {
                    writeToLog("Exiting...");
                    System.out.println("Exiting...");
                    logFile.close();
                    System.exit(0);
                // Allow user to create a new save
                } else if (input.equalsIgnoreCase("0")) {
                    Pet newPet = newPet();
                    if (newPet != null) {
                        return newPet;
                    }
                // Try to load a save
                } else {
                    try {
                        int index = Integer.parseInt(input);
                        if (index > 0 && index <= savedPets.size()) {
                            writeToLog("Loading pet...");
                            return loadPet(savedPets.get(index-1).toString());
                        } else {
                            System.out.println("Invalid input!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input!");
                    }
                }
            }
        // No saves found. Create a new pet
        }else {
            Pet newPet = newPet();
            if (newPet == null) {
                System.out.println("Exiting...");
                logFile.close();
                System.exit(0);
            }
            currentCycle = 0;
            totalCycles = 0;
            return newPet;
        }
    }

    // Creates a new pet
    public static Pet newPet(){
        writeToLog("Creating new pet...");
        System.out.println("Creating new pet...");
        while (true) {
            System.out.println("Please enter your pet's name: (Type 'cancel' to cancel)");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("cancel")) {
                return null;
            }
            if (!checkName(name)) {
                System.out.println("This pet name is already taken!");
            } else {
                writeToLog("Pet created!");
                return new Egg(name);
            }
        }
    }

    // Prints out the pet's info
    public static void printPetInfo(Pet pet){
        writeToLog("Printing pet info...");
        if (pet.getType() == PetType.Egg) {
            System.out.println(pet.getName() + " is a " + pet.getType() + " and is " + totalCycles + " cycles old. " + pet.getHatchTimeLeft() + " cycles until it hatches.");
        } else {
            System.out.println(pet.getName() + " is a " + pet.getType() + " and is " + pet.getAge() + " years old.");
            System.out.println("Happiness: " + pet.getHappiness() + " | Hunger: " + pet.getHunger() + " | Health: " + pet.getHealth());
        }
    }

    // Game logic for prompts
    public static boolean actionLogic(Pet pet){
        writeToLog("Running action logic...");
        // Game logic for prompts when pet is an egg
        if (pet.getType() == PetType.Egg) {
            System.out.println("What would you like to do? (continue, rename, quit)");
            String input = scanner.nextLine();
            // Continue to next cycle
            if (input.equalsIgnoreCase("continue") || input.equalsIgnoreCase("c")) {
                System.out.println("Continuing to next cycle...");
            // Rename pet
            } else if (input.equalsIgnoreCase("rename") || input.equalsIgnoreCase("r")) {
                writeToLog("Renaming pet...");
                System.out.println("Please enter your pet's new name: ");
                String newName = scanner.nextLine();
                pet.setName(newName);
            // Quit game
            } else if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("q")) {
                writeToLog("Exiting...");
                boolean saved = savePet(pet);
                if (saved) {
                    System.out.println("Pet saved successfully!");
                } else {
                    System.out.println("Error saving pet!");
                }
                saved = saveCycles(currentCycle, totalCycles, pet);
                if (saved) {
                    System.out.println("Cycles saved successfully!");
                } else {
                    System.out.println("Error saving cycles!");
                }
                logFile.close();
                System.exit(0);
            // Invalid input
            } else {
                System.out.println("Invalid input!");
                return false;
            }
        // Game logic for prompts when pet is not an egg
        } else {
            System.out.println("What would you like to do? (continue, feed, play, heal, rename, quit)");
            String input = scanner.nextLine();
            // Continue to next cycle
            if (input.equalsIgnoreCase("continue") || input.equalsIgnoreCase("c")) {
                System.out.println("Continuing to next cycle...");
            // Feed pet
            } else if (input.equalsIgnoreCase("feed") || input.equalsIgnoreCase("f")) {
                System.out.println("Feeding " + pet.getName() + "...");
                pet.feed();
            // Play with pet
            } else if (input.equalsIgnoreCase("play") || input.equalsIgnoreCase("p")) {
                System.out.println("Playing with " + pet.getName() + "...");
                pet.play();
            // Heal pet
            } else if (input.equalsIgnoreCase("heal") || input.equalsIgnoreCase("h")) {
                System.out.println("Healing " + pet.getName() + "...");
                pet.heal();
            // Rename pet
            } else if (input.equalsIgnoreCase("rename") || input.equalsIgnoreCase("r")) {
                writeToLog("Renaming pet...");
                System.out.println("Please enter your pet's new name: ");
                String newName = scanner.nextLine();
                pet.setName(newName);
            // Quit game
            } else if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("q")) {
                writeToLog("Exiting...");
                boolean saved = savePet(pet);
                if (saved) {
                    System.out.println("Pet saved successfully!");
                } else {
                    System.out.println("Error saving pet!");
                }
                saved = saveCycles(currentCycle, totalCycles, pet);
                if (saved) {
                    System.out.println("Cycles saved successfully!");
                } else {
                    System.out.println("Error saving cycles!");
                }
                logFile.close();
                System.exit(0);
            // Invalid input
            } else {
                System.out.println("Invalid input!");
                return false;
            }
        }
        pet.tick();
        return true;
    }

    // End of game logic
    public static Pet end(Pet pet){
        writeToLog("Pet died!");
        System.out.println("You lasted " + totalCycles + " cycles and your pet lived to be " + pet.getAge() + " years old!");
        savePastPets(pet, totalCycles);
        deleteSaveData(pet);
        while (true) {
            System.out.println("Would you like to start a new simulation? (yes/no)");
            String input = scanner.nextLine();
            // Player wants to get a new pet
            if (input.equalsIgnoreCase("yes")) {
                writeToLog("Creating new pet...");
                System.out.println("Starting new simulation...");
                System.out.println("Please enter your pet's name: ");
                String name = scanner.nextLine();
                totalCycles = 0;
                pet = new Egg(name);
                return pet;
            // Player wants to quit
            } else if (input.equalsIgnoreCase("no")) {
                writeToLog("Exiting...");
                System.out.println("Exiting...");
                logFile.close();
                System.exit(0);
            // Invalid input
            } else {
                System.out.println("Invalid input!");
            }
        }
    }

    // Deletes save data if pet dies
    public static void deleteSaveData(Pet pet){
        writeToLog("Deleting save data...");
        File file = new File("./data/"+pet.getName()+"-cycles.txt");
        file.delete();
        file = new File("./data/"+pet.getName()+".txt");
        file.delete();
    }


    // Log file logging
    public static void writeToLog(String message){
        logFile.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ": " + message);
    }
}
