package com.selflearning.tw.multiThread;

import com.selflearning.tw.multiThread.executors.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class MultiThread {

    public static void main(String[] args) {
        System.out.println("Thread this program using: " + Thread.activeCount());
        System.out.println("Thread available: " + Runtime.getRuntime().availableProcessors());

//        ThreadDemo.show();
//        ExecutorsDemo.show();
//        CompletableFuturesDemo.show();
//        CompletableFuturesDemo.composingCompletableFutures();
//        CompletableFuturesDemo.combineCompletableFuture();
        CompletableFuturesDemo.waitForManyTasks();
//        CompletableFuturesDemo.handleTimeouts();


        var start = LocalTime.now();
        FlightService service = new FlightService();
//        service.getQuote("site1")
//                .thenAccept(System.out::println);
        var futures = service.getQuotes()
                .map(future -> future.thenAccept(System.out::println))
                .collect(Collectors.toList());

        CompletableFuture
                .allOf( // 必須等到所有都完成。 allOf 傳入的是array 因此要將list 轉為array
                    // new CompletableFuture[0] => 空的Completable array 告知我們要的結果是CompletableFuture array
                    futures.toArray(new CompletableFuture[0])
                ).thenRun(() -> {
                    var end = LocalTime.now();
                    Duration duration = Duration.between(start, end);
                    System.out.println("Retrieved all quotes in " + duration.toMillis() + " msec.");
                });


//        var mailService = new MailService();
////        mailService.send();
//        mailService.sendAsync();
        System.out.println("Hey gir");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
