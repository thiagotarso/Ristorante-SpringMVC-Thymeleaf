package br.com.Tjsistemas.ristorante.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.model.SituacaoMesa;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Mesas;
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
	
	@PostMapping("/nova")
	public ModelAndView salvar(@Valid Mesa mesa,BindingResult bindingResult, 
			    @AuthenticationPrincipal Usuario usuarioSessao , Model model, RedirectAttributes attributes ) {
	
		if (bindingResult.hasErrors()) {
			return nova(mesa);
		}
		
		mesa.setEmpresa(usuarioSessao.getEmpresa());
		mesaService.salve(mesa);
		
		attributes.addFlashAttribute("mesagem", "Mesa Salva Com Sucesso!");
        return new ModelAndView("redirect:/mesa/nova");		
	}
	
	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id) {
		Mesa mesa = mesas.findOne(id);
		
		ModelAndView mv = nova(mesa);
		mv.addObject(mesa);
      return mv;
	}
}