package br.com.Tjsistemas.ristorante.Controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.Tjsistemas.ristorante.model.Cliente;

public class ClienteConverter implements Converter<String, Cliente> {

	@Override
	public Cliente convert(String id) {
		
		if ( !StringUtils.isEmpty(id)) {
			Cliente cliente = new Cliente();
			cliente.setId(Long.valueOf(id));
			return cliente;
		}
		
		return null;
	}
}