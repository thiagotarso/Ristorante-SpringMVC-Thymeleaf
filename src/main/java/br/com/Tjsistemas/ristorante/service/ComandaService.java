package br.com.Tjsistemas.ristorante.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.Tjsistemas.ristorante.model.Comanda;
import br.com.Tjsistemas.ristorante.model.StatusComanda;
import br.com.Tjsistemas.ristorante.repository.Comandas;

@Service
public class ComandaService {

	@Autowired 
	private Comandas comandas;
	
//	@Autowired
//	private ApplicationEventPublisher publisher;
	
	@Transactional
	public void salvar(Comanda comanda){
		
	     if (comanda.isNova()) {
			comanda.setInicioAtendimento(LocalDateTime.now());
		}else{
			Comanda comandaExistente = comandas.findOne(comanda.getId());
			comanda.setInicioAtendimento(comandaExistente.getInicioAtendimento());
		}
	     
	     if (comanda.getStatus().equals(StatusComanda.ENCERRADA) || comanda.getStatus().equals(StatusComanda.CANCELADA)) {
			comanda.setFimAtendimento(LocalDateTime.now());
		 }
    		
	    comandas.save(comanda);
	}
}