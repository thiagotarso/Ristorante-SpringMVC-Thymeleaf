package br.com.Tjsistemas.ristorante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.Tjsistemas.ristorante.model.Categoria;
import br.com.Tjsistemas.ristorante.repository.Categorias;

@Service
public class CategoriaService {

	@Autowired
	private Categorias categorias;
	
	@Transactional
	public void salvar(Categoria categoria) {
		
		if (categoria.isNova()) {
		 categoria.setCodigo(categorias.codigoCategorias(categoria.getEmpresa()));
		 }	
		
		categorias.save(categoria);
	}
}