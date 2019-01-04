package br.com.Tjsistemas.ristorante.repository.helper.mesa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.repository.filter.MesaFilter;

public interface MesasQueries {

	public List<Mesa> mesasLivres();
	public Long codigoMesa(Long codigoMesa);
	public Page<Mesa> filtrar(MesaFilter mesaFilter, Pageable pageable);
}