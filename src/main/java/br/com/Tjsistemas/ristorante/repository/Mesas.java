package br.com.Tjsistemas.ristorante.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.Tjsistemas.ristorante.model.Mesa;
import br.com.Tjsistemas.ristorante.model.SituacaoMesa;
import br.com.Tjsistemas.ristorante.repository.helper.mesa.MesasQueries;

@Repository
public interface Mesas extends JpaRepository<Mesa, Long>, MesasQueries {

//	public List<Mesa> findAllByOrderByNumeroMesaAsc();
	public List<Mesa> findByEmpresaOrderByNumeroMesaAsc(Long CodigoEmpresa);
	
	public List<Mesa> findBySituacaoMesaOrderByNumeroMesaAsc(SituacaoMesa situacaoMesa); 
}
