package br.com.Tjsistemas.ristorante.repository.helper.mesa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
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

import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.model.SituacaoMesa;
import br.com.Tjsistemas.ristorante.repository.filter.MesaFilter;
import br.com.Tjsistemas.ristorante.repository.paginacao.PaginacaoUtil;

public class MesasImpl implements MesasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	
	public Long codigoMesa(Long codigoMesa) {
		Long valor = manager.createQuery("select max(m.codigo) from Mesa m where m.empresa = :emp", Long.class)
				.setParameter("emp", codigoMesa)
				.getSingleResult();
	  				
		return valor != null ? valor + 1 : 1L;
	}
	
	
	@SuppressWarnings({ "static-access", "unchecked" })
	@Transactional(readOnly = true)
	public List<Mesa> mesasLivres() {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Mesa.class);

		criteria.add(Restrictions.eq("situacaoMesa", SituacaoMesa.LIVRE));
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		
		return (List<Mesa>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Mesa> filtrar(MesaFilter filtro, Pageable pageable) {
		Criteria criteria =  manager.unwrap(Session.class).createCriteria(Mesa.class);
		
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
	
	private Long total(MesaFilter filtro) {
		   Criteria criteria =  manager.unwrap(Session.class).createCriteria(Mesa.class);
		   adicionarFiltro(filtro, criteria);
		   criteria.setProjection(Projections.rowCount());
			return (Long) criteria.uniqueResult();
		}
	
	private void adicionarFiltro(MesaFilter filtro, Criteria criteria) {
		//filtros vindo da tela 
		if (filtro != null) {
			criteria.add(Restrictions.eq("empresa", filtro.getEmpresa()));
			
			if (!StringUtils.isEmpty(filtro.getCodigo())) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}
			if (!StringUtils.isEmpty(filtro.situacaoMesa)) {
				criteria.add(Restrictions.eq("situacaoMesa", filtro.situacaoMesa)); //  MatchMode.ANYWHER funciona como %%
			}
		}
	}
}
