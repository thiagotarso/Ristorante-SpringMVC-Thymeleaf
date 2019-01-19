package br.com.Tjsistemas.ristorante.repository.paginacao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginacaoUtil {

	public void preparar(Criteria criteria, Pageable pageable) {

		// paginacao
		int paginaAtual = pageable.getPageNumber();
		int totalRegistosPaginas = pageable.getPageSize();
		int primeiroRegistro = paginaAtual * totalRegistosPaginas;

		criteria.setFirstResult(primeiroRegistro);
		criteria.setMaxResults(totalRegistosPaginas);

		Sort sort = pageable.getSort();
		if (sort != null) {
			Sort.Order property = sort.iterator().next();
			String field = property.getProperty();
			criteria.addOrder(property.isAscending() ? Order.asc(field) : Order.desc(field));
		}
	}
}
