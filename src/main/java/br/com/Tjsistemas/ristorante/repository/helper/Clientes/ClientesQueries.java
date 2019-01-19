package br.com.Tjsistemas.ristorante.repository.helper.Clientes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.Tjsistemas.ristorante.model.Cliente;
import br.com.Tjsistemas.ristorante.repository.filter.ClienteFilter;

public interface ClientesQueries  {

	public Long codigoCliente(Long CodigoEmpresa);
	
	public Page<Cliente> filtrar(ClienteFilter filtro,Pageable pageable);
}
