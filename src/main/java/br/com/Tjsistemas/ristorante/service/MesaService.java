package br.com.Tjsistemas.ristorante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.repository.Mesas;

@Service
public class MesaService {
	
	@Autowired
	private Mesas mesas;
	
	@Transactional
	public Mesa salve(Mesa mesa){
		
		mesa.setCodigo(mesas.codigoMesa(mesa.getEmpresa()));
		return mesas.saveAndFlush(mesa);
	}
}
