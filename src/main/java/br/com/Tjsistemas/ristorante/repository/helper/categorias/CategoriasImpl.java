package br.com.Tjsistemas.ristorante.repository.helper.categorias;

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

import br.com.Tjsistemas.ristorante.model.Categoria;
import br.com.Tjsistemas.ristorante.repository.filter.CategoriaFilter;
import br.com.Tjsistemas.ristorante.repository.paginacao.PaginacaoUtil;

public class CategoriasImpl implements CategoriasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@Override
	public Long codigoCategorias(Long codigoUsuario) {
	  Long valor = manager.createQuery("select max(c.codigo) from Categoria c where c.empresa= :emp", Long.class)
			  .setParameter("emp", codigoUsuario)
			  .getSingleResult();
	  
		return valor != null ? valor + 1 : 1L;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Categoria> filtrar(CategoriaFilter filtro, Pageable pageable) {
		Criteria criteria =  manager.unwrap(Session.class).createCriteria(Categoria.class);
		
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
	
	private Long total(CategoriaFilter filtro) {
		   Criteria criteria =  manager.unwrap(Session.class).createCriteria(Categoria.class);
		   adicionarFiltro(filtro, criteria);
		   criteria.setProjection(Projections.rowCount());
			return (Long) criteria.uniqueResult();
		}
	
	
	private void adicionarFiltro(CategoriaFilter filtro, Criteria criteria) {
		//filtros vindo da tela 
		if (filtro != null) {
			criteria.add(Restrictions.eq("empresa", filtro.getEmpresa()));
			
			if (!StringUtils.isEmpty(filtro.getCodigo())) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}
			if (!StringUtils.isEmpty(filtro.getDescricao())) {
				criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(), MatchMode.ANYWHERE)); //  MatchMode.ANYWHER funciona como %%
			}
		}
	}
}
