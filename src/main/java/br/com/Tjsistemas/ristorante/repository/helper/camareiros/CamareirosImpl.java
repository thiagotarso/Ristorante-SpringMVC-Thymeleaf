package br.com.Tjsistemas.ristorante.repository.helper.camareiros;

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

import br.com.Tjsistemas.ristorante.model.Camareiro;
import br.com.Tjsistemas.ristorante.repository.filter.CamareiroFilter;
import br.com.Tjsistemas.ristorante.repository.paginacao.PaginacaoUtil;

public class CamareirosImpl implements CamareirosQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	public Long codigoCamareiros(Long codigoCamareiros) {
         Long valor = manager.createQuery("select max(c.codigo) from Camareiro c where c.empresa = :emp", Long.class)
        		 .setParameter("emp", codigoCamareiros)
        		 .getSingleResult(); 
         return valor != null ? valor + 1 : 1L;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Camareiro> filtrar(CamareiroFilter filtro, Pageable pageable) {
		Criteria criteria =  manager.unwrap(Session.class).createCriteria(Camareiro.class);
		
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
	
	private Long total(CamareiroFilter filtro) {
		   Criteria criteria =  manager.unwrap(Session.class).createCriteria(Camareiro.class);
		   adicionarFiltro(filtro, criteria);
		   criteria.setProjection(Projections.rowCount());
			return (Long) criteria.uniqueResult();
		}
	
	private void adicionarFiltro(CamareiroFilter filtro, Criteria criteria) {
		//filtros vindo da tela 
		if (filtro != null) {
			criteria.add(Restrictions.eq("empresa", filtro.getEmpresa()));
			
			if (!StringUtils.isEmpty(filtro.getCodigo())) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE)); //  MatchMode.ANYWHER funciona como %%
			}
		}
	}
}