package com.company.server;


import com.company.common.Serializer;
import com.company.server.core.Collection;
import com.company.server.core.OutputCore;
import com.company.server.core.SearchXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;

import java.io.IOException;
import java.net.*;
import java.nio.channels.DatagramChannel;

public class Server {
        public final static Logger LOG = LoggerFactory.getLogger(Processor.class);
    public static void main(String[] args) throws IOException {
            LOG.info("Сервер запущен и работает");
            DatagramChannelBuilder builder = new DatagramChannelBuilder();
            InetSocketAddress address = new InetSocketAddress(Serializer.PORT);
            DatagramChannel channel = builder.build();
            channel.bind(address);
            Server.LOG.info("Датаграмм-канал открыт. Порт: {}", Serializer.PORT);
            channel.configureBlocking(false);
            SearchXML searchXML = new SearchXML();
            Processor processor = new Processor(args[0]);
            Collection collection = searchXML.searchFile(args[0]);
            //setupSignalHandler(collection, args[0]);
            processor.begin(channel, collection);
    }
        private static void setupSignalHandler(Collection collection, String filepath){
                Signal.handle(new Signal("TSTP"), signal -> {
                        try{
                                LOG.info("Сохранение...");
                                OutputCore outputCore = new OutputCore();
                                outputCore.save(filepath, collection);
                        } catch (IOException e) {
                                e.printStackTrace();
                                throw new RuntimeException(e);
                        }
                });
        }
}

