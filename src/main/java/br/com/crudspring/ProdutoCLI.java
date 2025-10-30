package br.com.crudspring;

import br.com.crudspring.model.Produto;
import br.com.crudspring.service.ProdutoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ProdutoCLI implements CommandLineRunner {

    private final ProdutoService service;

    public ProdutoCLI(ProdutoService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== Sistema de Produtos (CLI) ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Atualizar Produto");
            System.out.println("3. Excluir Produto");
            System.out.println("4. Listar Produtos");
            System.out.println("5. Buscar por ID");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine(); // consumir quebra de linha

            switch (opcao) {

                // =========================== CADASTRAR ===========================
                case 1: {
                    System.out.println("Digite o nome do produto:");
                    String nome = sc.nextLine();
                    System.out.println("Digite o preço do produto:");
                    double preco = sc.nextDouble();
                    System.out.println("Digite o quantidade do produto:");
                    int quantidade = sc.nextInt();
                    sc.nextLine();

                    Produto produto = new Produto();
                    produto.setNome(nome);
                    produto.setPreco(preco);
                    produto.setQuantidade(quantidade);

                    service.salvar(produto);
                    System.out.println("✅ Produto cadastrado com sucesso!");
                    break;
                }

                // =========================== ATUALIZAR ===========================
                case 2: {
                    System.out.print("Digite o ID do produto a atualizar: ");
                    Long id = sc.nextLong();
                    sc.nextLine(); // consumir quebra de linha

                    try {
                        Produto produtoExistente = service.buscarPorId(id);

                        System.out.println("Produto atual: " + produtoExistente.getNome() + " - R$" + produtoExistente.getPreco());
                        System.out.print("Novo nome: ");
                        String novoNome = sc.nextLine();
                        System.out.print("Novo preço: ");
                        double novoPreco = sc.nextDouble();
                        System.out.println("Novo quantidade: ");
                        int novoQuantidade = sc.nextInt();

                        Produto novoProduto = new Produto(novoNome, novoPreco, novoQuantidade);
                        service.atualizar(id, novoProduto);

                        System.out.println("✅ Produto atualizado com sucesso!");
                    } catch (RuntimeException e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                    break;
                }

                // =========================== EXCLUIR ===========================
                case 3: {
                    System.out.print("Digite o ID do produto a excluir: ");
                    Long id = sc.nextLong();

                    try {
                        service.deletar(id);
                        System.out.println("🗑️ Produto excluído com sucesso!");
                    } catch (RuntimeException e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                    break;
                }

                // =========================== LISTAR ===========================
                case 4: {
                    var produtos = service.listar();

                    if (produtos.isEmpty()) {
                        System.out.println("📦 Nenhum produto cadastrado.");
                    } else {
                        System.out.println("\n--- Lista de Produtos ---");
                        produtos.forEach(produto ->
                                System.out.println("ID: " + produto.getId() +
                                        " | Nome: " + produto.getNome() +
                                        " | Preço: R$ " + produto.getPreco() +
                                        " | Quantidade: " + produto.getQuantidade())
                        );
                    }
                    break;
                }

                // =========================== BUSCAR POR ID ===========================
                case 5: {
                    System.out.print("Digite o ID do produto: ");
                    Long id = sc.nextLong();

                    try {
                        Produto produto = service.buscarPorId(id);
                        System.out.println("\n🔍 Produto encontrado:");
                        System.out.println("ID: " + produto.getId());
                        System.out.println("Nome: " + produto.getNome());
                        System.out.println("Preço: R$" + produto.getPreco());
                        System.out.println("Quantidade: " + produto.getQuantidade());
                    } catch (RuntimeException e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                    break;
                }

                // =========================== SAIR ===========================
                case 0: {
                    System.out.println("👋 Encerrando o sistema...");
                    break;
                }

                default: {
                    System.out.println("❌ Opção inválida! Tente novamente.");
                }
            }

        } while (opcao != 0);
    }
}
