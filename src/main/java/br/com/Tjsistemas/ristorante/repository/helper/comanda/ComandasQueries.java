package br.com.Tjsistemas.ristorante.repository.helper.comanda;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.Tjsistemas.ristorante.dto.ComandaMes;
import br.com.Tjsistemas.ristorante.model.Comanda;
import br.com.Tjsistemas.ristorante.model.ItemComanda;
import br.com.Tjsistemas.ristorante.model.MesaComanda;
import br.com.Tjsistemas.ristorante.repository.filter.ComandaFilter;

public interface ComandasQueries {
	
	public List<Comanda> ListaDeComandas(Long empresa); 
	public List<ItemComanda> BuscarItensComanda(Comanda comanda);
	public List<MesaComanda> BuscarMesasComanda(Comanda comanda);
	public Page<Comanda> filtrar(ComandaFilter comandaFilter, Pageable pageable);
	
	public BigDecimal totalComandaAnual(Long empresa);
	public BigDecimal totalComandaMes(Long empresa);
	public BigDecimal valorTicketMedioAno(Long empresa);
	
	public List<ComandaMes> totalPorMes(Long empresa); 
}
