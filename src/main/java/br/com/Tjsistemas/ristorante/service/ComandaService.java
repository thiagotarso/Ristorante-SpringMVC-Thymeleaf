package br.com.Tjsistemas.ristorante.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.Tjsistemas.ristorante.model.Comanda;
import br.com.Tjsistemas.ristorante.model.ItemComanda;
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
		
	     BigDecimal valorTotalItens = comanda.getItens().stream()
	    		 .map(ItemComanda::getValorTotal)
	    		 .reduce(BigDecimal::add)
	             .get();
		
			    comanda.setValorTotal(valorTotalItens);
    			comandas.save(comanda);
//    			publisher.publishEvent(new MesaEvent(comanda.getMesa)); ex. Evento
	}
}