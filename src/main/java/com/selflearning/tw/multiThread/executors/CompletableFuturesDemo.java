package com.selflearning.tw.multiThread.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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
}
