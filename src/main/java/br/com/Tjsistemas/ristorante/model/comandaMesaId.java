package br.com.Tjsistemas.ristorante.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class comandaMesaId implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="id_comanda", insertable = false, updatable = false)
	private Comanda idComanda;
	
	@ManyToOne
	@JoinColumn(name="id_mesa")
	private Mesa idMesa;

	public Comanda getIdComanda() {
		return idComanda;
	}

	public void setIdComanda(Comanda idComanda) {
		this.idComanda = idComanda;
	}

	public Mesa getIdMesa() {
		return idMesa;
	}

	public void setIdMesa(Mesa idMesa) {
		this.idMesa = idMesa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idComanda == null) ? 0 : idComanda.hashCode());
		result = prime * result + ((idMesa == null) ? 0 : idMesa.hashCode());
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
		comandaMesaId other = (comandaMesaId) obj;
		if (idComanda == null) {
			if (other.idComanda != null)
				return false;
		} else if (!idComanda.equals(other.idComanda))
			return false;
		if (idMesa == null) {
			if (other.idMesa != null)
				return false;
		} else if (!idMesa.equals(other.idMesa))
			return false;
		return true;
	}

}