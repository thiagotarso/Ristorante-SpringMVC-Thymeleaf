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
import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.model.SituacaoMesa;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Mesas;
import br.com.Tjsistemas.ristorante.repository.filter.MesaFilter;
import br.com.Tjsistemas.ristorante.service.MesaService;

@Controller
@RequestMapping("/mesa")
public class MesaController {

	@Autowired
	private MesaService mesaService;
	
	@Autowired
	private Mesas mesas;

	@GetMapping("/nova")
	public ModelAndView nova(Mesa mesa) {
	 ModelAndView mv = new ModelAndView("/mesas/cadastroMesa");
	 mv.addObject("SituacaoMesa", SituacaoMesa.values());
	return mv;	
	}
	
	@PostMapping(value ={"/nova", "{\\d+}"})
	public ModelAndView salvar(@Valid Mesa mesa,BindingResult bindingResult, 
			                          Model model, RedirectAttributes attributes ) {
	
		if (bindingResult.hasErrors()) {
			return nova(mesa);
		}
		
		mesa.setEmpresa(empresaSessao(mesa));
		mesaService.salve(mesa);
		
		attributes.addFlashAttribute("mesagem", "Mesa Salva Com Sucesso!");
        return new ModelAndView("redirect:/mesa/nova");		
	}
	
	@GetMapping
	 public ModelAndView pesquisar(MesaFilter mesafilter, BindingResult result,
			                      @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest ) {
		  ModelAndView mv = new ModelAndView("/mesas/pesquisaMesa");
		  mv.addObject("situacao", SituacaoMesa.values());
          mesafilter.setEmpresa(empresaSessao(null));
		  
		  PageWrapper<Mesa> paginasWrapper = new PageWrapper<>(mesas.filtrar(mesafilter, pageable), httpServletRequest);
		  mv.addObject("pagina", paginasWrapper);
		  
		  return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		Mesa mesa = mesas.findByIdAndEmpresa(id, empresaSessao(null));
		
		ModelAndView mv = nova(mesa);
		mv.addObject(mesa);
      return mv;
	}
	
	private Long empresaSessao(Mesa mesa) {
		 Usuario usuarioSessaos = (Usuario)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 if (mesa !=  null) {
			return  mesa.getEmpresa() != null ? mesa.getEmpresa() :usuarioSessaos.getEmpresa();
		 }else {
			 return usuarioSessaos.getEmpresa();
		 }
	}
}