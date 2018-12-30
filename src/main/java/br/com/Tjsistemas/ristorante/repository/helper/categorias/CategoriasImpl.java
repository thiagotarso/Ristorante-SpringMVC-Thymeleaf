package br.com.Tjsistemas.ristorante.repository.helper.categorias;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CategoriasImpl implements CategoriasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public Long codigoCategorias(Long codigoUsuario) {
	  Long valor = manager.createQuery("select max(c.codigo) from Categoria c where c.empresa= :emp", Long.class)
			  .setParameter("emp", codigoUsuario)
			  .getSingleResult();
	  
		return valor + 1;
	}
}
