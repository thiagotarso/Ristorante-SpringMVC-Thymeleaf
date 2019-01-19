var Ristorante = Ristorante || {};

Ristorante.CadastroProduto =(function(){
	
	function CadastroProduto(){
		this.precoCusto =$(".js-preco-custo");
		this.margemDeLucro = $(".js-margem-lucro");
		this.valorFinal =$(".js-valor-final");
	}
	
	CadastroProduto.prototype.iniciar = function (){
		this.margemDeLucro.on('keyup', onCalculaPorMargemDeLucro.bind(this));
		this.valorFinal.on('change', onCalculaValorFinal.bind(this));
	}
	
	function onCalculaPorMargemDeLucro(){
		var precoFinal = numeral(this.precoCusto.val() )  + (numeral(this.precoCusto.val()) * this.margemDeLucro.val()/100);
		this.valorFinal.val(Ristorante.formataMoeda(precoFinal));
	}
	
	function onCalculaValorFinal(){
		var percentual = ((numeral(this.valorFinal.val()) / numeral(this.precoCusto.val())) -1)* 100;
		
        if (this.valorFinal.val() == "" || this.valorFinal.val() == null){
        	this.margemDeLucro.val(0);
        }   else {
        this.margemDeLucro.val(percentual.toFixed(2));
	  }
	}
	return CadastroProduto;
})();

$(function(){
    var produto = new Ristorante.CadastroProduto;
    produto.iniciar();
});