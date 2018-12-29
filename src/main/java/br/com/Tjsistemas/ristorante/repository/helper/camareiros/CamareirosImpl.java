package br.com.Tjsistemas.ristorante.repository.helper.camareiros;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CamareirosImpl implements CamareirosQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Long codigoCamareiros(Long codigoCamareiros) {
         Long valor = manager.createQuery("select max(c.codigo) from Camareiro c where c.empresa = :emp", Long.class)
        		 .setParameter("emp", codigoCamareiros)
        		 .getSingleResult(); 
		
		return valor + 1L;
	}

}
