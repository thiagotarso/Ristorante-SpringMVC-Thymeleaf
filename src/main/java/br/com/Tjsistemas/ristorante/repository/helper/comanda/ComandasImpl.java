package br.com.Tjsistemas.ristorante.repository.helper.comanda;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

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
		
        Criteria criteria= manager.unwrap(Session.class).createCriteria(ItemComanda.class);
        criteria.add(Restrictions.eq("comanda", comanda)); 
        criteria.addOrder(Order.desc("id"));
    		   
		return (List<ItemComanda>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<MesaComanda> BuscarMesasComanda(Comanda comanda) {	
		
        Criteria criteria= manager.unwrap(Session.class).createCriteria(MesaComanda.class);
        criteria.add(Restrictions.eq("comanda", comanda)); 
        criteria.addOrder(Order.desc("id"));
    		   
		return (List<MesaComanda>) criteria.list();
	}
}
