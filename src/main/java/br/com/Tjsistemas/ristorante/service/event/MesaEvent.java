package br.com.Tjsistemas.ristorante.service.event;

import java.util.List;

import br.com.Tjsistemas.ristorante.model.Mesa;

public class MesaEvent {

	private List<Mesa> mesa;

	public MesaEvent(List<Mesa> list) {
		super();
		this.mesa = list;
	}

	public List<Mesa> getMesa() {
		return mesa;
	}
}
