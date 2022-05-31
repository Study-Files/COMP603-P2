package project2;

import org.junit.*;
import project2.Database.DatabaseController;
import project2.Database.DatabasePets;
import project2.Database.DatabaseUser;
import project2.Pets.Types.Dog;

import java.sql.SQLException;

public class Project2Test {

    DatabaseController database;

    @Before
    public void setUp() {
        database = new DatabaseController();
        DatabaseUser.register(database.getConnection(), "test", "test");
        DatabasePets.savePet(database.getConnection(), "test", new Dog("test", 1));
        DatabasePets.savePet(database.getConnection(), "jack", new Dog("jacks dog", 1));
    }

    @After
    public void tearDown() {
        database.closeConnection();
        database = null;
    }

    @Test
    public void testDatabase() {
        Assert.assertNotNull(database);
    }

    @Test()
    public void testDatabaseConnection() throws SQLException {
        Assert.assertFalse(database.getConnection().isClosed());
    }

    @Test
    public void testDatabaseLogin() {
        Assert.assertTrue(DatabaseUser.login(database.getConnection(), "test", "test"));
    }



}
