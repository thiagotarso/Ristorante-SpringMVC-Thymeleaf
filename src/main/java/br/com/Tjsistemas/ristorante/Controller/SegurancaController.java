package br.com.Tjsistemas.ristorante.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.Tjsistemas.ristorante.dto.EmpresaDTO;
import br.com.Tjsistemas.ristorante.model.EmpresaUsuario;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Usuarios;

@Controller
public class SegurancaController {

	@Autowired 
	private Usuarios usuarios;
	
	@GetMapping("/login")
	public String login(@AuthenticationPrincipal Usuario usuario){
		if (usuario != null) {
			return "redirect:/comanda/comanda";
		}
		return "login";
	}
	
	@GetMapping("/403")
	 public String acessoNegado() {
		return "403";
	}
	
	@RequestMapping(value="/empresas", consumes= {MediaType.APPLICATION_JSON_VALUE})
	private @ResponseBody ResponseEntity<?>  listaEmpresas(String userName){

		Optional<Usuario> usuario = usuarios.porNomeEAtivo(userName);
		
		if (usuario.isPresent()) {
    	List<EmpresaUsuario> empres = usuarios.BuscarEmpresaUsuario(usuario.get());
    	List<EmpresaDTO> empresasDto = new ArrayList<>();
    	
    	for (EmpresaUsuario emp : empres) {
			empresasDto.add(
					   new EmpresaDTO(emp.getEmpresa().getId(), emp.getEmpresa().getCodigo() ,emp.getEmpresa().getRazaoSocial()));
		 }
    	
    	return ResponseEntity.ok(empresasDto);         
		}
		
		return ResponseEntity.badRequest().body("Usuario não Localizado!");
	}
}