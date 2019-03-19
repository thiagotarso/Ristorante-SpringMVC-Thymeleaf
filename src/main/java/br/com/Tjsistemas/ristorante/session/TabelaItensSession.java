package br.com.Tjsistemas.ristorante.session;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.Tjsistemas.ristorante.model.ItemComanda;
import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.model.MesaComanda;
import br.com.Tjsistemas.ristorante.model.Produto;

@SessionScope
@Component
public class TabelaItensSession {
	
	private Set<TabelaItensComanda> tabelas = new HashSet<>();
	
	public void adicionaMesas(String uuid, Mesa mesa) {
		TabelaItensComanda tabela = buscarTabelaPorUuid(uuid);
		
		tabela.adicionaMesa(mesa);
		tabelas.add(tabela);
	}
	
	public void removerMesaComanda(String uuid, Mesa mesa) {
		TabelaItensComanda tabela = buscarTabelaPorUuid(uuid);
		tabela.removerMesaComanda(mesa);
	}
	

	public void adicionaItem(String uuid, Produto produto, Integer quantidade, Integer quantidadeAdicionada, LocalDateTime controleAtendimento, String obs) {
		TabelaItensComanda tabela = buscarTabelaPorUuid(uuid);
		
		tabela.adicionaItem(produto, quantidade, quantidadeAdicionada, controleAtendimento, obs);
		tabelas.add(tabela);
		
	}

	public void alterarQuantidadeItens(String uuid, Produto produto, Integer quantidade) {
		TabelaItensComanda tabela = buscarTabelaPorUuid(uuid);
		tabela.alterarQuantidadeItens(produto, quantidade);
		
	}
	
	public ItemComanda buscarObservacoesItens(String uuid, Produto produto) {
		TabelaItensComanda tabela = buscarTabelaPorUuid(uuid);
		return tabela.buscarObservacoesItens(produto);
	}

	public void removerItemComanda(String uuid, Produto produto) {
		TabelaItensComanda tabela = buscarTabelaPorUuid(uuid);
		tabela.removerItemComanda(produto);
	}

	public List<ItemComanda> getItens(String uuid) {
		return buscarTabelaPorUuid(uuid).getItens();
	}
	
	public List<MesaComanda> getMesas(String uuid) {
		return buscarTabelaPorUuid(uuid).getMesas();
	}
	
	public Object getValorTotal(String uuid) {
		return buscarTabelaPorUuid(uuid).getValorTotal();
	}

	public Object getQuantidadeItens(String uuid) {
		return buscarTabelaPorUuid(uuid).getQuantitadeItens();
	}
	
	private TabelaItensComanda buscarTabelaPorUuid(String uuid) {
		TabelaItensComanda tabela = tabelas.stream()
				 .filter(t -> t.getUuid().equals(uuid))
				 .findAny()
				 .orElse(new TabelaItensComanda(uuid));
	
		return tabela;
	}
}
