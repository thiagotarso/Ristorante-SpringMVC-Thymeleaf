package br.com.Tjsistemas.ristorante.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import br.com.Tjsistemas.ristorante.model.Usuario;

public class MyAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Usuario usuario;
   
	public MyAuthenticationToken(UsuarioSistema usuarioSistema, Collection<? extends GrantedAuthority> authorities) {
		super(usuarioSistema.getUsername() , usuarioSistema.getPassword(), authorities);
		this.usuario = usuarioSistema.getUsuario();
	}
	
	@Override
	public Usuario getPrincipal() {
		usuario.setSenha("");
		return this.usuario;
	}
}