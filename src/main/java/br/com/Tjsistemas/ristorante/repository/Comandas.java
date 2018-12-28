package br.com.Tjsistemas.ristorante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Tjsistemas.ristorante.model.Comanda;
import br.com.Tjsistemas.ristorante.repository.helper.comanda.ComandasQueries;

@Repository
public interface Comandas extends JpaRepository<Comanda, Long>, ComandasQueries {

	public List<Comanda> findByEmpresaOrderByIdAsc(Long CodigoEmpresa);

}
