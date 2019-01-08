package br.com.Tjsistemas.ristorante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Tjsistemas.ristorante.model.Categoria;
import br.com.Tjsistemas.ristorante.repository.helper.categorias.CategoriasQueries;

@Repository
public interface Categorias extends  JpaRepository<Categoria, Long>, CategoriasQueries{

	public List<Categoria> findByEmpresaOrderByCodigoAsc(Long codigoEmpresa);

	public Categoria findByIdAndEmpresa(Long parseLong, Long empresaSessao);
}
