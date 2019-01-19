package br.com.Tjsistemas.ristorante.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.model.SituacaoMesa;
import br.com.Tjsistemas.ristorante.repository.Mesas;

@Component
public class MesaListener {

	@Autowired
	private Mesas mesas;
	
	@EventListener
	public void AlterarSituacaoMesa(MesaEvent mesaEvent){
	
	for (Mesa mes : mesaEvent.getMesa()) {
		 Mesa mesa = mesas.findOne(mes.getId());
		 mesa.setSituacaoMesa(SituacaoMesa.OCUPADA);
	}	
  }
}