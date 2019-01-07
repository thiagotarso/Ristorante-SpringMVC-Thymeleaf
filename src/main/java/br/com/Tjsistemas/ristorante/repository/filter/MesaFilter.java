package br.com.Tjsistemas.ristorante.repository.filter;

import br.com.Tjsistemas.ristorante.model.SituacaoMesa;

public class MesaFilter {

    public Long codigo;
    public SituacaoMesa situacaoMesa;
    
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public SituacaoMesa getSituacaoMesa() {
		return situacaoMesa;
	}
	public void setSituacaoMesa(SituacaoMesa situacaoMesa) {
		this.situacaoMesa = situacaoMesa;
	}
    
    
}