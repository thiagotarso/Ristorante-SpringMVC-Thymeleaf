package br.com.Tjsistemas.ristorante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Tjsistemas.ristorante.model.Cliente;
import br.com.Tjsistemas.ristorante.repository.helper.Clientes.ClientesQueries;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {

	public List<Cliente> findByNomeStartingWithIgnoreCase(String nome);
	
	public List<Cliente> findByEmpresaOrderByCodigoAsc(Long codigoEmpresa);

	public Cliente findByIdAndEmpresa(Long id, Long empresaSessao);

	public List<Cliente> findByNomeStartingWithIgnoreCaseAndEmpresa(String nome, Long empresaSessao);
}