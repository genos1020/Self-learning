package com.selflearning.tw.multiThread.concurrency;

public class DownloadFileThread implements Runnable {
    @Override
    public void run() {
        System.out.println("Downloading a file: " + Thread.currentThread().getName());
    }
}
