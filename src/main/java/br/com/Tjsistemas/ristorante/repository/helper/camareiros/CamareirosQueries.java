package br.com.Tjsistemas.ristorante.repository.helper.camareiros;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.Tjsistemas.ristorante.model.Camareiro;
import br.com.Tjsistemas.ristorante.repository.filter.CamareiroFilter;

public interface CamareirosQueries {
	
       public Long codigoCamareiros(Long codigoCamareiros);	
       public Page<Camareiro> filtrar(CamareiroFilter camareiroFilter, Pageable pageable);
}
