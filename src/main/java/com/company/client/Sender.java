package com.company.client;

import com.company.common.Answer;
import com.company.common.Pack;
import com.company.common.Request;
import com.company.common.Serializer;

import java.io.IOException;
import java.net.*;


public class Sender {

    public void send(byte[] byteOutput, InetAddress address, DatagramSocket socket) {

        DatagramPacket toServer = new DatagramPacket(byteOutput, byteOutput.length, address, Serializer.PORT);
        DatagramPacket packetIn = new DatagramPacket(new byte[Serializer.SIZE], Serializer.SIZE);
        try {

                //socket.connect(address, Serializer.PORT);
                //пробуем достучаться до сервера 10 раз, отправляя пакеты каждую секунду
                System.out.println("Пакет отправлен, ждите.");
                for(int i = 0; i<11; i++){
                    try {
                        socket.setSoTimeout(0);
                        socket.send(toServer);
                        socket.setSoTimeout(1000);
                        socket.receive(packetIn);

                        System.out.println("Пакет доставлен.");
                        Answer answer = (Answer) new Serializer().deserialize(packetIn.getData());

                        Serializer serializer = new Serializer();
                        byte[] byteApproval = serializer.serialize(new Request(null, new Pack(null, approvalCreator(toServer)))).toByteArray();
                        socket.send(new DatagramPacket(byteApproval, byteApproval.length, address, Serializer.PORT));
                        System.out.println("Подтверждение отправлено.");
                        System.out.println(answer.getStr());
                        break;
                    } catch (SocketTimeoutException e){
                        if(i!=10){
                        continue;}
                        else{
                            socket.close();
                            System.out.println("Сервер недоступен. Попробуйте ввести другой порт и IP.");
                            Client client = new Client();
                            client.start();
                        }
                    }
                }

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private String approvalCreator(DatagramPacket toServer){
        return toServer.getAddress().getHostAddress();
    }
}
