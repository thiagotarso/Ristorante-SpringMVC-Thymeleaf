package br.com.Tjsistemas.ristorante.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.Tjsistemas.ristorante.model.Cliente;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Clientes;
import br.com.Tjsistemas.ristorante.service.ClienteService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired 
	private Clientes clientes;
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/novo")
	public ModelAndView novo(Cliente cliente){
	 ModelAndView mv = new ModelAndView("/cliente/cadastroCliente");	
	 return mv;
	}

   @PostMapping("/novo")
   public ModelAndView salvar(@Valid Cliente cliente, BindingResult bindingResult,
		          @AuthenticationPrincipal Usuario usuarioSessao , Model model, RedirectAttributes attributes) {
	
	   if (bindingResult.hasErrors()) {
		 return novo(cliente);
	   }
	   
	   try {
		 cliente.setEmpresa(usuarioSessao.getEmpresa());
		 clienteService.salvar(cliente);
	} catch (Exception e) {
		return novo(cliente);
	}
	   
	attributes.addFlashAttribute("mensagem", "Cliente Cadastrado Com Sucesso!");   
	return new ModelAndView("redirect:/cliente/novo");
    }
   
   @GetMapping("/{id}")
   private ModelAndView editar(@PathVariable Long id) {
      Cliente cliente = clientes.findOne(id);
      
      ModelAndView mv = novo(cliente);
      mv.addObject(cliente);
      return mv;
   }
   
	@GetMapping
	 public ModelAndView pesquisar() {
		  ModelAndView mv = new ModelAndView("/cliente/pesquisaClientes");
		  mv.addObject("listaClientes", clientes.findAll());
		  
		  return mv;
	}
    
	
	@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Cliente> ListaClientes(String nome){
		validarTamanhoNome(nome);
		return clientes.findByNomeStartingWithIgnoreCase(nome); 
	}
	
	public void validarTamanhoNome(String nome){
	   if (StringUtils.isEmpty(nome) || nome.length() < 3) {
		   throw new IllegalArgumentException();
    	}	
	}

	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> IllegalArgumentException(IllegalArgumentException e ) {
		return ResponseEntity.badRequest().build();
	}	
}