package br.com.Tjsistemas.ristorante.session;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import br.com.Tjsistemas.ristorante.model.Produto;
import br.com.Tjsistemas.ristorante.session.TabelaItensComanda;

public class tabelaItensComandaTeste {

	public TabelaItensComanda tabelaItensComanda;
	
	@Before
	public void setUp(){
		this.tabelaItensComanda =  new TabelaItensComanda("1");
	}
	
	@Test
	public void deveCalcularValorTotalSemItens() throws Exception{
		assertEquals(BigDecimal.ZERO,tabelaItensComanda.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorComUmTotal() throws Exception {
		Produto produto = new Produto();
		BigDecimal valor = new BigDecimal("8.90");
		produto.setValor(valor);
		
		tabelaItensComanda.adicionaItem(produto, 1,"");
		assertEquals(valor, tabelaItensComanda.getValorTotal());
	}
	
	@Test
	public void deveCalcularValorTotalComVariosItens() throws Exception {
		Produto produto1 = new Produto();
		produto1.setId(1L);
		BigDecimal valor1 = new BigDecimal("8.90");
		produto1.setValor(valor1);
		
		Produto produto2 = new Produto();
		produto2.setId(2L);
		BigDecimal valor2 = new BigDecimal("4.99");
		produto2.setValor(valor2);
		
		tabelaItensComanda.adicionaItem(produto1, 1,"");
		tabelaItensComanda.adicionaItem(produto2, 2,"");
		
		assertEquals(new BigDecimal("18.88"), tabelaItensComanda.getValorTotal());
	}
	
	@Test
	public void deveManterTamanhoDaListaParaMesmosProdutos() throws Exception {
		Produto produto1 = new Produto();
		produto1.setId(1L);
		BigDecimal valor1 = new BigDecimal("10.00");
		produto1.setValor(valor1);
		
		tabelaItensComanda.adicionaItem(produto1, 1,"");
		tabelaItensComanda.adicionaItem(produto1, 2,"");
		
		assertEquals(1, tabelaItensComanda.total());
		assertEquals(new BigDecimal("30.00"), tabelaItensComanda.getValorTotal());
	}
	
	@Test
	public void deveAlterarQuantidadeProdutos() throws Exception {
		Produto produto1 = new Produto();
		produto1.setId(1L);
		BigDecimal valor1 = new BigDecimal("10.00");
		produto1.setValor(valor1);
		
		tabelaItensComanda.adicionaItem(produto1, 1,"");
		tabelaItensComanda.alterarQuantidadeItens(produto1, 3);
		
		assertEquals(new BigDecimal("30.00"), tabelaItensComanda.getValorTotal());
	}
	
	
	@Test
	public void deveRemoverIten() throws Exception {
		Produto produto1 = new Produto();
		produto1.setId(1L);
		produto1.setValor(new BigDecimal("8.00"));
		
		Produto produto2 = new Produto();
		produto2.setId(2L);
		produto2.setValor(new BigDecimal("5.00"));
		
		Produto produto3 = new Produto();
		produto3.setId(3L);
		produto3.setValor(new BigDecimal("7.00"));
		
		tabelaItensComanda.adicionaItem(produto1, 1,"");
		tabelaItensComanda.adicionaItem(produto2, 1,"");
		tabelaItensComanda.adicionaItem(produto3, 2,"");
		
		tabelaItensComanda.removerItemComanda(produto3);
		
		assertEquals(2, tabelaItensComanda.total());
		assertEquals(new BigDecimal("13.00"), tabelaItensComanda.getValorTotal());
	}
	
}