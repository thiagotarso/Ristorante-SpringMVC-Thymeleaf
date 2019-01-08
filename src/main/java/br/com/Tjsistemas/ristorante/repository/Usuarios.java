package br.com.Tjsistemas.ristorante.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Tjsistemas.ristorante.model.Usuario;
import br.com.Tjsistemas.ristorante.repository.helper.usuario.UsuariosQueries;

@Repository
public interface Usuarios extends  JpaRepository<Usuario, Long>, UsuariosQueries {
	
	public Optional<Usuario> findByEmail(String email);
	public List<Usuario> findByEmpresa(Long codigoEmpresa);
    public Usuario findByIdAndEmpresa(Long id, Long empresa);	
}