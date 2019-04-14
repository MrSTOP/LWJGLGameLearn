package ykw;

import ykw.common.GameEngine;
import ykw.common.IGameLogic;

public class Main {
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new ColorGame();
            GameEngine gameEngine = new GameEngine("ColorGame", 800, 600, vSync, gameLogic);
            gameEngine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
