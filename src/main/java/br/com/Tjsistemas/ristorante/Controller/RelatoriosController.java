package br.com.Tjsistemas.ristorante.Controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.service.RelatoriosService;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/relatorios")
public class RelatoriosController {

	@Autowired
	private RelatoriosService relatoriosService;
	
	@PostMapping("/cupom/{id}")
	public ResponseEntity<byte[]> relatorioComandaCupom(@PathVariable Long id , Long empresa) throws SQLException, JRException {
       byte[] relatorio = relatoriosService.relatorioComandaCupom(empresaSessao(), id);

       return ResponseEntity.ok()
    		   .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
    		   .body(relatorio);
	}
	
	private Long empresaSessao(){
		 Usuario usuarioSessaos = (Usuario)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				 return  usuarioSessaos.getEmpresa();
		}	
    }
