package project2;

import project2.GUI.*;

public class Project2 {

    public static void main(String[] args) {
        LoginPanel loginPanel = new LoginPanel();
        MenuPanel menu = new MenuPanel();
        GamePanel game = new GamePanel();
        GameOverPanel gameOver = new GameOverPanel();
        new GUIController(loginPanel, menu, game, gameOver);
    }
}
