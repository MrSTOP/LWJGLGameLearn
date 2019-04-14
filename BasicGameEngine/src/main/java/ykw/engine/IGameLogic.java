package ykw.engine;

public interface IGameLogic {

    void init() throws Exception;

    void input(Window window);

    void update(int interval);

    void render(Window window, double percentage);
}
