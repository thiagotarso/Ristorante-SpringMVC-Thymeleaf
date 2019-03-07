package br.com.Tjsistemas.ristorante.dto;

public class PreparoDTO {

	public Long idComanda;
	public Long codigo;
	public String status;
	public Long numeroMesa;
	public String nomeCamareiro;
	public String dataAlteracao;
	
	public PreparoDTO(Long idComanda, Long codigo, String status, Long numeroMesa, String nomeCamareiro, String dataAlteracao) {
		this.idComanda = idComanda;
		this.codigo = codigo;
		this.status = status;
		this.numeroMesa = numeroMesa;
		this.nomeCamareiro = nomeCamareiro;
		this.dataAlteracao = dataAlteracao;
	}
	public Long getIdComanda() {
		return idComanda;
	}
	public void setIdComanda(Long idComanda) {
		this.idComanda = idComanda;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getNumeroMesa() {
		return numeroMesa;
	}
	public void setNumeroMesa(Long numeroMesa) {
		this.numeroMesa = numeroMesa;
	}
	public String getNomeCamareiro() {
		return nomeCamareiro;
	}
	public void setNomeMamareiro(String nomeCamareiro) {
		this.nomeCamareiro = nomeCamareiro;
	}
	public String getDataAlteracao() {
		return dataAlteracao;
	}
	public void setDataAlteracao(String dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	
	
}