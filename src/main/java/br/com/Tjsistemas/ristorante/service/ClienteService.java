package br.com.Tjsistemas.ristorante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.Tjsistemas.ristorante.model.Cliente;
import br.com.Tjsistemas.ristorante.repository.Clientes;

@Service
public class ClienteService {

	@Autowired 
	public Clientes clientes;
	
	@Transactional
	public Cliente salvar(Cliente cliente){
		return clientes.save(cliente);
	}
}
