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
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Grupos;
import br.com.Tjsistemas.ristorante.repository.Usuarios;
import br.com.Tjsistemas.ristorante.repository.filter.UsuarioFilter;
import br.com.Tjsistemas.ristorante.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService UsuarioService;
	
	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private Grupos grupos;
	
	@GetMapping("/novo")
	public ModelAndView novo(Usuario usuario){
     ModelAndView mv = new ModelAndView("/usuario/cadastroUsuario");
     mv.addObject("grupos", grupos.findAll());
     
	return mv;	
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Usuario usuario,  BindingResult bindingResult,
			       @AuthenticationPrincipal Usuario usuarioSessao, Model model, RedirectAttributes attributes) {
	
		if (bindingResult.hasErrors()) {
			return novo(usuarioSessao);
		}
		try {
			
			usuario.setEmpresa(usuarioSessao.getEmpresa()); 
			UsuarioService.salvar(usuario);
			
		} catch (Exception e) {
			System.out.println("erro ao salvar"+ e);
		}
		attributes.addFlashAttribute("mensagem", "Produto salvo com Sucesso!"); 
		return new ModelAndView("redirect:/usuario/novo");
	}
	
	@GetMapping
	 public ModelAndView pesquisar(UsuarioFilter  usuarioFilter, BindingResult result,
			                      @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest ) {
		  ModelAndView mv = new ModelAndView("/usuario/pesquisaUsuario");
		  
		  PageWrapper<Usuario> paginasWrapper = new PageWrapper<>(usuarios.filtrar(usuarioFilter, pageable), httpServletRequest);
		  mv.addObject("pagina", paginasWrapper);
		  
		  return mv;
	}
	
   @GetMapping("/{id}")
   public void editar(@PathVariable Long id){
	   Usuario usuario = usuarios.findOne(id);
       
	   usuario.getEmpresaUsuario().forEach(f ->{
		   System.out.println( f.getEmpresa().getFantasia());
	   });
   }	
}