Ristorante.Comanda = (function(){
	
	function Comanda(tabelaItens){
		this.tabelaItens = tabelaItens;
		this.valorTotalComanda = $('.js-valor-total-itens');
		
		this.containerTotais= $('.js-totais');
		this.totaisItens = $('.js-quantidade-itens');
		
		this.valorTotalItens = this.tabelaItens.valorTotal();
		this.totalItens= this.tabelaItens.totalItens();
		this.quantidadeItens = this.tabelaItens.quantidadeItens();
	}
	
	Comanda.prototype.iniciar = function(){
		this.tabelaItens.on('tabela-itens-atualizada', onTabelaItensAtualizada.bind(this));
		
		this.tabelaItens.on('tabela-itens-atualizada', onValoresAlterados.bind(this));
		onValoresAlterados.call(this);
	}	
	
	function onTabelaItensAtualizada(evento, total){
	    this.valorTotalItens = total.valorTotalItens == null ? 0 : total.valorTotalItens;
        this.totalItens = total.totalItens == null ? 0 : total.totalItens;
        this.quantidadeItens = total.quantidadeItens == null ? 0 : total.quantidadeItens;
	}
	
	function onValoresAlterados(){
		
		this.totaisItens.html( this.totalItens +"("+ this.quantidadeItens +")");
		
		var valorTotal =  numeral(this.valorTotalItens);
		this.valorTotalComanda.html('€' + Ristorante.formataMoeda(valorTotal));
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
