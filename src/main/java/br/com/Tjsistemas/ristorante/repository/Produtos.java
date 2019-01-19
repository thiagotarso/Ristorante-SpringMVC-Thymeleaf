package br.com.Tjsistemas.ristorante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Tjsistemas.ristorante.model.Categoria;
import br.com.Tjsistemas.ristorante.model.Produto;
import br.com.Tjsistemas.ristorante.repository.helper.produtos.ProdutosQueries;

@Repository
public interface Produtos extends JpaRepository<Produto, Long>, ProdutosQueries {

	public List<Produto> findByCategoriaAndEmpresa(Categoria categoria, Long empresa);
	
	public List<Produto> findByEmpresaOrderByCodigoAsc(Long codigoEmpresa);

	public Produto findByIdAndEmpresa(Long id, Long empresaSessao);
}
