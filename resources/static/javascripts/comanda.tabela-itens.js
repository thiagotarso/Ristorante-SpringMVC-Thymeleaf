Ristorante.TabelaItens = (function(){

	function TabelaItens(PesquisaProdutos){
	    this.tabelaprodutos = PesquisaProdutos;
	    this.produtoContainer = $('.js-tabela-produtos-container')
	    this.uuid = $('#uuid').val();
	    
	    this.emitter= $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	TabelaItens.prototype.iniciar = function(){
		this.tabelaprodutos.on('item-selecionado', onItemSelecionado.bind(this));
		
		bindTabelaItem.call(this);
	}
	
	TabelaItens.prototype.valorTotal = function(){
		return this.produtoContainer.data('valor');
	}
	
	TabelaItens.prototype.totalItens = function(){
		return this.produtoContainer.data('total-itens');
	}
	
	TabelaItens.prototype.quantidadeItens = function(){
		return this.produtoContainer.data('qtd-Itens');
	}
	
	function onItemSelecionado(event, idItem){
		
		var resposta = $.ajax({
			url: '/ristorante/comanda/item',
			method: 'POST',
			data:{
				idProduto: idItem ,
				uuid: this.uuid
			}
		});
		
		resposta.done(onItemAtualizadoNoServidor.bind(this));
	}
	
		function onItemAtualizadoNoServidor(html){
			this.produtoContainer.html(html);
			
			var quantidadeInput = $('.js-tabela-comanda-quantidade-item');
			quantidadeInput.on('change', onQuantidadeItemAlterado.bind(this));
//			quantidadeInput.maskNumber({integer: true, thousands: ''});
			quantidadeInput.mask('9#');
			
			var tabelaItem = bindTabelaItem.call(this);
			
		    this.emitter.trigger('tabela-itens-atualizada', {valorTotalItens: tabelaItem.data('valor-total'), totalItens: tabelaItem.data('total-itens'), quantidadeItens:tabelaItem.data('quantidade-Itens')});
		    
		}
		
		function onQuantidadeItemAlterado(evento){
			var input = $(evento.target);
			var quantidade = input.val();
			
			if(quantidade <= 0 ){
				input.val(1);
				quantidade = 1 
			}
				
			var idProduto =  input.data('id-produto');
			
			var resposta = $.ajax({
				url: '/ristorante/comanda/item/'+ idProduto,
				method: 'PUT',
				data:{
					quantidade: quantidade,
					uuid: this.uuid
				}
			});
			
			resposta.done(onItemAtualizadoNoServidor.bind(this));
		}
	
	function onRemoverItemComanda(evento){
		 evento.preventDefault(); // para com o comportamento padrao
	  	var idProduto=  $(evento.target).data('id-produto');
	
		var resposta = $.ajax({
			url: '/ristorante/comanda/item/'+ this.uuid +'/' + idProduto,
			method: 'DELETE'
		});
		
		resposta.done(onItemAtualizadoNoServidor.bind(this));
	 }
	
	function bindTabelaItem(){
		  var tabelaItens = $('.js-tabela-itens');
		  $('.js-remover-item-comanda-btn').on('click', onRemoverItemComanda.bind(this));
		  return tabelaItens;
	}

	return TabelaItens;
})();
