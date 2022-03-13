package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class CadastroDePedido {

	public static void main(String[] args) {
		cadastrarProduto();
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		Produto produto = produtoDao.buscarPorId(1l);
		ClienteDao clienteDao = new ClienteDao(em);
		Cliente cliente = clienteDao.buscarPorId(1l);
		
		
		em.getTransaction().begin();
		
		Pedido pedido = new Pedido(cliente);
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		
		PedidoDao pedidoDao = new PedidoDao(em);
		pedidoDao.cadastrar(pedido);
		em.getTransaction().commit();
		
		
		BigDecimal total = pedidoDao.valorTotalVendido();
		System.out.println("Valor total de vendas: "+ total);
		
		List<RelatorioDeVendasVo> relatorio = pedidoDao.relatoriodeVendas();
		relatorio.forEach(System.out::println);
	}
	
	
	private static void cadastrarProduto() {
		Categoria categoria = new Categoria("CELULARES");
		
		Cliente cliente = new Cliente("José Jorge", "47160538814");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), categoria);
		

		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		
		
		em.getTransaction().begin();
		categoriaDao.cadastrar(categoria);
		produtoDao.cadastrar(celular);
		clienteDao.cadastrar(cliente);
		em.getTransaction().commit();
		em.close();
	}

}
