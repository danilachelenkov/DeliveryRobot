package ru.netology;

import java.util.Map;

public class MonitorMaxValue implements Runnable {
    private Printable printer;
    private Map<Integer, Integer> mapSizeFreq;

    public MonitorMaxValue(Printable printer, Map<Integer, Integer> mapSizeFreq) {
        this.printer = printer;
        this.mapSizeFreq = mapSizeFreq;
    }

    @Override
    public void run() {

        while (!Thread.interrupted()) {
            if (!mapSizeFreq.isEmpty()) {
                synchronized (mapSizeFreq) {
                    try {
                        mapSizeFreq.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }

                printer.print(getCurrentMaxMapValue(mapSizeFreq));
            }
        }
    }

    private String getCurrentMaxMapValue(Map<Integer, Integer> mapSizeFreq) {

        int maxValue = 0;
        int key = 0;

        for (Map.Entry<Integer, Integer> item : mapSizeFreq.entrySet()) {
            if (item.getValue() >= maxValue) {
                maxValue = item.getValue();
                key = item.getKey();
            }
        }

        return String.format("Current maxValue %s (%s) of sizeFreq", key, maxValue);
    }
}
