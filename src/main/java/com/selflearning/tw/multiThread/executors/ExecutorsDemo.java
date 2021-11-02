package com.selflearning.tw.multiThread.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsDemo {

    public static void show(){
        var executor = Executors.newFixedThreadPool(2);
        System.out.println(executor.getClass().getName());

        try{
            var future = executor.submit(() -> {
//                System.out.println(Thread.currentThread().getName());
                LongTask.simulate();
                return 1;
            });

            System.out.println("Do more and more work");
            try {
                var result = future.get(); // blocking method: 會在這等到thread task執行完成 才會往下繼續執行
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }finally{
            executor.shutdown();
        }
    }
}
