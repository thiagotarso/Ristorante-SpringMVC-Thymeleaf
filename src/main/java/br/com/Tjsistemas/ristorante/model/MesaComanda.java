package br.com.Tjsistemas.ristorante.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="mesa_comanda")
public class MesaComanda {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="mesa_comanda_id")
	private Long id;
		
	@ManyToOne
	@JoinColumn(name="comanda_id")
	private Comanda comanda;
	
	@ManyToOne
	@JoinColumn(name="mesa_id")
	private Mesa mesa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Comanda getComanda() {
		return comanda;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public void setComanda(Comanda comanda) {
		this.comanda = comanda;
	}

	public Mesa getMesa() {
		return mesa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comanda == null) ? 0 : comanda.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mesa == null) ? 0 : mesa.hashCode());
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
		MesaComanda other = (MesaComanda) obj;
		if (comanda == null) {
			if (other.comanda != null)
				return false;
		} else if (!comanda.equals(other.comanda))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mesa == null) {
			if (other.mesa != null)
				return false;
		} else if (!mesa.equals(other.mesa))
			return false;
		return true;
	}
}