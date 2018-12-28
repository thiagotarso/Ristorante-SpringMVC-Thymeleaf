package br.com.Tjsistemas.ristorante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Tjsistemas.ristorante.model.Categoria;

@Repository
public interface Categorias extends  JpaRepository<Categoria, Long>{

	public List<Categoria> findByEmpresaOrderByCodigoAsc(Long codigoEmpresa);
	
}
