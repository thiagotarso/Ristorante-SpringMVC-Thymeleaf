package br.com.Tjsistemas.ristorante.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.Tjsistemas.ristorante.model.Camareiro;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.service.CamareiroService;

@Controller
@RequestMapping("/camareiro")
public class CamareiroController {
	
	@Autowired
	private CamareiroService camareiroService;
	
	@GetMapping("/novo")
	public ModelAndView novo(Camareiro camareiro) {
		ModelAndView mv = new ModelAndView("/camareiro/cadastroCamareiro");
		return mv;
	}
	
   @PostMapping("/novo")	
   public ModelAndView salvar(@Valid Camareiro camareiro, BindingResult bindingResult, 
		   @AuthenticationPrincipal Usuario usuarioSessao, Model model, RedirectAttributes attributes ){
	  
	   if (bindingResult.hasErrors()) {
		  return novo(camareiro);  
	    }
	   
	   try {
		   		   
	   camareiro.setEmpresa(usuarioSessao.getEmpresa());
       camareiroService.salvar(camareiro);		
       
	} catch (Exception e) {
		return novo(camareiro); 
	}
	  attributes.addFlashAttribute("mensagem", "Camareiro salvo com Sucesso!");  
	  return new ModelAndView("redirect:/camareiro/novo"); 
   }
}