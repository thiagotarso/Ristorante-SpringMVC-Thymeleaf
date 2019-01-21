package br.com.Tjsistemas.ristorante.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
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
import br.com.Tjsistemas.ristorante.model.Camareiro;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Camareiros;
import br.com.Tjsistemas.ristorante.repository.filter.CamareiroFilter;
import br.com.Tjsistemas.ristorante.service.CamareiroService;

@Controller
@RequestMapping("/camareiro")
public class CamareiroController {
	
	@Autowired
	private CamareiroService camareiroService;
	
	@Autowired
	private Camareiros camareiros;
	
	@GetMapping("/novo")
	public ModelAndView novo(Camareiro camareiro) {
		ModelAndView mv = new ModelAndView("/camareiro/cadastroCamareiro");
		return mv;
	}
	
   @PostMapping(value ={"/novo", "{\\d+}"})	
   public ModelAndView salvar(@Valid Camareiro camareiro, BindingResult bindingResult,
		                                Model model, RedirectAttributes attributes ){
	  
	   if (bindingResult.hasErrors()) {
		  return novo(camareiro);  
	    }
	   
	   try {
	   camareiro.setEmpresa(empresaSessao(camareiro));
       camareiroService.salvar(camareiro);		
       
	} catch (Exception e) {
		return novo(camareiro); 
	}
	  attributes.addFlashAttribute("mensagem", "Camareiro salvo com Sucesso!");  
	  return new ModelAndView("redirect:/camareiro/novo"); 
   }
   
	@GetMapping
	 public ModelAndView pesquisar(CamareiroFilter camareirofilter, BindingResult result,
			                      @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest ) {
		  ModelAndView mv = new ModelAndView("/camareiro/pesquisaCamareiros");
		  camareirofilter.setEmpresa(empresaSessao(null));
		  
		  PageWrapper<Camareiro> paginasWrapper = new PageWrapper<>(camareiros.filtrar(camareirofilter, pageable), httpServletRequest);
		  mv.addObject("pagina", paginasWrapper);
		  
		  return mv;
	}
   
   @GetMapping("/{id}")
   public ModelAndView editar(@PathVariable Long id) {
	 Camareiro camareiro = camareiros.findByIdAndEmpresa(id, empresaSessao(null));
	   
	 ModelAndView mv = novo(camareiro);
     mv.addObject(camareiro);	   
	 return mv;
  }
   
	private Long empresaSessao(Camareiro camrerero) {
		 Usuario usuarioSessaos = (Usuario)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 
		 if (camrerero !=  null) {
		 	return  camrerero.getEmpresa() != null ? camrerero.getEmpresa() :usuarioSessaos.getEmpresa();
		 }else {
			 return  usuarioSessaos.getEmpresa();
		 }
	}
}