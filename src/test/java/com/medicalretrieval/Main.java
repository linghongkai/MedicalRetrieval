package com.medicalretrieval;

public class Main {
    private Main(){
        System.out.println("构造函数");
    }
    static Main main;
    static void t(){
        if (main==null){
            main = new Main();
        }
        System.out.println("222");
    }
}
