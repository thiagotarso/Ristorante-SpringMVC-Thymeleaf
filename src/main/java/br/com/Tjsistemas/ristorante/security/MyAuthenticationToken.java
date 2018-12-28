package br.com.Tjsistemas.ristorante.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.Tjsistemas.ristorante.model.Usuario;

public class MyAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Usuario usuario;
   
	public MyAuthenticationToken(UserDetails usuarioDetails, Collection<? extends GrantedAuthority> authorities, Usuario usuarioSessao) {
		super(usuarioDetails.getUsername() , usuarioDetails.getPassword(), authorities);
		this.usuario = usuarioSessao;
	}
	
	@Override
	public Usuario getPrincipal() {
		return this.usuario;
	}
}