package br.com.Tjsistemas.ristorante.repository.helper.produtos;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.Tjsistemas.ristorante.model.Produto;

public class ProdutosImpl implements ProdutosQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	@SuppressWarnings("unchecked")
	public List<Produto> porDescricao(String descricao) {
		String jpql = "Select * From Produto "
				+ "where lower(descricao) like lower(:descricao)";
		 
		List<Produto> produtosFiltos = manager.createNativeQuery(jpql, Produto.class)
				 .setParameter("descricao", descricao + "%")
				 .getResultList();
		return produtosFiltos;
	}
}