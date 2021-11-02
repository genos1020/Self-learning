package com.selflearning.tw.multiThread.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {

    public static void show(){
        var executor = Executors.newFixedThreadPool(2);
        System.out.println(executor.getClass().getName());

        try{
            for(var i = 0; i < 10; i++)
                executor.submit(()->{
                    System.out.println(Thread.currentThread().getName());
                });
        }finally{
            executor.shutdown();
        }
    }
}
