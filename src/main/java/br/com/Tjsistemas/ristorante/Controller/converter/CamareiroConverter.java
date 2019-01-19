package br.com.Tjsistemas.ristorante.Controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.com.Tjsistemas.ristorante.model.Camareiro;

public class CamareiroConverter implements Converter<String, Camareiro>  {

	@Override
	public Camareiro convert(String id) {
		
		if (!StringUtils.isEmpty(id)) {
			Camareiro camareiro = new Camareiro();
			camareiro.setId(Long.valueOf(id));
       
			return camareiro;
		}
		return null;
	}
}