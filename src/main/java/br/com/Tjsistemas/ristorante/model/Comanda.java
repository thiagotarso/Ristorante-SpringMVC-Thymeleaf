package br.com.Tjsistemas.ristorante.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="comanda")
public class Comanda {
     
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="comanda_id")
	private Long id;
	
	private Long codigo;
	
	private String observacoes; 
	
	@Column(name="iniciar_atendimento")
	public LocalDateTime inicioAtendimento;
	
	@Column(name="valor_total")
	private BigDecimal valorTotal;
	
	@NotNull(message="Seleceione Um Cliente!")
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@NotNull(message="Seleceione Um Camareiro!")
    @ManyToOne
    @JoinColumn(name="camareiro_id")
	private Camareiro camareiro;
    
//    @Size(min=1 , message="selecione uma ou mais mesas!")
//    @ManyToMany(fetch= FetchType.EAGER )// duplicaçao produtos na comanda
//	@JoinTable(name="comanda_mesa", joinColumns = @JoinColumn(name ="id_comanda"), inverseJoinColumns = @JoinColumn(name="id_mesa"))
//	private List<Mesa> mesa;

    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true) 
    private List<MesaComanda> mesasComanda = new ArrayList<>();
	
	@OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true) // corrigir
	private List<ItemComanda> itens = new ArrayList<>();
	
	private Long empresa;
	
	@Transient
	private String uuid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Long getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Camareiro getCamareiro() {
		return camareiro;
	}

	public void setCamareiro(Camareiro camareiro) {
		this.camareiro = camareiro;
	}
    
//	public List<Mesa> getMesa() {
//		return mesa;
//	}
//
//	public void setMesa(List<Mesa> mesa) {
//		this.mesa = mesa;
//	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public LocalDateTime getInicioAtendimento() {
		return inicioAtendimento;
	}

	public void setInicioAtendimento(LocalDateTime inicioAtendimento) {
		this.inicioAtendimento = inicioAtendimento;
	}

	public List<ItemComanda> getItens() {
		return itens;
	}

	public void setItens(List<ItemComanda> itens) {
		this.itens = itens;
	}

	public boolean isNova(){
		return id == null;
	}

	public void adicionarItens(List<ItemComanda> itens) {
		this.itens = itens;
		this.itens.forEach(i -> i.setComanda(this));
	}
	
	public void adicionarMesas(List<MesaComanda> mesa) {
		this.mesasComanda = mesa;
		this.mesasComanda.forEach(i -> i.setComanda(this));
	}
	
	public List<MesaComanda> getMesasComanda() {
		return mesasComanda;
	}

	public void setMesasComanda(List<MesaComanda> mesasComanda) {
		this.mesasComanda = mesasComanda;
	}

	public LocalTime getTempoEmAberto(){
		Long totalMinutes = ChronoUnit.MINUTES.between(inicioAtendimento, LocalDateTime.now());
		int horas = (int) (totalMinutes / 60L); 
		int minutes = (int) (totalMinutes % 60);
		
		return	LocalTime.of(horas, minutes);
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Comanda other = (Comanda) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}