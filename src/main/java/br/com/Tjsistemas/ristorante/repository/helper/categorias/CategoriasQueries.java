package br.com.Tjsistemas.ristorante.repository.helper.categorias;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.Tjsistemas.ristorante.model.Categoria;
import br.com.Tjsistemas.ristorante.repository.filter.Categoriafilter;

public interface CategoriasQueries {

	public Long codigoCategorias(Long codigoUsuario);
	public Page<Categoria> filtrar(Categoriafilter filtro,Pageable pageable);
}
