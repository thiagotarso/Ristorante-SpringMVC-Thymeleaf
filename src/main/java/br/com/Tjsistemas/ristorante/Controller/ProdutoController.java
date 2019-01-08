package br.com.Tjsistemas.ristorante.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.Tjsistemas.ristorante.model.Categoria;
import br.com.Tjsistemas.ristorante.model.Produto;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Categorias;
import br.com.Tjsistemas.ristorante.repository.Produtos;
import br.com.Tjsistemas.ristorante.service.ProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoController {
	
	@Autowired
	private Produtos produtos;
	
	@Autowired
	private Categorias categorias;
	
	@Autowired
	private ProdutoService produtoService;
  
	@GetMapping("/novo")
	public ModelAndView novo(Produto produto){
		ModelAndView mv = new ModelAndView("/produto/cadastroProduto");
		mv.addObject("categorias", categorias.findByEmpresaOrderByCodigoAsc(empresaSessao(produto)));
		
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Produto produto,BindingResult bindingResult, Model model, RedirectAttributes attributes){
		
		if (bindingResult.hasErrors()) {
			return novo(produto);
		}
		try {
			
			produto.setEmpresa(empresaSessao(produto));
			produtoService.salvar(produto);
		} catch (Exception e) {
			return novo(produto); 
		}
		attributes.addFlashAttribute("mensagem", "Produto salvo com Sucesso!"); 
		return new ModelAndView("redirect:/produto/novo");
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
	 Produto produto = produtos.findByIdAndEmpresa(id, empresaSessao(null));
		
	 ModelAndView mv = novo(produto);
	 mv.addObject(produto);	
	 return mv;
	}
	
	
	@RequestMapping(consumes= {MediaType.APPLICATION_JSON_VALUE})
	private @ResponseBody List<Produto> listaProdutos(String categoria){
		Long empresa =empresaSessao(null);
        // categorias.findOne
		Categoria categoriaSelecionada = categorias.findByIdAndEmpresa(Long.parseLong(categoria), empresa);
		return  produtos.findByCategoriaAndEmpresa(categoriaSelecionada, empresa);
	}
	
	@GetMapping("/filtro")
	private @ResponseBody ResponseEntity<?> buscarProdutosDescricao(String descricao){
		return ResponseEntity.ok(produtos.porDescricao(descricao, empresaSessao(null)));
	}
	
	private Long empresaSessao(Produto  produto) {
		 Usuario usuarioSessaos = (Usuario)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 if (produto !=  null) {
			return  produto.getEmpresa() != null ? produto.getEmpresa() :usuarioSessaos.getEmpresa();
		 }else {
			 return usuarioSessaos.getEmpresa();
		 }
	}
}