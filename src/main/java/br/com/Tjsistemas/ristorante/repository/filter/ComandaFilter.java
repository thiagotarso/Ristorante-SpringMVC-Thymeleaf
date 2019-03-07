package br.com.Tjsistemas.ristorante.repository.filter;

import java.math.BigDecimal;

import br.com.Tjsistemas.ristorante.model.Camareiro;
import br.com.Tjsistemas.ristorante.model.Cliente;
import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.model.Produto;
import br.com.Tjsistemas.ristorante.model.SetorPreparo;
import br.com.Tjsistemas.ristorante.model.StatusComanda;

public class ComandaFilter {

	public Long codigo;
	public BigDecimal desconto;
    public BigDecimal valorMinimo;
    public BigDecimal valorMaximo;
	public StatusComanda status;
	public SetorPreparo setorPreparo;
    public Mesa mesa;
    public Camareiro camareiro;
    public Cliente cliente;
    public Produto produto;
    public Long empresa;
    
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}
	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}
	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}
	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
	public StatusComanda getStatus() {
		return status;
	}
	public void setStatus(StatusComanda status) {
		this.status = status;
	}
	public SetorPreparo getSetorPreparo() {
		return setorPreparo;
	}
	public void setSetorPreparo(SetorPreparo setorPreparo) {
		this.setorPreparo = setorPreparo;
	}
	public Mesa getMesa() {
		return mesa;
	}
	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}
	public Camareiro getCamareiro() {
		return camareiro;
	}
	public void setCamareiro(Camareiro camareiro) {
		this.camareiro = camareiro;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public BigDecimal getDesconto() {
		return desconto;
	}
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
	public Long getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}
}
