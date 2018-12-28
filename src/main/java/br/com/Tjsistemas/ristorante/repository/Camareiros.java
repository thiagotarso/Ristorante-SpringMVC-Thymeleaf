package br.com.Tjsistemas.ristorante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Tjsistemas.ristorante.model.Camareiro;
import br.com.Tjsistemas.ristorante.repository.helper.camareiros.CamareirosQueries;

@Repository
public interface Camareiros extends JpaRepository<Camareiro, Long>, CamareirosQueries {
	
	 public List<Camareiro> findByEmpresaOrderByCodigoAsc(Long codigoEmpresa); 

}
