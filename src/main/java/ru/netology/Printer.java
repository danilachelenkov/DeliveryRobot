package ru.netology;

public class Printer implements Printable{
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }
}
