package br.com.Tjsistemas.ristorante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Tjsistemas.ristorante.model.Cliente;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long> {

	public List<Cliente> findByNomeStartingWithIgnoreCase(String nome);
	
	public List<Cliente> findByEmpresaOrderByCodigoAsc(Long codigoEmpresa);
}