package br.com.Tjsistemas.ristorante.model;

public enum SituacaoMesa {
	
	OCUPADA("Ocupada"),
	LIVRE("Livre"),
	RESERVADA("Reservada");
	
	private String descricao;
	
	SituacaoMesa(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() { 
		return descricao;
	}
	
}
