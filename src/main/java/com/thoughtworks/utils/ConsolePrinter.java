package com.thoughtworks.utils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsolePrinter {

    public static void print(Object s){
        System.out.println(s);
    }

    public static void print(Object...s){
        String str = Stream.of(s).map(Object::toString).collect(Collectors.joining(" "));
        print(str);
    }
}
