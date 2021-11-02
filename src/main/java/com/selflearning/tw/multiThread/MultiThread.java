package com.selflearning.tw.multiThread;

import com.selflearning.tw.multiThread.executors.CompletableFuturesDemo;
import com.selflearning.tw.multiThread.executors.ExecutorsDemo;
import com.selflearning.tw.multiThread.executors.MailService;

public class MultiThread {

    public static void main(String[] args) {
//        System.out.println(Thread.activeCount());
//        System.out.println(Runtime.getRuntime().availableProcessors());

//        ThreadDemo.show();
//        ExecutorsDemo.show();
//        CompletableFuturesDemo.show();

        var mailService = new MailService();
//        mailService.send();
        mailService.sendAsync();
        System.out.println("Hey gir");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
