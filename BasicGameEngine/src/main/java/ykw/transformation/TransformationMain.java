package ykw.transformation;

import ykw.engine.GameEngine;
import ykw.engine.IGameLogic;

public class TransformationMain {
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new Transformation();
            GameEngine gameEngine = new GameEngine("Transformation", 800, 600, vSync, gameLogic);
            gameEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
