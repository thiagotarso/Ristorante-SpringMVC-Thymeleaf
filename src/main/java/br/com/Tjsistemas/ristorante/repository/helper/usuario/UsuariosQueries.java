package br.com.Tjsistemas.ristorante.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.Tjsistemas.ristorante.model.EmpresaUsuario;
import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	public Optional<Usuario> porNomeEAtivo(String userName);
	
	public List<String> permissoes(Usuario usuario);

	public List<EmpresaUsuario> BuscarEmpresaUsuario(Usuario usuario);

	public Long codigoUsuario(Long codigoEmpresa);
	
	public Page<Usuario> filtrar (UsuarioFilter usuarioFilter, Pageable pageable);
}