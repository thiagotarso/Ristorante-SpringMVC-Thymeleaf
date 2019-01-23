package br.com.Tjsistemas.ristorante.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
      
	  mv.addObject("totalComandaAnual", comandas.totalComandaAnual());
	  mv.addObject("totalComandaMesal", comandas.totalComandaMesal());
	  mv.addObject("ticketMedioAnual", comandas.valorTicketMedioAno());
	  
	  mv.addObject("valorItensEstoque", produtos.valorItensEstoque());
	  mv.addObject("totalClientes", clientes.count());
	  
	  return  mv;
   }	
  
}
