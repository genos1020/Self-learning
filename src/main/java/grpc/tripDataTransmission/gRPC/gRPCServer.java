package grpc.tripDataTransmission.gRPC;
import grpc.tripDataTransmission.TripDataReply;
import grpc.tripDataTransmission.TripDataRequest;
import grpc.tripDataTransmission.TripTransactionDataServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class gRPCServer {

    private int port = 6565;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new MistraHelloWorldImpl())
                .build()
                .start();

        System.out.println("------------------- 服务端服务已开启，等待客户端访问 -------------------");


        //說明(1) jvm中增加一个关闭的钩子，当jvm关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子，
        //当系统执行完这些钩子后，jvm才会关闭。
        //所以这些钩子可以在jvm关闭的时候进行内存清理、对象销毁等操作。

        //說明(2) 根据 Java API, 所谓 shutdown hook 就是已经初始化但尚未开始执行的线程对象。
        //在Runtime 注册后，如果JVM要停止前，这些 shutdown hook 便开始执行。
        //也就是在你的程序结束前，执行一些清理工作，尤其是没有用户界面的程序。
        //这些 shutdown hook 都是些线程对象，因此，你的清理工作要写在 run() 里。
        //根据 Java API，你的清理工作不能太重了，要尽快结束。但仍然可以对数据库进行操作。问题是这个度该如何把握。
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {

                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                gRPCServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final gRPCServer server = new gRPCServer();
        //启动服务
        server.start();
        //服务一直在线，不关闭
        server.blockUntilShutdown();
    }
}


// 定义一个实现服务接口的类
class MistraHelloWorldImpl extends TripTransactionDataServiceGrpc.TripTransactionDataServiceImplBase{

    @Override
    public void sendTripData(TripDataRequest request, StreamObserver<TripDataReply> responseObserver) {

        System.out.println("Begin sayHello");
        System.out.println(request);
        // 具体其他丰富的业务实现代码
        System.err.println("server:" + request.getTripId());
        TripDataReply reply = TripDataReply.newBuilder()
                .setStatus(true)
                .setMessage(("响应信息: " + request.getTripId()))
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();

        System.out.println("End sayHello");
    }
}

