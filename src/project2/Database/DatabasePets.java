package project2.Database;

import project2.Pets.Pet;
import project2.Pets.Types.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class DatabasePets {

    public static HashSet<Pet> getPets(Connection connection, String username) {
        HashSet<Pet> pets = new HashSet<>();
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM PETS WHERE USERNAME = '" + username + "'").executeQuery();
            while (rs.next()) {
                String name = rs.getString("NAME");
                String type = rs.getString("TYPE");
                int hatchTime = 0;
                int age = 0;
                int happiness = 0;
                int hunger = 0;
                int health = 0;
                if (type.equalsIgnoreCase("egg")) {
                    hatchTime = rs.getInt("HATCH_TIME");
                } else {
                    age = rs.getInt("AGE");
                    happiness = rs.getInt("HAPPINESS");
                    hunger = rs.getInt("HUNGER");
                    health = rs.getInt("HEALTH");
                }

                switch (type) {
                    case "Egg" -> pets.add(new Egg(name, hatchTime));
                    case "Cat" -> pets.add(new Cat(name, age, happiness, hunger, health));
                    case "Dog" -> pets.add(new Dog(name, age, happiness, hunger, health));
                    case "Bird" -> pets.add(new Bird(name, age, happiness, hunger, health));
                    case "Fish" -> pets.add(new Fish(name, age, happiness, hunger, health));
                    case "Rabbit" -> pets.add(new Rabbit(name, age, happiness, hunger, health));
                }

            }
        } catch (SQLException e) {
            System.out.println("Error getting pets");
        }
        return pets;
    }

    public static int[] getCycleData(Connection connection, String username, String petName) {
        int[] cycleData = new int[3];
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM CYCLES WHERE USERNAME = '" + username + "' AND PET_NAME = '" + petName + "'").executeQuery();
            while (rs.next()) {
                cycleData[0] = rs.getInt("CYCLE");
                cycleData[1] = rs.getInt("TOTAL_CYCLES");
                cycleData[2] = rs.getInt("YEARS");
            }
        } catch (SQLException e) {
            System.out.println("Error getting cycle data");
        }
        return cycleData;
    }

    public static void updateCycleData(Connection connection, String username, String petName, int cycle, int totalCycles, int year) {
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM CYCLES WHERE USERNAME = '" + username + "' AND PET_NAME = '" + petName + "'").executeQuery();
            if (rs.next()) {
                connection.prepareStatement("UPDATE CYCLES SET CYCLE = " + cycle + ", TOTAL_CYCLES = " + totalCycles + ", YEARS = " + year + " WHERE USERNAME = '" + username + "' AND PET_NAME = '" + petName + "'").executeUpdate();
            } else {
                connection.prepareStatement("INSERT INTO CYCLES VALUES ('" + username + "', '" + petName + "', " + cycle + ", " + totalCycles + ", " + year + ")").executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error updating cycle data");
        }
    }

    public static void savePet(Connection connection, String username, Pet pet) {
        // insert into pets but update if pet already exists
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM PETS WHERE USERNAME = '" + username + "' AND NAME = '" + pet.getName() + "'").executeQuery();
            if (rs.next() && rs.getString("NAME").equals(pet.getName())) {
                if (pet instanceof Egg) {
                    connection.prepareStatement("UPDATE PETS SET TYPE = 'Egg', HATCH_TIME = " +  pet.getHatchTimeLeft() + " WHERE USERNAME = '" + username + "' AND NAME = '" + pet.getName() + "'").executeUpdate();
                }else {
                    connection.prepareStatement("UPDATE PETS SET TYPE = '" + pet.getType() + "', AGE = " + pet.getAge() + ", HAPPINESS = " + pet.getHappiness() + ", HUNGER = " + pet.getHunger() + ", HEALTH = " + pet.getHealth() + ", HATCH_TIME = null WHERE USERNAME = '" + username + "' AND NAME = '" + pet.getName() + "'").executeUpdate();
                }
            }
            else {
                if (pet instanceof Egg) {
                    connection.prepareStatement("INSERT INTO PETS (USERNAME, NAME, TYPE, HATCH_TIME) VALUES ('" + username + "', '" + pet.getName() + "', 'Egg', " + pet.getHatchTimeLeft() + ")").executeUpdate();
                }else {
                    connection.prepareStatement("INSERT INTO PETS (USERNAME, NAME, TYPE, AGE, HAPPINESS, HUNGER, HEALTH) VALUES ('" + username + "', '" + pet.getName() + "', '" + pet.getType() + "', " + pet.getAge() + ", " + pet.getHappiness() + ", " + pet.getHunger() + ", " + pet.getHealth() + ")").executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating pet");
        }
    }

    public static void renamePet(Connection connection, String username, String petName, String newName) {
        try {
            connection.prepareStatement("UPDATE PETS SET NAME = '" + newName + "' WHERE USERNAME = '" + username + "' AND NAME = '" + petName + "'").executeUpdate();
            connection.prepareStatement("UPDATE CYCLES SET PET_NAME = '" + newName + "' WHERE USERNAME = '" + username + "' AND PET_NAME = '" + petName + "'").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error renaming pet");
        }
    }

    public static void deletePet(Connection connection, String username, String petName) {
        try {
            connection.prepareStatement("DELETE FROM PETS WHERE USERNAME = '" + username + "' AND NAME = '" + petName + "'").executeUpdate();
            connection.prepareStatement("DELETE FROM CYCLES WHERE USERNAME = '" + username + "' AND PET_NAME = '" + petName + "'").executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting pet");
        }
    }
}
