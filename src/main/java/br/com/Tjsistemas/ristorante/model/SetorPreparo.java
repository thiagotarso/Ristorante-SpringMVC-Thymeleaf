package br.com.Tjsistemas.ristorante.model;

public enum SetorPreparo {

	BAR("Bar Principal"),
	BAR2("Bar Secundario"),
	COZINHA1 ("Cozinha Principal"),
	COZINHA2 ("Cozinha Secundaria");
	
	private String descricao;

	private SetorPreparo(String descricao) {
     this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}