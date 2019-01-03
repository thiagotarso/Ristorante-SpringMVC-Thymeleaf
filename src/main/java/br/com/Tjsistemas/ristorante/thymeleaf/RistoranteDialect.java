package br.com.Tjsistemas.ristorante.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import br.com.Tjsistemas.ristorante.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import br.com.Tjsistemas.ristorante.thymeleaf.processor.OrderElementTagProcessor;
import br.com.Tjsistemas.ristorante.thymeleaf.processor.PaginationElementTagProcessor;

public class RistoranteDialect extends AbstractProcessorDialect {
	
	public RistoranteDialect() {
		super("TJsistemas Ristorante", "ristorante", StandardDialect.PROCESSOR_PRECEDENCE);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new PaginationElementTagProcessor(dialectPrefix));
//		processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
		return processadores;
	}
}
