package br.com.Tjsistemas.ristorante.repository.helper.produtos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.Tjsistemas.ristorante.dto.ValorItensEstoque;
import br.com.Tjsistemas.ristorante.model.Produto;
import br.com.Tjsistemas.ristorante.repository.filter.ProdutoFilter;

public interface ProdutosQueries {

	public List<Produto> porDescricao(String descricao, Long empresa);
	public Long codigoProduto(Long codigoEmpresaProduto);
	public Page<Produto> filtrar(ProdutoFilter filtro, Pageable pageable);

	public ValorItensEstoque valorItensEstoque();
}
