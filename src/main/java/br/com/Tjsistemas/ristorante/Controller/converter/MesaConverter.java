package br.com.Tjsistemas.ristorante.Controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.Tjsistemas.ristorante.model.Mesa;

public class MesaConverter implements Converter<String, Mesa>{

	@Override
	public Mesa convert(String id) {

       if (!StringUtils.isEmpty(id)) {
		Mesa mesa = new Mesa();
		mesa.setId(Long.valueOf(id));
	    return mesa;
       }
		
		return null;
	}

	
	
}
