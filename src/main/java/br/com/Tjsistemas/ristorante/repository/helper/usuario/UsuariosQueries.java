package br.com.Tjsistemas.ristorante.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import br.com.Tjsistemas.ristorante.model.EmpresaUsuario;
import br.com.Tjsistemas.ristorante.model.Usuario;

public interface UsuariosQueries {

	public Optional<Usuario> porNomeEAtivo(String userName);
	
	public List<String> permissoes(Usuario usuario);

	public List<EmpresaUsuario> BuscarEmpresaUsuario(Usuario usuario);

	public Long codigoUsuario(Usuario usuarioAutenticado);
}