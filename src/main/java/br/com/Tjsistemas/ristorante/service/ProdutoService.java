package br.com.Tjsistemas.ristorante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.Tjsistemas.ristorante.model.Produto;
import br.com.Tjsistemas.ristorante.repository.Produtos;

@Service
public class ProdutoService {

  @Autowired 
  private Produtos produtos;
  
  @Transactional
  public Produto salvar(Produto produto){
	 
	  if (produto.isNovo()) {
		  produto.setCodigo(produtos.codigoProduto(produto.getEmpresa()));
		}
	  
	  return produtos.save(produto); //produtos.saveAndFlush(produto);  salvar e retorna o obj
  }
}