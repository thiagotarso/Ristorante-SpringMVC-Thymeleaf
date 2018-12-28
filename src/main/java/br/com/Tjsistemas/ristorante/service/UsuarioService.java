package br.com.Tjsistemas.ristorante.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.Usuarios;

@Service
public class UsuarioService {

	
	@Autowired
	private Usuarios usuarios;
	
//	@PreAuthorize("#usuario.email == principal.usuario.email")
	@Transactional
	public void salvar(Usuario usuario){
		usuario.setCodigo(usuarios.codigoUsuario(usuario));
		
		usuarios.save(usuario);
	}
}
