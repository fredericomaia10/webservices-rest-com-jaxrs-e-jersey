package br.com.alura.loja.resource;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import com.thoughtworks.xstream.XStream;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("carrinhos")
public class CarrinhoResource {
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Carrinho busca(@PathParam("id") long id) {
        return new CarrinhoDAO().busca(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response adiciona(Carrinho carrinho) {
        new CarrinhoDAO().adiciona(carrinho);
        URI uri = URI.create("/carrinhos/" + carrinho.getId());
        return Response.created(uri).build();
    }

    @Path("{id}/produtos/{produtoId}")
    @DELETE
    public Response removeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        carrinho.remove(produtoId);
        return Response.ok().build();
    }

    @Path("{id}/produtos/{produtoId}")
    @PUT
    public Response alteraProduto(@PathParam("id") long id,
                                  @PathParam("produtoId") long produtoId, String conteudo) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        Produto produto = (Produto) new XStream().fromXML(conteudo);
        carrinho.troca(produto);
        return Response.ok().build();
    }

    @Path("{id}/produtos/{produtoId}/quantidade")
    @PUT
    public Response alteraQuantidadeProduto(@PathParam("id") long id, @PathParam("produtoId") long produtoId, String conteudo) {
        Carrinho carrinho = new CarrinhoDAO().busca(id);
        Produto produto = (Produto) new XStream().fromXML(conteudo);
        carrinho.trocaQuantidade(produto);
        return Response.ok().build();
    }
}
