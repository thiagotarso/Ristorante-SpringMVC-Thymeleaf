package br.com.Tjsistemas.ristorante.model;

public enum StatusComanda {
  
	EMITIDA("Emitida"),
	ENCERRADA("Encerrada"),
	CANCELADA("Cancelada"),
	DELIVERY("Delivery");
	
   	private String descricao;
    
    StatusComanda(String descricao){
   		 this.descricao = descricao;
   	}
   	
   	public String getDescricao() {
   		return descricao ;
   	} 
}
