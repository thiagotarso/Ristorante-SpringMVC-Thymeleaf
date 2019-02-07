package br.com.Tjsistemas.ristorante.Controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.Tjsistemas.ristorante.model.Comanda;

@Component
public class ComandaValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Comanda.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "cliente.id","", "Selecione um cliente na pesquisa rápida!");
		
		Comanda comanda = (Comanda) target;
		validarSeInformouItens(errors, comanda);
		validarValorTotalNegativo(errors ,comanda);
        ValidarSeInformouMesas(errors, comanda);
	}
	
	public void validarValorTotalNegativo(Errors errors, Comanda comanda) {
		if (comanda.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
			errors.reject("","Valor Total da Comanda nao pode ser negativo!");
		}
	}
	
	public void validarSeInformouItens(Errors errors, Comanda comanda) {
		if (comanda.getItens().isEmpty()) {
			errors.reject("", "Adicione pelo menos 1 dos Produtos listados!");
		}
	}
	
	private void ValidarSeInformouMesas(Errors errors, Comanda comanda) {
		if (comanda.getMesasComanda().isEmpty()) {
		   errors.reject("", "Adicione pelo menos 1 mesa!");
     	}
	}
}
