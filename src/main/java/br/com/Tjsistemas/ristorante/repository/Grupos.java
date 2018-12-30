package br.com.Tjsistemas.ristorante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.Tjsistemas.ristorante.model.Grupo;
import br.com.Tjsistemas.ristorante.repository.helper.grupos.GruposQueries;

public interface Grupos extends JpaRepository<Grupo, Long>, GruposQueries{
	
	public List<Grupo> findByEmpresaOrderByIdAsc(Long codigoEmpresa); 
}
