package br.com.crudspring.controller;

import br.com.crudspring.model.Produto;
import br.com.crudspring.service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    ///Essa camada é responsável por:
    ///
    ///Receber requisições HTTP (GET, POST, PUT, DELETE)
    ///
    ///Chamar o Service para processar a lógica
    ///
    ///Retornar respostas para o cliente (Postman, navegador, front-end, etc)

    private ProdutoService service;


    /// Injeção de dependência (Spring injeta o service automaticamente)
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    //CREATE -> POST /produtos
    @PostMapping
    public Produto salvar(@RequestBody Produto produto){
        return service.salvar(produto);
    }

    //READ -> GET /produtos
    @GetMapping
    public List<Produto> listar (){
        return service.listar();
    }

    //READ (POR ID) -> GET /produtos
    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    //ATUALIZAR -> PUT /produtos
    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto produto){
        return service.atualizar(id, produto);
    }

    //DELETE -> PUT /produtos
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);


    }

}
