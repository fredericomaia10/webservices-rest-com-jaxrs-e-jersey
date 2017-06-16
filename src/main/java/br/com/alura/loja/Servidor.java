package br.com.alura.loja;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Servidor {

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = inicializaServidor();
        System.out.println("Server running!");
        System.in.read();
        httpServer.stop();
    }

    static HttpServer inicializaServidor() {
        URI uri = URI.create("http://localhost:8080");
        ResourceConfig config = new ResourceConfig().packages("br.com.alura.loja");
        return GrizzlyHttpServerFactory.createHttpServer(uri, config);
    }
}
