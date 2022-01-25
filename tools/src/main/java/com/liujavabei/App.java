package com.liujavabei;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add(1,"c");
        list.remove(2);
        for (String string : list) {
            System.out.println(string);
        }
        System.out.println("Hello World!");
    }
}
