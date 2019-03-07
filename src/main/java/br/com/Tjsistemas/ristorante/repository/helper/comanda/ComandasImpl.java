package br.com.Tjsistemas.ristorante.repository.helper.comanda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.com.Tjsistemas.ristorante.dto.ComandaMes;
import br.com.Tjsistemas.ristorante.dto.PreparoDTO;
import br.com.Tjsistemas.ristorante.model.Comanda;
import br.com.Tjsistemas.ristorante.model.ItemComanda;
import br.com.Tjsistemas.ristorante.model.MesaComanda;
import br.com.Tjsistemas.ristorante.model.StatusComanda;
import br.com.Tjsistemas.ristorante.repository.filter.ComandaFilter;
import br.com.Tjsistemas.ristorante.repository.paginacao.PaginacaoUtil;

public class ComandasImpl implements ComandasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings(value = { "static-access", "unchecked" })
	@Transactional(readOnly = true)
	public List<Comanda> ListaDeComandas(Long empresa) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Comanda.class);
		criteria.add(Restrictions.eq("empresa", empresa));
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);

		return (List<Comanda>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<ItemComanda> BuscarItensComanda(Comanda comanda) {

		Criteria criteria = manager.unwrap(Session.class).createCriteria(ItemComanda.class);
		criteria.add(Restrictions.eq("comanda", comanda));
		criteria.addOrder(Order.asc("id"));

		return (List<ItemComanda>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<MesaComanda> BuscarMesasComanda(Comanda comanda) {

		Criteria criteria = manager.unwrap(Session.class).createCriteria(MesaComanda.class);
		criteria.add(Restrictions.eq("comanda", comanda));
		criteria.addOrder(Order.desc("id"));

		return (List<MesaComanda>) criteria.list();
	}

	@Override
	public BigDecimal totalComandaAnual(Long empresa) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valorTotal) from Comanda where year(inicioAtendimento) =:ano and empresa = :emp", BigDecimal.class)
				   .setParameter("emp", empresa)		
				   .setParameter("ano", Year.now().getValue()).getSingleResult());

		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override
	public BigDecimal totalComandaMes(Long empresa) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				  manager.createQuery("select sum(valorTotal) from Comanda where month(inicioAtendimento) =:mes and empresa = :emp", BigDecimal.class)
				      .setParameter("emp", empresa)
				      .setParameter("mes", MonthDay.now().getMonthValue())
				      .getSingleResult());

		return optional.orElse(BigDecimal.ZERO);
	}
	
	@Override
	public BigDecimal valorTicketMedioAno(Long empresa) {
		Optional<BigDecimal> optional = Optional.ofNullable(
				manager.createQuery("select sum(valorTotal)/count(*) from Comanda where year(inicioAtendimento) =:ano and empresa = :emp", BigDecimal.class)
				.setParameter("emp", empresa)
				.setParameter("ano", Year.now().getValue())
                .getSingleResult());
		
		return optional.orElse(BigDecimal.ZERO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComandaMes> totalPorMes(Long empresa) {
		List<ComandaMes> comandaMes = manager.createNamedQuery("Comanda.totalPorMes")
				.setParameter("emp", empresa)
				.getResultList();
		
		LocalDate hoje = LocalDate.now();
		for (int i = 0; i <= 6; i++) {
			String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonthValue());

			boolean possueMes = comandaMes.stream().filter(v-> v.getMes().equals(mesIdeal)).findAny().isPresent();
			if (!possueMes) {
				comandaMes.add(i - 1, new ComandaMes(mesIdeal, 0));
			}
			
			hoje = hoje.minusMonths(1);
		}
		return comandaMes.stream().sorted((c1,c2) -> c2.getMes().compareTo(c1.getMes())).collect(Collectors.toList());
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Comanda> filtrar(ComandaFilter filtro, Pageable pageable) {
		Criteria criteria =  manager.unwrap(Session.class).createCriteria(Comanda.class);
		
        paginacaoUtil.preparar(criteria, pageable);		
		adicionarFiltro(filtro, criteria);
		
		criteria.addOrder(Order.desc("id"));
		
		Sort sort = pageable.getSort();
		if (sort != null) {
			Sort.Order order = sort.iterator().next();
			String property = order.getProperty();
			criteria.addOrder(order.isAscending()? Order.asc(property) : Order.desc(property));
		}
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(ComandaFilter filtro) {
		   Criteria criteria =  manager.unwrap(Session.class).createCriteria(Comanda.class);
		   adicionarFiltro(filtro, criteria);
		   criteria.setProjection(Projections.rowCount());
			return (Long) criteria.uniqueResult();
		}
	
	private void adicionarFiltro(ComandaFilter filtro, Criteria criteria) {
		//filtros vindo da tela 
		if (filtro.getEmpresa() != null) {
			criteria.add(Restrictions.eq("empresa", filtro.getEmpresa()));
			
			if (!StringUtils.isEmpty(filtro.getCodigo())) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}
			if (filtro.getDesconto() != null) {
				criteria.add(Restrictions.eq("desconto", filtro.getDesconto()));
			}
			if (!StringUtils.isEmpty(filtro.getStatus())) {
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			}
			if (filtro.getValorMinimo() != null) {
				criteria.add(Restrictions.ge("valorTotal", filtro.getValorMinimo()));
			}
			if (filtro.getValorMaximo() != null) {
				criteria.add(Restrictions.le("valorTotal", filtro.getValorMaximo()));
			}
			if (filtro.getMesa() != null) {
				criteria.add(Restrictions.eq("mesasComanda", filtro.getMesa()));
			}
			if (filtro.getCamareiro() != null) {
				criteria.add(Restrictions.eq("camareiro", filtro.getCamareiro()));
			}
			if (filtro.getCliente() != null) {
				criteria.add(Restrictions.eq("cliente", filtro.getCliente()));
			}
			if (filtro.getProduto() != null) {
				criteria.add(Restrictions.eq("itens.produto", filtro.getProduto()));
			}
		}
	}
	
	
//	@SuppressWarnings("unchecked")
//	@Transactional(readOnly = true)
//	@Override
//	private Page<Comanda> filtrarPreparos(Pageable pageable) {
//		Criteria criteria =  manager.unwrap(Session.class).createCriteria(ItemComanda.class);
//		criteria.createAlias("comanda", "c", JoinType.LEFT_OUTER_JOIN);
//		
//        paginacaoUtil.preparar(criteria, pageable);		
//        
//		criteria.add(Restrictions.gt("quantidadeAdicionada", 0));
//	    criteria.add(Restrictions.eq("c.status", StatusComanda.EMITIDA));
////	    criteria.	Projections.min("controleAtendimento")
//		criteria.setProjection(Projections.projectionList()
//				.add(Projections.groupProperty("comanda")));
//		
//		return new PageImpl<>(criteria.list(), pageable, preparoTotal());
//	}
	
	@Override
	public List<PreparoDTO> filtrarPreparo() {

		@SuppressWarnings("unchecked")
		List<PreparoDTO> comandasPreparo = manager.createNamedQuery("Comanda.preparoComandas")
				.getResultList();
		
	
		return comandasPreparo;
	}
	
	private Long preparoTotal() {
		   Criteria criteria =  manager.unwrap(Session.class).createCriteria(ItemComanda.class);
		   criteria.createAlias("comanda", "c", JoinType.LEFT_OUTER_JOIN);
		   criteria.add(Restrictions.eq("c.status", StatusComanda.EMITIDA));
		   
		   criteria.add(Restrictions.gt("quantidadeAdicionada", 0));
		   criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("comanda")));
		   criteria.setProjection(Projections.rowCount());
			return (Long) criteria.uniqueResult();
		}
	
//	private void preparoFiltro(ComandaFilter filtro, Criteria criteria) {
//			if (!StringUtils.isEmpty(filtro.getSetorPreparo())) {
//				criteria.add(Restrictions.eq("setorPreparo", filtro.getSetorPreparo()));
//			}
//	}
}