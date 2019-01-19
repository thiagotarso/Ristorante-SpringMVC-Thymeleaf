package br.com.Tjsistemas.ristorante.repository.helper.produtos;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.Tjsistemas.ristorante.model.Produto;
import br.com.Tjsistemas.ristorante.repository.filter.ProdutoFilter;
import br.com.Tjsistemas.ristorante.repository.paginacao.PaginacaoUtil;

public class ProdutosImpl implements ProdutosQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

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
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Produto> filtrar(ProdutoFilter filtro, Pageable pageable) {
		Criteria criteria =  manager.unwrap(Session.class).createCriteria(Produto.class);
		
        paginacaoUtil.preparar(criteria, pageable);		
		adicionarFiltro(filtro, criteria);
		
		Sort sort = pageable.getSort();
		if (sort != null) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending()? Order.asc(property) : Order.desc(property));
		}
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(ProdutoFilter filtro) {
		   Criteria criteria =  manager.unwrap(Session.class).createCriteria(Produto.class);
		   adicionarFiltro(filtro, criteria);
		   criteria.setProjection(Projections.rowCount());
			return (Long) criteria.uniqueResult();
		}
	
	private void adicionarFiltro(ProdutoFilter filtro, Criteria criteria) {
		//filtros vindo da tela 
		if (filtro.getEmpresa() != null) {
			criteria.add(Restrictions.eq("empresa", filtro.getEmpresa()));
			
			if (filtro.getCodigo() != null) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}
			if (!StringUtils.isEmpty(filtro.getDescricao())) {
				criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE)); //  MatchMode.ANYWHER funciona como %%
			}
			if (!StringUtils.isEmpty(filtro.getEAN())) {
				criteria.add(Restrictions.ilike("EAN", filtro.getEAN(), MatchMode.ANYWHERE));
			}
			if (filtro.getValorMinimo() != null) {
				criteria.add(Restrictions.ge("valor", filtro.getValorMinimo()));
			}
			if (filtro.getValorMaximo() != null) {
				criteria.add(Restrictions.le("valor", filtro.getValorMaximo()));
			}
			if (filtro.getCliente() != null) {
				criteria.add(Restrictions.eq("cliente", filtro.getCliente()));
			}
			if (filtro.getCategoria() != null) {
				criteria.add(Restrictions.eq("categoria", filtro.getCategoria()));
			}
		}
	}
}