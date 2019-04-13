package ykw.common;

public class GameEngine implements Runnable {

    private static final int TARGET_FRAME_PER_SECOND = 75;
    private static final int TARGET_TICK_PER_SECOND = 20;
    private static final int MILLISECOND_PER_TICK = 1000 / TARGET_TICK_PER_SECOND;
    private final Window window;
    private final Thread gameLoopThread;
    private final Timer timer;
    private final IGameLogic gameLogic;

    public GameEngine(String windowTitle, int windowWidth, int windowHeight, boolean vSync, IGameLogic gameLogic) {
        gameLoopThread = new Thread(this, "Game Loop Thread");
        window = new Window(windowTitle, windowWidth, windowHeight, vSync);
        this.gameLogic = gameLogic;
        this.timer = new Timer();
    }

    protected void init() throws Exception {
        window.init();
        timer.init();
        gameLogic.init();
    }

    public void start() {
        gameLoopThread.start();
    }

    @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void gameLoop() {
        float elapsedTime;
        boolean running = true;
        while (running && !window.windowShouldClose()) {


            input();

            while (timer.getAccumulator() >= MILLISECOND_PER_TICK) {
                update(MILLISECOND_PER_TICK);
                timer.accumulatorDec(MILLISECOND_PER_TICK);
            }

            render(0.0);

            if (!window.isvSync()) {
                sync();
            }
        }
    }

    private void sync() {
        float loopSlot = 1F / TARGET_FRAME_PER_SECOND;
        double endTime = timer.getPreviousLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected void input() {
        gameLogic.input(window);
    }

    protected void update(int millisecondPerTick) {
        gameLogic.update(millisecondPerTick);
    }

    protected void render(double percentage) {
        gameLogic.render(window);
        window.update();
    }
}
