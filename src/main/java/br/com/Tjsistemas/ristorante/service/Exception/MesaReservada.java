package br.com.Tjsistemas.ristorante.service.Exception;

public class MesaReservada extends RuntimeException {
	private static final long serialVersionUID = 1L;
   
	public MesaReservada(String message){
		super(message);
	}
}
