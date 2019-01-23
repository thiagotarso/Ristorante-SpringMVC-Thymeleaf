package br.com.Tjsistemas.ristorante.repository.helper.comanda;

import java.math.BigDecimal;
import java.util.List;

import br.com.Tjsistemas.ristorante.dto.ComandaMes;
import br.com.Tjsistemas.ristorante.model.Comanda;
import br.com.Tjsistemas.ristorante.model.ItemComanda;
import br.com.Tjsistemas.ristorante.model.MesaComanda;

public interface ComandasQueries {
	
	public List<Comanda> ListaDeComandas(Long empresa); 
	public List<ItemComanda> BuscarItensComanda(Comanda comanda);
	public List<MesaComanda> BuscarMesasComanda(Comanda comanda);
	
	public BigDecimal totalComandaAnual(Long empresa);
	public BigDecimal totalComandaMes(Long empresa);
	public BigDecimal valorTicketMedioAno(Long empresa);
	
	public List<ComandaMes> totalPorMes(Long empresa); 
}
