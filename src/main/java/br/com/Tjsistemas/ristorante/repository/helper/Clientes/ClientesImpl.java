package br.com.Tjsistemas.ristorante.repository.helper.Clientes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ClientesImpl implements ClientesQueries{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Long codigoCliente(Long codigoEmpresa) {
		Long valor = manager.createQuery("select max(c.codigo) from Cliente c where c.empresa = :emp", Long.class)
				.setParameter("emp", codigoEmpresa)
				.getSingleResult();
		
		return  valor + 1L;
	}
}
