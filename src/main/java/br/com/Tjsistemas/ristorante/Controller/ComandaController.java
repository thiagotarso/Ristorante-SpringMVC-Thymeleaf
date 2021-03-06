package br.com.Tjsistemas.ristorante.Controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.Tjsistemas.ristorante.Controller.page.PageWrapper;
import br.com.Tjsistemas.ristorante.Controller.validator.ComandaValidator;
import br.com.Tjsistemas.ristorante.dto.ComandaMes;
import br.com.Tjsistemas.ristorante.dto.PreparoDTO;
import br.com.Tjsistemas.ristorante.model.Comanda;
import br.com.Tjsistemas.ristorante.model.ItemComanda;
import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.model.MesaComanda;
import br.com.Tjsistemas.ristorante.model.Produto;
import br.com.Tjsistemas.ristorante.model.SetorPreparo;
import br.com.Tjsistemas.ristorante.model.SituacaoMesa;
import br.com.Tjsistemas.ristorante.model.StatusComanda;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Camareiros;
import br.com.Tjsistemas.ristorante.repository.Categorias;
import br.com.Tjsistemas.ristorante.repository.Clientes;
import br.com.Tjsistemas.ristorante.repository.Comandas;
import br.com.Tjsistemas.ristorante.repository.Mesas;
import br.com.Tjsistemas.ristorante.repository.Produtos;
import br.com.Tjsistemas.ristorante.repository.filter.ComandaFilter;
import br.com.Tjsistemas.ristorante.service.ComandaService;
import br.com.Tjsistemas.ristorante.service.Exception.MesaOcupada;
import br.com.Tjsistemas.ristorante.service.Exception.MesaReservada;
import br.com.Tjsistemas.ristorante.session.TabelaItensSession;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/comanda")
public class ComandaController {

	@Autowired
	private ComandaService comandaService;
	
	@Autowired 
	private Comandas comandas;
	
	@Autowired
	private ComandaValidator comandaValidator;
	
	@Autowired
	private Produtos produtos;
	
	@Autowired
	private Clientes clientes;
	
	@Autowired
	private Categorias categorias;

	@Autowired
	private Mesas mesas;
	
	@Autowired
	private Camareiros camareiros;
	
	@Autowired
	private TabelaItensSession tabelaItensSession;
	
	@InitBinder("comanda")
	public void inicicalizarValidador(WebDataBinder dataBinder) throws SQLException, JRException {
		dataBinder.addValidators(comandaValidator);
	}
	
	@GetMapping("/visao")
	public ModelAndView VisaoComandas(Comanda comanda, @AuthenticationPrincipal Usuario userAltenticado){
		ModelAndView mv = new ModelAndView("/comanda/visaoComandas");

		List<Comanda> listaComandas = comandas.findByStatusAndEmpresaOrderByIdAsc(StatusComanda.EMITIDA ,userAltenticado.getEmpresa());
		
		for (Comanda comandaMesas : listaComandas) {
			comandaMesas.setMesasComanda(comandas.BuscarMesasComanda(comandaMesas));
		}

		mv.addObject("comandas", listaComandas);
		
		return mv;
	}
	
	@GetMapping("/nova")
	public ModelAndView nova(Comanda comanda){
		ModelAndView mv = new ModelAndView("/comanda/comanda");

		setComandaUuid(comanda);

		mv.addObject("mesas", mesas.findBySituacaoMesaAndEmpresaOrderByNumeroMesaAsc(SituacaoMesa.LIVRE, empresaSessao(comanda))); 
		
		mv.addObject("statusComanda", StatusComanda.values()); 
		mv.addObject("mesasSelecionada", comanda.getMesasComanda()); 
		mv.addObject("itens", comanda.getItens());
		
		mv.addObject("categorias", categorias.findByEmpresaOrderByCodigoAsc(empresaSessao(comanda)));
		mv.addObject("camareiros", camareiros.findByEmpresaOrderByCodigoAsc(empresaSessao(comanda)));
		
		mv.addObject("valorDesconto", comanda.getDesconto());
		mv.addObject("valorTotalItens", tabelaItensSession.getValorTotal(comanda.getUuid()));
		mv.addObject("totalItens", comanda.getItens().size());
		mv.addObject("quantidadeProd", tabelaItensSession.getQuantidadeItens(comanda.getUuid()));
		
		return mv;
	}

	
	@PostMapping(value ={"/novo", "{\\d+}"})
	public ModelAndView salvar(Comanda comanda, BindingResult bindingResult, Model model, RedirectAttributes attributes){
		
		validaComanda(comanda, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return nova(comanda);
		}
		
		try {
			comanda.setEmpresa(empresaSessao(comanda));
			
			tabelaItensSession.setControleAtendimento(comanda.getUuid());
			comandaService.salvar(comanda);
			
			attributes.addFlashAttribute("mensagem", "venda Salva com Sucesso!");
			
		}catch (MesaOcupada  e) {
			 bindingResult.rejectValue("mesa", e.getMessage(), e.getMessage());
		}catch(MesaReservada f){
			 bindingResult.rejectValue("mesa", f.getMessage(), f.getMessage());			
		}
		
		
		return new ModelAndView("redirect:/comanda/"+ comanda.getId());
	}
	
	@GetMapping
	 public ModelAndView pesquisar(ComandaFilter comandaFilter, BindingResult result,
			                          @PageableDefault(size=5) Pageable pageable, HttpServletRequest httpServletRequest ) {
		  ModelAndView mv = new ModelAndView("/comanda/pesquisaComanda");
		  
		  mv.addObject("mesa", mesas.findByEmpresaOrderByNumeroMesaAsc(empresaSessao(null)));
		  mv.addObject("camareiro", camareiros.findByEmpresaOrderByCodigoAsc(empresaSessao(null)));
		  mv.addObject("cliente", clientes.findByEmpresaOrderByCodigoAsc(empresaSessao(null)));
		  mv.addObject("produtos", produtos.findByEmpresaOrderByCodigoAsc(empresaSessao(null)));
		  mv.addObject("status", StatusComanda.values());
		  
		  comandaFilter.setEmpresa(empresaSessao(null));
		  PageWrapper<Comanda> paginasWrapper = new PageWrapper<>(comandas.filtrar(comandaFilter, pageable), httpServletRequest);
		  mv.addObject("pagina", paginasWrapper);
		  
		  return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView editar(@PathVariable Long id){
		Comanda comanda = comandas.findByIdAndEmpresa(id, empresaSessao(null)) ;
		
		comanda.setMesasComanda(comandas.BuscarMesasComanda(comanda));
		comanda.setItens(comandas.BuscarItensComanda(comanda));
		
	    setComandaUuid(comanda);
		
//	    adicionando Itens
	    for (ItemComanda item : comanda.getItens()) {
	    
			tabelaItensSession.adicionaItem(comanda.getUuid(), item.getProduto(), item.getQuantidade(),
					                         item.getQuantidadeAdicionada(), item.getControleAtendimento(), item.getObservacoes());
		}
	    
//	    adicionando mesas
	    for (MesaComanda mesa : comanda.getMesasComanda()) {
	    	tabelaItensSession.adicionaMesas(comanda.getUuid(), mesa.getMesa());
		}
		
		ModelAndView mv = nova(comanda);
		mv.addObject(comanda);
		return mv;
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long idProduto, String uuid){
		Produto produto = produtos.findByIdAndEmpresa(idProduto, empresaSessao(null));
		tabelaItensSession.adicionaItem(uuid ,produto, 1, 1, LocalDateTime.now(), null);
        return mvTabelaItensComanda(uuid);
	}
	
	@PutMapping("/item/{idProduto}")
	public ModelAndView alterarQuandidadeDeItem(@PathVariable Long idProduto, Integer quantidade, String uuid) {
		Produto produto = produtos.findByIdAndEmpresa(idProduto, empresaSessao(null));
		tabelaItensSession.alterarQuantidadeItens(uuid ,produto, quantidade);
	
		return mvTabelaItensComanda(uuid);
	}
	
	@DeleteMapping("item/{uuid}/{idProduto}")
	public ModelAndView removerItem(@PathVariable Long idProduto, @PathVariable String uuid){
		Produto produto = produtos.findByIdAndEmpresa(idProduto, empresaSessao(null));
		tabelaItensSession.removerItemComanda(uuid ,produto);
		return mvTabelaItensComanda(uuid);
	}
	
	@PostMapping("/mesa")
	public ModelAndView adicionarMesa(Long idMesa, String uuid){
		Mesa mesa = mesas.findByIdAndEmpresa(idMesa, empresaSessao(null));
		tabelaItensSession.adicionaMesas(uuid, mesa);
        return mvTabelaMesasComanda(uuid);
	}
	
	@DeleteMapping("mesa/{uuid}/{idMesa}")
	public ModelAndView removerMesa(@PathVariable Long idMesa, @PathVariable String uuid){
		Mesa mesa = mesas.findOne(idMesa);
		tabelaItensSession.removerMesaComanda(uuid ,mesa);
		return mvTabelaMesasComanda(uuid);
	}
	
	@GetMapping("/preparo")
	 public ModelAndView listaPreparo(ComandaFilter comandaFilter, BindingResult result) {
		  ModelAndView mv = new ModelAndView("/comanda/comandasPreparo");
		  
		  mv.addObject("setorPreparo", SetorPreparo.values()); 
		  comandaFilter.setEmpresa(empresaSessao(null));
		  
		  List<PreparoDTO> preparo = comandas.filtrarPreparo(empresaSessao(null), comandaFilter.getSetorPreparo()); 
		  mv.addObject("comandasPreparo", preparo);
		  return mv;
	}
	
	@PutMapping("/encerramentoComanda")
	 public @ResponseBody ResponseEntity<?> encerramentoPreparo(Long idComanda,String localPreparo) {
	      Comanda comanda = comandas.findByIdAndEmpresa(idComanda, empresaSessao(null)) ;
		  comanda.setItens(comandas.BuscarItensComanda(comanda));
		  for (ItemComanda item : comanda.getItens()) {
			    if (item.getSetorPreparo().toString().equals(localPreparo)) {
			    	item.setQuantidadeAdicionada(0);
			    	item.setControleAtendimento(null);
				}
		      }
		  
		  comandaService.salvar(comanda);
		  return ResponseEntity.ok().build();
	}
	
	public ModelAndView mvTabelaMesasComanda(String uuid) {
		ModelAndView mv = new ModelAndView("comanda/tabelaMesas");
		mv.addObject("mesasSelecionada", tabelaItensSession.getMesas(uuid)); 
		return mv;
	}
	
	@PostMapping("/observacoes")
	public @ResponseBody ResponseEntity<?> adicionarObservacoes(String uuid, Long idProduto, String obsProduto){
		Produto produto = produtos.findOne(idProduto);
		tabelaItensSession.adicionaItem(uuid, produto, 0, 0, null, obsProduto); 
		
		return ResponseEntity.ok("Observação salva Com sucesso!");
	}
	
	@GetMapping("/buscarObservacoes")
	public @ResponseBody ResponseEntity<?> buscarObservacoes(String uuid, Long idProduto){

	 Produto produto = produtos.findOne(idProduto);
		
	  ItemComanda item =  tabelaItensSession.buscarObservacoesItens(uuid, produto);
	  
	  return  ResponseEntity.ok(item.getObservacoes());
	}
	
	@GetMapping("/totalPorMes")
	public @ResponseBody List<ComandaMes> listarTotalVendaPorMes(){
		return comandas.totalPorMes(empresaSessao(null));
	}
	
	public ModelAndView mvTabelaItensComanda(String uuid) {
		ModelAndView mv = new ModelAndView("comanda/tabelaItensComanda");
		mv.addObject("itens", tabelaItensSession.getItens(uuid)); 
	    mv.addObject("valorTotal", tabelaItensSession.getValorTotal(uuid)); 
	    
	    mv.addObject("totalItens", tabelaItensSession.getItens(uuid).size());
		mv.addObject("quantidadeProd", tabelaItensSession.getQuantidadeItens(uuid));
		return mv;
	}

	
	private void setComandaUuid(Comanda comanda){
		if(StringUtils.isEmpty(comanda.getUuid())){
			comanda.setUuid(UUID.randomUUID().toString());
		}
	}
	
	public void validaComanda(Comanda comanda, BindingResult bindingResult) {
		comanda.adicionarMesas(tabelaItensSession.getMesas(comanda.getUuid()));
		comanda.adicionarItens(tabelaItensSession.getItens(comanda.getUuid()));
		comanda.calcularValorTotal();
		
		comandaValidator.validate(comanda, bindingResult);
	}
	
	private Long empresaSessao(Comanda comanda) {
		 Usuario usuarioSessaos = (Usuario)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 
		 if (comanda !=  null) {
		 	return  comanda.getEmpresa() != null ? comanda.getEmpresa() :usuarioSessaos.getEmpresa();
		 }else {
			 return  usuarioSessaos.getEmpresa();
		 }
	}
}