package com.selflearning.tw.multiThread.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

// asynchronous
public class CompletableFuturesDemo {
    public static void show() {
        // 如果沒有指定executor的話，預設為ForkJoinPool.commonPool()
        // commonPool是根據系統的可用thread( 每core為2個thread ) Runtime.getRuntime().availableProcessors()
        // 產生該數量的thread到pool中，e.g. 本機為 2 core = 4 thread
        var executor = Executors.newFixedThreadPool(100);
        var withExecutorFuture = CompletableFuture
                                                    .supplyAsync(() -> {
                                                        LongTask.simulate();
                                                        System.out.println(Thread.currentThread().getName());
                                                        return "with fixed thread pool executor";
                                                    }, executor);

        // non-blocking
        // thenAccept => main thread / thenAcceptAsync => ForkJoinPool.commonPool-worker-7 (另外的thread中執行)
//        withExecutorFuture.thenAcceptAsync((q)-> {
//            System.out.println(Thread.currentThread().getName());
//            System.out.println(q);
//        }); // 2
//        System.out.println("lez go"); // 1

        // blocking
        try {
            System.out.println(Thread.currentThread().getName());
            System.out.println(withExecutorFuture.get()); // 1
            System.out.println("lez go"); // 2
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Supplier<Integer> task = () -> 1;
        var future = CompletableFuture.supplyAsync(task); // non-blocking
        try {
            Integer result = future.get(); // block
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    // Composing Completable Futures: 第一個操作完成時，將其結果作為參數傳遞給第二個操作。
    public static void composingCompletableFutures() {

        getUserEmailAsync()
                .thenCompose(CompletableFuturesDemo::getPlaylistAsync)
                .thenAccept(p -> System.out.println(p));
    }

    public static CompletableFuture<String> getUserEmailAsync() {
        return CompletableFuture.supplyAsync(() -> "email");
    }

    public static CompletableFuture<String> getPlaylistAsync(String email) {
        return CompletableFuture.supplyAsync(() -> "playlist");
    }


    // Combining Completable Futures
    public static void combineCompletableFuture() {
        var first = CompletableFuture
                .supplyAsync(() -> "20USD")
                .thenApply(str -> { // thenApply => 類似 .map 負責改變型態
                    var price = str.replace("USD", "");
                    return Integer.parseInt(price);
                });
        var second = CompletableFuture.supplyAsync(() -> 0.9);
        first.thenCombine(second, (price, exchageRate) -> price * exchageRate)
                .thenAccept(result -> System.out.println(result));
    }


    // Waiting for Many Tasks to Complete
    public static void waitForManyTasks() {
        var first = CompletableFuture.supplyAsync(() -> 1);
        var second = CompletableFuture.supplyAsync(() -> 2);
        var third = CompletableFuture.supplyAsync(() -> 3);
        var all = CompletableFuture.allOf(first, second, third);
        all.thenRun(() -> {
            try {
                var firstResult = first.get();
                var secondResult = second.get();
                var thirdResult = third.get();
                System.out.println(firstResult);
                System.out.println(secondResult);
                System.out.println(thirdResult);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("All are done.");
        });

    }


    // Handling timeouts
    public static void handleTimeouts() {
        var future = CompletableFuture.supplyAsync(() -> {
            LongTask.simulate();
            return 1;
        });
        try {
            var result = future.completeOnTimeout(1, 1, TimeUnit.SECONDS)
                    .get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
