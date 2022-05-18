package com.company.client;

import java.io.IOException;
import java.net.*;



public class Client {
    private final DatagramSocket socket = new DatagramSocket();
    private final InetAddress address = InetAddress.getByName("localhost");

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
        CommandChecker checker = new CommandChecker(address, socket);
        checker.check();
    }
}
