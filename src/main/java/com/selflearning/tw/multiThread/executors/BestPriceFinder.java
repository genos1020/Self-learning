package com.selflearning.tw.multiThread.executors;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class BestPriceFinder {

    public static void show(){
        long start = System.currentTimeMillis();
        var first = getSite1();
        var second = getSite2();
        var third = getSite3();

        var all = CompletableFuture.allOf(first,second,third);
        all.thenRun(()->{
            long end = System.currentTimeMillis();
            try {
                var firstResult = first.get();
                var secondResult = second.get();
                var thirdResult = third.get();
                System.out.printf("Quote{site='%s', price=%d}%n", "site1",firstResult);
                System.out.printf("Quote{site='%s', price=%d}%n", "site2",secondResult);
                System.out.printf("Quote{site='%s', price=%d}%n", "site3",thirdResult);
                System.out.printf("Retrieved all quotes in %d msec.%n", end-start);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

    }

    public static CompletableFuture<Integer> getSite1() {
        System.out.println("Getting a quote from site 1.");
        try{
            Thread.sleep(2000);
        }catch(Exception e){
            e.printStackTrace();
        }
        return CompletableFuture.supplyAsync(() -> 100);
    }

    public static CompletableFuture<Integer> getSite2() {
        System.out.println("Getting a quote from site 2.");
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            e.printStackTrace();
        }
        return CompletableFuture.supplyAsync(() -> 105);
    }

    public static CompletableFuture<Integer> getSite3() {
        System.out.println("Getting a quote from site 3.");
        try{
            Thread.sleep(3000);
        }catch(Exception e){
            e.printStackTrace();
        }
        return CompletableFuture.supplyAsync(() -> 108);
    }
}
