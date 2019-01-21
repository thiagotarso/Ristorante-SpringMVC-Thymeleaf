package br.com.Tjsistemas.ristorante.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

import br.com.Tjsistemas.ristorante.Controller.page.PageWrapper;
import br.com.Tjsistemas.ristorante.model.Cliente;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Clientes;
import br.com.Tjsistemas.ristorante.repository.filter.ClienteFilter;
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

   @PostMapping(value ={"/novo", "{\\d+}"})
   public ModelAndView salvar(@Valid Cliente cliente, BindingResult bindingResult ,
		                             Model model, RedirectAttributes attributes) {
	
	   if (bindingResult.hasErrors()) {
		 return novo(cliente);
	   }
	   
	   try {
		  cliente.setEmpresa(empresaSessao(cliente));
		 clienteService.salvar(cliente);
	} catch (Exception e) {
		return novo(cliente);
	}
	   
	attributes.addFlashAttribute("mensagem", "Cliente Cadastrado Com Sucesso!");   
	return new ModelAndView("redirect:/cliente/novo");
    }
   
	@GetMapping
	 public ModelAndView pesquisar(ClienteFilter clientefilter, BindingResult result,
			                          @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest ) {
		  ModelAndView mv = new ModelAndView("/cliente/pesquisaClientes");
		  
		  clientefilter.setEmpresa(empresaSessao(null));
		  PageWrapper<Cliente> paginasWrapper = new PageWrapper<>(clientes.filtrar(clientefilter, pageable), httpServletRequest);
		  mv.addObject("pagina", paginasWrapper);
		  
		  return mv;
	}
   
   @GetMapping("/{id}")
   private ModelAndView editar(@PathVariable Long id) {
      Cliente cliente = clientes.findByIdAndEmpresa(id, empresaSessao(null));
      
      ModelAndView mv = novo(cliente);
      mv.addObject(cliente);
      return mv;
   }
   
	@RequestMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody List<Cliente> ListaClientes(String nome, boolean fornecedor){
		validarTamanhoNome(nome);
		return clientes.findByNomeStartingWithIgnoreCaseAndEmpresaAndFornecerdor(nome, empresaSessao(null), fornecedor); 
	}
	
	public void validarTamanhoNome(String nome){
	   if (StringUtils.isEmpty(nome) || nome.length() < 1) {
		   throw new IllegalArgumentException();
    	}	
	}

	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> IllegalArgumentException(IllegalArgumentException e ) {
		return ResponseEntity.badRequest().build();
	}	

	private Long empresaSessao(Cliente  cliente) {
		 Usuario usuarioSessaos = (Usuario)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 if (cliente !=  null) {
			return  cliente.getEmpresa() != null ? cliente.getEmpresa() :usuarioSessaos.getEmpresa();
		 }else {
			 return usuarioSessaos.getEmpresa();
		 }
	}
}