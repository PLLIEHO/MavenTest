package com.company.client;


import com.company.common.Serializer;

import java.io.IOException;
import java.net.*;



public class Client {
    private final DatagramSocket socket = new DatagramSocket();
    private InetAddress address;

    public Client() throws UnknownHostException, SocketException {
    }

    public static void main(String[] args) throws IOException {
        try{
            Client client = new Client();
            client.start();
        } catch (SocketException | UnknownHostException e) {
            System.out.println("Невозможно подключиться к серверу.");
            System.exit(1);
        }
    }
    public void start() throws IOException {
        System.out.println("Введите IP адрес сервера...");
        address = InetAddress.getByName(InputCore.input());
        System.out.println("Введите порт...");
        Serializer.PORT = Integer.parseInt(InputCore.input());

        CommandChecker checker = new CommandChecker(address, socket);
        checker.check();
    }
}
