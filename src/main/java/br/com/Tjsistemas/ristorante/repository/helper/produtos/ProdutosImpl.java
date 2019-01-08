package br.com.Tjsistemas.ristorante.repository.helper.produtos;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.Tjsistemas.ristorante.model.Produto;

public class ProdutosImpl implements ProdutosQueries {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Long codigoProduto(Long codigoEmpresaProduto) {
       Long valor = manager.createQuery("select max(p.codigo) from Produto p where p.empresa = :emp", Long.class)
    		   .setParameter("emp", codigoEmpresaProduto)
    		   .getSingleResult();
		
       return valor != null ? valor + 1 : 1L;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Produto> porDescricao(String descricao, Long empresa) {
		String jpql = "Select * From Produto "
				+ "where lower(descricao) like lower(:descricao) and empresa=(:emp)";
		 
		List<Produto> produtosFiltos = manager.createNativeQuery(jpql, Produto.class)
				 .setParameter("descricao", descricao + "%")
				 .setParameter("emp", empresa)
				 .getResultList();
		return produtosFiltos;
	}
}