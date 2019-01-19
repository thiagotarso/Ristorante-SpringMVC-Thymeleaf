var Ristorante = Ristorante || {};

Ristorante.PesquisaProdutos = (function(){
	
	function PesquisaProdutos(){
		this.formProdutos = $('#formProdutos');
		this.categoriaProdutos= $('.js-categoria-produtos');
        this.btnBuscarPorDescricao= $('.js-buscar-por-descricao');
		
		this.containerProdutoPesquisaRapida = $('#containerProdutoPesquisaRapida');
		
		var htmlProdutoPesquisaRapida = $('#produto-pesquisa-rapida').html();
		this.template = Handlebars.compile(htmlProdutoPesquisaRapida);
		
		this.emitter= $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	PesquisaProdutos.prototype.iniciar = function (){
		this.categoriaProdutos.on('click', onBuscaProdutos.bind(this));  
		this.btnBuscarPorDescricao.on('click', onBuscarPorDescricao.bind(this));
	}
	
	function onBuscarPorDescricao(event){
		event.preventDefault();
		var categoriaSelecionado = $(event.currentTarget);
		
		var decricaoProduto = $('.js-descricao').val();
		
		$.ajax({
			url: this.formProdutos.find('form').attr('action') +'/filtro?descricao=' + decricaoProduto,
		    method: 'GET',
		    contentType: 'application/json',
		    
			success: onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this)
		 });
	}
	
	function onBuscaProdutos(event){
		var categoriaSelecionado = $(event.currentTarget);
		
		$.ajax({
			url:  this.formProdutos.find('form').attr('action'),
			method: 'GET',
			contentType: 'application/json',
			data:{
				categoria: categoriaSelecionado.data('categoria')
			},
			success: onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this)
		});
	}
	
	function onPesquisaConcluida(resultado){
		
		for (i in resultado) {
			resultado[i].valor = Ristorante.formataMoeda(resultado[i].valor);	
		}
		
		var html = this.template(resultado);
		this.containerProdutoPesquisaRapida.html(html);
		
		this.produto = $('.js-produto-selecionado');
		this.produto.on('click', onProdutoSelecionado.bind(this));
		
	}
	
	function onProdutoSelecionado(event){
		var produtoSelecionado = $(event.currentTarget);
		
		this.emitter.trigger( 'item-selecionado', produtoSelecionado.data('id'));
	}
	
	function onErroPesquisa(){
		console.log(erro);
	}
	
	return PesquisaProdutos; 
})();