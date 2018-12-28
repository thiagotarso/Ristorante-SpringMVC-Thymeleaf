package br.com.Tjsistemas.ristorante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.Tjsistemas.ristorante.model.Camareiro;
import br.com.Tjsistemas.ristorante.repository.Camareiros;

@Service
public class CamareiroService {
	
	@Autowired
	public Camareiros camareiros;
	
	@Transactional
	public void salvar(Camareiro camareiro){
	    camareiros.save(camareiro);	
	}
}
