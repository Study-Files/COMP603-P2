package project2;

import org.junit.*;
import project2.Database.DatabaseController;
import project2.Database.DatabasePets;
import project2.Database.DatabaseUser;
import project2.Pets.Pet;
import project2.Pets.Types.Dog;

import java.sql.SQLException;
import java.util.HashSet;

public class Project2Test {

    DatabaseController database;

    @Before
    public void setUp() {
        database = new DatabaseController();
        DatabaseUser.register(database.getConnection(), "test", "test");
        DatabasePets.savePet(database.getConnection(), "test", new Dog("test", 1));
    }

    @After
    public void tearDown() {
        DatabaseUser.deleteUser(database.getConnection(), "test");
        DatabasePets.deletePet(database.getConnection(), "test", "test");
        database.closeConnection();
        database = null;
    }

    @Test
    public void testDatabase() {
        // Test that database exists
        Assert.assertNotNull(database);
    }

    @Test()
    public void testDatabaseConnectionNotClosed() throws SQLException {
        // Test that connection is not closed
        Assert.assertFalse(database.getConnection().isClosed());
    }

    @Test
    public void testDatabaseLogin() {
        // Test that user can login
        Assert.assertTrue(DatabaseUser.login(database.getConnection(), "test", "test"));
    }

    @Test
    public void testGetPets() {
        // Test getPets()
        Assert.assertEquals(DatabasePets.getPets(database.getConnection(), "test").size(), 1);
    }

    @Test
    public void testGetPets2() {
        // Test that code doesn't crash when there are no pets
        Assert.assertEquals(DatabasePets.getPets(database.getConnection(), "jack").size(), 0);
    }

    @Test
    public void testIncreasePetAge() {
        // Test age increase
        HashSet<Pet> pets = DatabasePets.getPets(database.getConnection(), "test");
        Pet pet = pets.iterator().next();
        pet.increaseAge();
        int expected = 2;
        int actual = pet.getAge();
        Assert.assertEquals(expected, actual);
    }

}
