package br.com.Tjsistemas.ristorante.repository.helper.mesa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.model.SituacaoMesa;

public class MesasImpl implements MesasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@SuppressWarnings({ "static-access", "unchecked" })
	@Transactional(readOnly = true)
	public List<Mesa> mesasLivres() {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Mesa.class);

		criteria.add(Restrictions.eq("situacaoMesa", SituacaoMesa.LIVRE));
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		
		return (List<Mesa>) criteria.list();
	}


}
