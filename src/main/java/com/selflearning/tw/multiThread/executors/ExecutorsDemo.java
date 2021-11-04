package com.selflearning.tw.multiThread.executors;

import java.util.concurrent.*;

public class ExecutorsDemo {

    public static void show(){
        // Executors 方便管理thread，但無法避免 concurrency problem (兩個thread 同時改動到同一個資源)
        var executor = Executors.newFixedThreadPool(2);
        System.out.println(executor.getClass().getName());
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(100);

        try{
            var future = executor.submit(() -> {
//                System.out.println(Thread.currentThread().getName());
                LongTask.simulate();
                return 1;
            });

            System.out.println("Do more and more work");
            try {
                var result = future.get(); // blocking method: 會在這等到thread task執行完成 才會往下繼續執行
//                future.get(10, TimeUnit.SECONDS); // 設定timeout 10 sec
                System.out.println(result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }finally{
            executor.shutdown();
        }
    }
}
