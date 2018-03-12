package com.jarbytes.catarrh;

import io.undertow.Undertow;

public class CatarrhService
{
    public static void main(final String[] args)
    {
        if (args.length != 2) {
            System.err.println("Usage: service [port] [host]");
        } else {
            final int port = Integer.parseInt(args[0]);
            final String host = args[1];
            final Undertow server = Undertow.builder()
                    .addHttpListener(port, host)
                    .setHandler(new CatarrhHandler())
                    .build();
            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
            server.start();
        }
    }
}
