package ykw.triangle;

import ykw.engine.GameEngine;
import ykw.engine.IGameLogic;

public class HelloTriangleMain {
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new HelloTriangle();
            GameEngine gameEngine = new GameEngine("Hello Triangle!", 800, 600, vSync, gameLogic);
            gameEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
