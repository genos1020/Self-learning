package com.selflearning.tw.multiThread.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CompletableFuturesDemo {
    public static void show(){
        Supplier<Integer> task = () -> 1;
        var future = CompletableFuture.supplyAsync(task); // non-blocking
        try {
            Integer result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    // Composing Completable Futures
    public static void composingCompletableFutures(){

        getUserEmailAsync()
                .thenCompose(CompletableFuturesDemo::getPlaylistAsync)
                .thenAccept(p -> System.out.println(p));
    }

    public static CompletableFuture<String> getUserEmailAsync(){
        return CompletableFuture.supplyAsync(() -> "email");
    }

    public static CompletableFuture<String> getPlaylistAsync(String email){
        return CompletableFuture.supplyAsync(() -> "playlist");
    }


    // Combining Completable Futures
    public static void combineCompletableFuture(){
        var first = CompletableFuture
                                                .supplyAsync(()-> "20USD")
                                                .thenApply(str -> {
                                                    var price = str.replace("USD", "");
                                                    return Integer.parseInt(price);
                                                });
        var second = CompletableFuture.supplyAsync(()-> 0.9);
        first.thenCombine(second, (price, exchageRate) -> price*exchageRate)
                .thenAccept(result -> System.out.println(result));
    }


    // Waiting for Many Tasks to Complete
    public static void waitForManyTasks(){
        var first = CompletableFuture.supplyAsync(() -> 1);
        var second = CompletableFuture.supplyAsync(() -> 2);
        var third = CompletableFuture.supplyAsync(() -> 3);
        var all = CompletableFuture.allOf(first, second, third);
        all.thenRun(()->{
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
    public static void handleTimeouts(){
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
