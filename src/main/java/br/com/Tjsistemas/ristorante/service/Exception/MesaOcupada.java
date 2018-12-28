package br.com.Tjsistemas.ristorante.service.Exception;

public class MesaOcupada extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MesaOcupada(String messages){
	super(messages);	
	}
	 
}
