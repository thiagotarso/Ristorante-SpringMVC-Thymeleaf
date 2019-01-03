package br.com.Tjsistemas.ristorante.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.Tjsistemas.ristorante.Controller.page.PageWrapper;
import br.com.Tjsistemas.ristorante.model.Categoria;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Categorias;
import br.com.Tjsistemas.ristorante.repository.filter.Categoriafilter;
import br.com.Tjsistemas.ristorante.service.CategoriaService;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private Categorias categorias;
	
	@GetMapping("/nova")
	public ModelAndView novo(Categoria categoria){
		ModelAndView mv = new ModelAndView("categoria/cadastroCategoria");
		return mv;
	}

	@PostMapping("/nova")
	public ModelAndView salvar(@Valid Categoria categoria, BindingResult bindingResult,
			@AuthenticationPrincipal Usuario usuarioSessao, Model model, RedirectAttributes attributes){
		
		if(bindingResult.hasErrors()){
			return novo(categoria);
		}
		
		 categoria.setEmpresa(usuarioSessao.getEmpresa()); 
		 categoriaService.salvar(categoria);		
		
	    attributes.addFlashAttribute("mensagem", "categoria salvo com Sucesso!");   
		
		return  new ModelAndView("categoria/cadastroCategoria"); 
	}
	
	   
	@GetMapping
	 public ModelAndView pesquisar(Categoriafilter categoriafilter, BindingResult result,
			                      @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest ) {
		  ModelAndView mv = new ModelAndView("/categoria/pesquisaCategoria");
		  
		  PageWrapper<Categoria> paginasWrapper = new PageWrapper<>(categorias.filtrar(categoriafilter, pageable), httpServletRequest);
		  mv.addObject("pagina", paginasWrapper);
		  
		  return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		Categoria categoria = categorias.findOne(id);
		
		ModelAndView mv = novo(categoria);
		mv.addObject(categoria);
		return mv;
	}
}