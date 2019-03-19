package br.com.Tjsistemas.ristorante.session;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import br.com.Tjsistemas.ristorante.model.ItemComanda;
import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.model.MesaComanda;
import br.com.Tjsistemas.ristorante.model.Produto;


class TabelaItensComanda {
    
	private String uuid;
	public List<ItemComanda> itens = new ArrayList<>();
	
//	inicia aqui
	public List<MesaComanda> mesas = new ArrayList<>();
     
	public void adicionaMesa(Mesa mesa) {
		
		 Optional<MesaComanda> mesaComandaOptional = buscarMesas(mesa);
		 
		     MesaComanda mesaComanda = null;
		    if(mesaComandaOptional.isPresent()) {
		    	mesaComanda = mesaComandaOptional.get();
			}else{
			
			mesaComanda = new MesaComanda();
			mesaComanda.setMesa(mesa);;
	        
			mesas.add(mesaComanda);
			}
		}
	
	private Optional<MesaComanda> buscarMesas(Mesa mesa) {
		 return	 mesas.stream()
				.filter(i -> i.getMesa().equals(mesa))
				.findAny();
		}
	
	public void removerMesaComanda(Mesa mesa){
	    int indice = IntStream.range(0, mesas.size())
	 		   .filter(i -> mesas.get(i).getMesa().equals(mesa))
	 		   .findAny().getAsInt();
	     
	      mesas.remove(indice);
		}
	
	public List<MesaComanda> getMesas(){
		return mesas;
	}
	
//	acaba aqqui
	
	public TabelaItensComanda(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getValorTotal(){
		return itens.stream()
		    .map(ItemComanda::getValorTotal)
		      .reduce(BigDecimal::add)
		        .orElse(BigDecimal.ZERO);
	}

	public Integer getQuantitadeItens() {
		return itens.stream()
				.map(ItemComanda::getQuantidade)
				 .reduce(Integer::sum)
				  .orElse(0);
	}
	
	public void adicionaItem(Produto produto, Integer quantidade,  Integer quantidadeAdicionada, LocalDateTime controleAtendimento, String obs) {
		
	 Optional<ItemComanda> itemComandaOptional = buscarItemPorProduto(produto);
	 
	    ItemComanda itemComanda = null;
	    if(itemComandaOptional.isPresent()) {
			itemComanda = itemComandaOptional.get();
			itemComanda.setQuantidade(itemComanda.getQuantidade() + quantidade);
            itemComanda.setQuantidadeAdicionada(quantidadeAdicionada);
			
			if(obs != null) {
			      itemComanda.setObservacoes(obs);
			   }
			
		}else{
		
		itemComanda = new ItemComanda();
		itemComanda.setProduto(produto);
		itemComanda.setSetorPreparo(produto.getSetorPreparo());
        itemComanda.setQuantidade(quantidade);
        itemComanda.setValorUnitario(produto.getValor());
        itemComanda.setQuantidadeAdicionada(quantidadeAdicionada);
        
        itemComanda.setObservacoes(obs);
        
        itens.add(0, itemComanda);
		}
	}
	
	public void alterarQuantidadeItens(Produto produto, Integer quantidade){
		ItemComanda itemComanda = buscarItemPorProduto(produto).get();
		Integer quantidadeAlterada = quantidade - itemComanda.getQuantidade() ;

	    if(quantidadeAlterada >= 0 ){
	    	itemComanda.setQuantidadeAdicionada(itemComanda.getQuantidadeAdicionada() + quantidadeAlterada);
	      }
	    
	    else if (quantidadeAlterada < 0 ){
	    	Integer novaQtd = itemComanda.getQuantidade() - quantidade;
	    	itemComanda.setQuantidadeAdicionada(itemComanda.getQuantidadeAdicionada() - novaQtd);
	      }
	      
	      itemComanda.setQuantidade(quantidade);
	}
	
	public ItemComanda buscarObservacoesItens(Produto produto){
		return buscarItemPorProduto(produto).get();
	}

	
	public void removerItemComanda(Produto produto){
    int indice = IntStream.range(0, itens.size())
 		   .filter(i -> itens.get(i).getProduto().equals(produto))
 		   .findAny().getAsInt();
     
	    itens.remove(indice);
	}
	    
	public int total(){
		return itens.size();
	}
	
	public List<ItemComanda> getItens(){
		return itens; 
	}
	

	private Optional<ItemComanda> buscarItemPorProduto(Produto produto) {
	 return	 itens.stream()
			.filter(i -> i.getProduto().equals(produto))
			.findAny();
	}
	
	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabelaItensComanda other = (TabelaItensComanda) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

}
