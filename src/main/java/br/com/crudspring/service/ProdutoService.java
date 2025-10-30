package br.com.crudspring.service;

import br.com.crudspring.model.Produto;
import br.com.crudspring.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
private ProdutoRepository repository;

    // Injeção de dependência via construtor (recomendado)
    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    //CREATE
    public Produto salvar(Produto produto){

        //validação simples
        if (produto.getPreco() <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior do que zero!");
        }
        return repository.save(produto);
    }

    //LISTAR
    public List<Produto> listar(){
        return repository.findAll();
    }

    //READ
    public Produto buscarPorId(Long id){
        return repository.findById(id).orElseThrow(()-> new RuntimeException("Produto não encontrado"));
    }

    //ATUALIZAR
    public Produto atualizar(Long id, Produto produto){
        Produto produtoAtualizado = buscarPorId(id);
        produtoAtualizado.setNome(produto.getNome());
        produtoAtualizado.setPreco(produto.getPreco());
        produtoAtualizado.setQuantidade(produto.getQuantidade());
        return repository.save(produtoAtualizado);
    }

    //DELETAR
    public void deletar(Long id){
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        repository.deleteById(id);
    }


}
