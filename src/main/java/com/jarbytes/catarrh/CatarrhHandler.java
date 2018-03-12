package com.jarbytes.catarrh;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Deque;
import java.util.Optional;

public class CatarrhHandler implements HttpHandler
{
    @Override
    public void handleRequest(final HttpServerExchange httpServerExchange)
    {
        switch (httpServerExchange.getRequestMethod().toString().toUpperCase()) {
            case "GET":
                handleGet(httpServerExchange);
                break;
            case "POST":
                handlePost(httpServerExchange);
                break;
            default:
                httpServerExchange.setStatusCode(400)
                        .getResponseSender()
                        .send("Sorry no clue.");
        }
    }

    private void handleGet(final HttpServerExchange httpServerExchange)
    {
        httpServerExchange.getResponseSender().send("Oh, hi Mark.");
    }

    private void handlePost(final HttpServerExchange httpServerExchange)
    {
        try {
            final String outputFileName = Optional.ofNullable(httpServerExchange.getQueryParameters()
                    .get("file"))
                    .map(Deque::getFirst)
                    .orElse("");
            if (outputFileName.isEmpty()) {
                httpServerExchange.setStatusCode(400)
                        .getResponseSender()
                        .send("No file specified.");
            } else {
                final File outputFile = File.createTempFile(outputFileName, "tmp");
                final FileOutputStream outputStream = new FileOutputStream(outputFile);
                httpServerExchange.getRequestChannel().transferTo(
                        0,
                        httpServerExchange.getRequestContentLength(),
                        outputStream.getChannel());
                outputStream.close();
                httpServerExchange.getResponseSender().send("I'm done!");
            }
        } catch (Exception e) {
            httpServerExchange.setStatusCode(500)
                    .getResponseSender()
                    .send(e.getMessage());
        }
    }
}
