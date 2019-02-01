Ristorante.Comanda = (function(){
	
	function Comanda(tabelaItens){
		this.tabelaItens = tabelaItens;
		this.valorTotalitens = $('.js-valor-total-itens');
		this.valorTotalComanda = $('.js-valor-total-comanda');
		
		this.containerTotais= $('.js-totais');
		this.totaisItens = $('.js-quantidade-itens');
		this.valorDescontoInput = $('#inputDesconto');
		
		this.valorDesconto = this.valorDescontoInput.data('valor');
		this.valorTotalItens = this.tabelaItens.valorTotal();
		this.totalItens= this.tabelaItens.totalItens();
		this.quantidadeItens = this.tabelaItens.quantidadeItens();
	}
	
	Comanda.prototype.iniciar = function(){
		this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtualizada.bind(this));
		this.valorDescontoInput.on('change', onValorDescontoAlterado.bind(this));
		
		this.tabelaItens.on('tabela-itens-atualizada', onValoresAlterados.bind(this));
		this.valorDescontoInput.on('change', onValoresAlterados.bind(this));
		
		onValoresAlterados.call(this);
	}	
	
	function onTabelaItensAtualizada(evento, total){
	    this.valorTotalItens = total.valorTotalItens == null ? 0 : total.valorTotalItens;
        this.totalItens = total.totalItens == null ? 0 : total.totalItens;
        this.quantidadeItens = total.quantidadeItens == null ? 0 : total.quantidadeItens;
	}
	
	 function onValorDescontoAlterado(evento){
	    	this.valorDesconto= Ristorante.recuperaValor($(evento.target).val());
	    }
	
	function onValoresAlterados(){
		this.totaisItens.html( this.totalItens +"("+ this.quantidadeItens +")");
		this.valorTotalitens.html('€' + Ristorante.formataMoeda(this.valorTotalItens));
		
		if ( this.valorDesconto >= this.valorTotalItens){
			this.valorDescontoInput.val( Ristorante.formataMoeda(this.valorTotalItens));
			this.valorDesconto = Ristorante.formataMoeda(this.valorTotalItens);
		} 
		
		var valorTotal =  numeral(this.valorTotalItens) - numeral(this.valorDesconto);
		
		this.valorTotalComanda.html('€' + Ristorante.formataMoeda(valorTotal) );
	}
	
	return Comanda;
})();


$(function(){
     var pesquisaProdutos = new Ristorante.PesquisaProdutos();	
     pesquisaProdutos.iniciar();
    
     var tabelaItens = new Ristorante.TabelaItens(pesquisaProdutos);
     tabelaItens.iniciar();
     
     var comanda = new Ristorante.Comanda(tabelaItens);
     comanda.iniciar();
});
