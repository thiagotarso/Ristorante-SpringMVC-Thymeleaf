package br.com.Tjsistemas.ristorante.repository.helper.produtos;

import java.util.List;

import br.com.Tjsistemas.ristorante.model.Produto;

public interface ProdutosQueries {

	public List<Produto> porDescricao(String descricao);
	
}
