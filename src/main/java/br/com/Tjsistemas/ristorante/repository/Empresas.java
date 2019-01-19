package br.com.Tjsistemas.ristorante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Tjsistemas.ristorante.model.Empresa;

@Repository
public interface Empresas extends JpaRepository<Empresa, Long> {
	
}
