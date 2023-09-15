package ru.netology;

import java.util.ArrayList;
import java.util.Map;

public class Application {
    public void run() throws InterruptedException {

        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1001; i++) {
            threads.add(new Thread(new RoutMap(RoutMap.generateRoute("RLRFR", 100))));
        }

        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }

        RoutMap.printMap(new Printer());
    }
}
