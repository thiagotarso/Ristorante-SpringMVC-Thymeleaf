package br.com.Tjsistemas.ristorante.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mesa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="mesa_id")
	private Long id;
	
	private Long codigo;
	
	@Column(name="numero_mesa")
	private Long numeroMesa;
	
	private Long empresa;
	
	@Column(name="situacao_mesa")
	@Enumerated(EnumType.STRING)
	private SituacaoMesa situacaoMesa;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumeroMesa() {
		return numeroMesa;
	}

	public void setNumeroMesa(Long numeroMesa) {
		this.numeroMesa = numeroMesa;
	}
	
	public SituacaoMesa getSituacaoMesa() {
		return situacaoMesa;
	}

	public void setSituacaoMesa(SituacaoMesa situacaoMesa) {
		this.situacaoMesa = situacaoMesa;
	}

	@SuppressWarnings("static-access")
	public boolean isLivre(Mesa mesa){
		return mesa.getSituacaoMesa() == situacaoMesa.LIVRE;
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
		Mesa other = (Mesa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}