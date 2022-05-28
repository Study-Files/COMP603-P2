package project2.util;

import project2.Pets.Pet;
import project2.Pets.PetType;

import javax.xml.crypto.Data;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Write {

    // Save pet to file
    public static boolean savePet(Pet pet) {
        System.out.println("Saving pet...");
        try {
            PrintWriter writer = new PrintWriter("./data/"+pet.getName()+".txt");
            writer.println("name: " + pet.getName());
            writer.println("type: " + pet.getType());
            writer.println("age: " + pet.getAge());
            if (!(pet.getType().equals(PetType.Egg))) {
                writer.println("happiness: " + pet.getHappiness());
                writer.println("hunger: " + pet.getHunger());
                writer.println("health: " + pet.getHealth());
            }else {
                writer.println("hatchTime: " + pet.getHatchTimeLeft());
            }
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Save the cycles to file
    public static boolean saveCycles(int currentCycle, int totalCycles, Pet pet) {
        System.out.println("Saving cycles...");
        try {
            PrintWriter writer = new PrintWriter("./data/"+pet.getName()+"-cycles.txt");
            writer.println("currentCycle: " + currentCycle);
            writer.println("totalCycles: " + totalCycles);
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Save a pet that died to file
    public static void savePastPets(Pet pet, int totalCycles){
        System.out.println("Adding pet to Past Pets...");
        try {
            FileWriter fw = new FileWriter("./data/Past Pets.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(pet.getName() + " was a " + pet.getType() + " and lasted " + totalCycles + " cycles and was " + pet.getAge() + " years old.");
            bw.newLine();
            bw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
