package br.com.Tjsistemas.ristorante.repository.helper.comanda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import br.com.Tjsistemas.ristorante.dto.ComandaMes;
import br.com.Tjsistemas.ristorante.model.Comanda;
import br.com.Tjsistemas.ristorante.model.ItemComanda;
import br.com.Tjsistemas.ristorante.model.MesaComanda;

public class ComandasImpl implements ComandasQueries {

	@PersistenceContext
	private EntityManager manager;

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
		criteria.addOrder(Order.desc("id"));

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
		return comandaMes;
	}
}