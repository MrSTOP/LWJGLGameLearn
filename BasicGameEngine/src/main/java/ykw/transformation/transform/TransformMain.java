package ykw.transformation.transform;

import ykw.engine.GameEngine;
import ykw.engine.IGameLogic;

public class TransformMain {
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new Transform();
            GameEngine gameEngine = new GameEngine("Transform", 800, 600, vSync, gameLogic);
            gameEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
