package ykw.texture;

import ykw.engine.GameEngine;
import ykw.engine.IGameLogic;

public class TextureTestMain {
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new TextureTest();
            GameEngine gameEngine = new GameEngine("TextureTest", 800, 600, vSync, gameLogic);
            gameEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
