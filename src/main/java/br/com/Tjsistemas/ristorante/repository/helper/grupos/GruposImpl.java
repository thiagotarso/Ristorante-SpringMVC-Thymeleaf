package br.com.Tjsistemas.ristorante.repository.helper.grupos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GruposImpl implements GruposQueries{

	@PersistenceContext
	private EntityManager manager;
	
	public Long codigoGrupo(Long codigoGrupo) {
	  Long valor = manager.createQuery("select max(g.codigo) from Grupo g where g.empresa = :emp", Long.class)
			  .setParameter("emp", codigoGrupo)
			  .getSingleResult();
		
	  return valor != null ? valor + 1 : 1L;
	}
}
