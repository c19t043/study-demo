package cn.cjf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBinding(value = {ReceiveService.class, Sink.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @StreamListener("myInput")
    public void receive(byte[] msg) {
        System.out.println("接收到的消息：  " + new String(msg));
    }

    @StreamListener(Sink.INPUT)
    public void receiveInput(byte[] msg) {
        System.out.println("receiveInput方法，接收到的消息：  " + new String(msg));
    }
}
