package com.company.server;

import com.company.client.Sender;
import com.company.common.Answer;
import com.company.common.Request;
import com.company.common.Serializer;
import com.company.server.core.Collection;
import com.company.server.core.commands.History;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

public class Processor {
    public SocketAddress address;
    private String filename;
    private Queue<InetSocketAddress> confirmation = new LinkedList<>();
    private String approvalString;

    public Processor(String filename){
        this.filename = filename;
    }

    public void begin(DatagramChannel channel, Collection collection) {
        try {
                ByteBuffer clientRequest = ByteBuffer.allocate(Serializer.SIZE);
                Selector selector = Selector.open();
                SelectionKey key = channel.register(selector, SelectionKey.OP_READ);

                getPacket(channel, clientRequest, selector, key, collection);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void getPacket(DatagramChannel channel, ByteBuffer clientRequest, Selector selector, SelectionKey key, Collection collection) throws IOException {
        while (true) {
            int readyChannels = selector.selectNow();
            if (readyChannels == 0) continue;
            else {
                break;
            }
        }
        History history = new History();
        while (true) {
                address = channel.receive(clientRequest);
                if(address!=null) {
                    Serializer serializer = new Serializer();
                    InetSocketAddress clientAddress = (InetSocketAddress) address;
                    clientRequest.flip();
                    Server.LOG.info("Получен пакет от клиента {}", address.toString());

                    Request request = (Request) serializer.deserialize(clientRequest.array());
                    if(!checkApproval(request)) {
                        confirmation.add(clientAddress);
                        CommandCore core = new CommandCore(request, collection, channel);
                        AnswerSender sender = new AnswerSender(address);
                        sender.send(channel, new Serializer().serialize(new Answer(core.commandSearch(history, filename))));
                        clientRequest.clear();
                    }
                    else {
                        confirmation.remove();
                        clientRequest.clear();
                    }
                }
        }
    }
    public boolean checkApproval(Request request) throws NullPointerException{
            if (confirmation.size() > 0 ) {
                if (request.getArgument().getArgB() != null && Objects.equals(request.getArgument().getArgB(), confirmation.peek().getAddress().getHostAddress())) {
                    Server.LOG.info("Клиент {} подтвердил получение ответа.", request.getArgument().getArgB());
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
    }
}

