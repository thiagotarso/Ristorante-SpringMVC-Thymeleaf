package br.com.Tjsistemas.ristorante.model;

public enum CategoriaCores {

	danger("Rojo"),
	success("Verde"),
	warning("Amarillo"),
	primary("Azul"),
	info("Azul claro"),
	black("Preto"),
	pink("Rosa"),
	gray("gris"),
	purple("púrpura"),
	fuschsia("fucsia");
	
	private String descricao;
	 
	CategoriaCores(String descricao) {
      this.descricao = descricao;
	}
 
	public String getDescricao() {
		return this.descricao;
	}
}
