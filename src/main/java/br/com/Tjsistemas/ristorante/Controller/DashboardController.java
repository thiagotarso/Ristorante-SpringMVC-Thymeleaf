package br.com.Tjsistemas.ristorante.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Clientes;
import br.com.Tjsistemas.ristorante.repository.Comandas;
import br.com.Tjsistemas.ristorante.repository.Produtos;

@Controller
public class DashboardController {

  @Autowired
  private Comandas comandas;
	
  @Autowired
  private Clientes clientes;
  
  @Autowired
  private Produtos produtos;
  
  @GetMapping("/")	
  public ModelAndView dashboard(){
	  ModelAndView mv = new ModelAndView("/layout/dashboard");
      
	  mv.addObject("totalComandaAnual", comandas.totalComandaAnual(empresaSessao()));
	  mv.addObject("totalComandaMesal", comandas.totalComandaMes(empresaSessao()));
	  mv.addObject("ticketMedioAnual", comandas.valorTicketMedioAno(empresaSessao()));
	  
	  mv.addObject("valorItensEstoque", produtos.valorItensEstoque());
	  mv.addObject("totalClientes", clientes.count());
	  
	  return  mv;
   }	
  
  private Long empresaSessao() {
		 Usuario usuarioSessaos = (Usuario)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 return  usuarioSessaos.getEmpresa();
  }
}