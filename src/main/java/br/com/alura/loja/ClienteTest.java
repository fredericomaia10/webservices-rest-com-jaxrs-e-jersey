package br.com.alura.loja;

import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClienteTest {

    private HttpServer httpServer;
    private Client client;

    @Before
    public void inicializaServidor() {
        httpServer = Servidor.inicializaServidor();
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(new LoggingFilter());
        client = ClientBuilder.newClient(clientConfig);
    }

    @After
    public void paraServidor() {
        httpServer.stop();
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
        WebTarget target = client.target("http://localhost:8080");
        Carrinho carrinho = target.path("/carrinhos/1").request().get(Carrinho.class);
        Assert.assertEquals(carrinho.getRua(), "Rua Vergueiro 3185, 8 andar");
    }

    @Test
    public void testaQueInserirUmCarrinhoRetornaSucesso() {
        WebTarget target = client.target("http://localhost:8080");
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        Entity<Carrinho> entity = Entity.entity(carrinho, MediaType.APPLICATION_XML);
        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());
    }

    @Test
    public void testaQueBuscarUmProjetoTrazOEsperado() {
        WebTarget target = client.target("http://localhost:8080");
        Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
        Assert.assertTrue(projeto.getNome().equals("Minha loja"));
    }
}