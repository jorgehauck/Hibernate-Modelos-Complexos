package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {
		cadastrarProduto();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		
		Produto p = produtoDao.buscarPorId(1l);
		System.out.println(p.getPreco());
		
		List<Produto> todos = produtoDao.buscarPorNomeDaCategoria("CELULARES");
		todos.forEach(p2 -> System.out.println(p2.getNome()));
		
		BigDecimal precoProduto = produtoDao.buscarPrecoProdutoComNome("Xiaomi Redmi");
		System.out.println(precoProduto);
		
		List<Produto> listaProdutos = produtoDao.buscarTodos();
		
		for (Produto produto : listaProdutos) {
			System.out.println(produto.getNome());
		}
	}

	private static void cadastrarProduto() {
		Categoria categoria = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEO GAMES");
		
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), categoria);
		Produto videogame = new Produto("Playstation 5", "Sony", new BigDecimal("4500.00"), videogames);

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);

		em.getTransaction().begin();
		categoriaDao.cadastrar(categoria);
		categoriaDao.cadastrar(videogames);
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		em.getTransaction().commit();
		em.close();
	}

}
