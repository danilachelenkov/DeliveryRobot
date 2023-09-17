package ru.netology;

import java.util.*;

public class RoutMap implements Runnable {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    private String route;

    public RoutMap(String route) {
        this.route = route;

    }

    @Override
    public void run() {
        System.out.println(route);
        generateStatistics(route);
    }

    private void generateStatistics(String route) {

        int n = 0;
        for (int i = 0; i < route.length(); i++) {
            if (route.charAt(i) == 'R')
                n++;
        }

        synchronized (sizeToFreq) {
            if (sizeToFreq.containsKey(n)) {
                sizeToFreq.put(n, sizeToFreq.get(n) + 1);
            } else {
                sizeToFreq.put(n, 1);
            }
            sizeToFreq.notify();
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();

        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void printMap(Printable printer) {
        try {
            if (!sizeToFreq.isEmpty()) {
                Map.Entry<Integer, Integer> entry = getMaxRepeat();

                printer.print(String.format("Самое частое количество повторений %s (встретилось %s раз)",
                        entry.getKey().toString(), entry.getValue().toString()));

                printer.print("Другие размеры: ");
                for (Map.Entry<Integer, Integer> item : sizeToFreq.entrySet()) {
                    printer.print(String.format("-%s (%s раз) ", item.getKey().toString(), item.getValue().toString()));
                }
            } else {
                printer.print("Список пустой");
            }
        } catch (RuntimeException ex) {
            printer.print(ex.getMessage());
        }

    }

    public static Map.Entry<Integer, Integer> getMaxRepeat() {
        Optional<Map.Entry<Integer, Integer>> max;
        max = sizeToFreq.entrySet()
                .stream()
                .max(Comparator.comparingInt(Map.Entry::getValue));

        if (!max.isPresent()) {
            throw new IllegalStateException("Map is Empty");
        }
        return max.get();
    }
}
