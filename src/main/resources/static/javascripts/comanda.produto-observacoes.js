var Ristorante = Ristorante || {};

Ristorante.CadastroRapidoObservacoesProdutos = (function(){
	 
	function CadastroRapidoObservacoesProdutos(){
		   this.uuid =$("#uuid").val();
		   this.modal = $('#cadastroRapidoObservacoes');

		   this.idProduto = 0;
		   this.inputObservacao = $('#ObservacaoProduto'); 
		   
		   this.botaoSalvar = this.modal.find('.js-cadastro-model-Observacoes-btn');
		   this.form = this.modal.find('form');
		   this.url =  this.form.attr('action');
		   this.containerMensagemErro = $('.js-mensagem-cadastro-rapido-observacoes');
		   
	}
	
	CadastroRapidoObservacoesProdutos.prototype.iniciar = function(){
		 this.modal.on('shown.bs.modal', onModalShow.bind(this));
	     this.modal.on('hide.bs.modal', onModalClose.bind(this));
		 this.botaoSalvar.on('click', onSalvarClick.bind(this));
	}
	
	
	function onModalShow(event) {
		this.inputObservacao.focus();
		
		 var modal = $(event.relatedTarget);
		 this.idProduto = modal.data('id-Produto');
	
		 $.ajax({
			 url: this.url + "/obs",
			 method: "GET",
			 data: {
				 uuid: this.uuid,
				 idProduto: this.idProduto
			 },
//		   error: ontesteErro.bind(),
		   success: onObssSalvo.bind(this)
		})
	   }

    function onModalClose(){
		   this.inputObservacao.val('');
		   this.containerMensagemErro.addClass('hidden');
		   this.form.find('.form-group').removeClass('has-error');
	   }
    
    function onObssSalvo(msg){
		this.inputObservacao.val(msg);
	}
    
    
	  function onSalvarClick(){
		   var ObsProduto = this.inputObservacao.val().trim();
		   
		   $.ajax({
			    url: this.url  + "/observacoes",
				method: 'POST',
				data:{
					uuid: this.uuid,
					idProduto: this.idProduto,
					obsProduto: ObsProduto
				}, 		   
			
			     error: onErrorSalvandoObs.bind(this),
			     success: onObsSalvo.bind(this)
		   });
		   
	      }     
  
    function onErrorSalvandoObs(obj){
    	console.log("Error!");
    	this.containerMensagemErro.removeClass('hidden');
        this.containerMensagemErro.html('<span>' + obj.responseText + '</span>');
        this.form.find('.form-group').addClass('has-error');
    }
   
	function onObsSalvo(mensagem){
		var classeDescricaoProduto = $('.js-produto-'+ this.idProduto);
		
		if(this.inputObservacao.val().trim() == '' || this.inputObservacao.val().trim() == null){
			classeDescricaoProduto.removeClass('tj-produtos-observacoes');
		} 
		else{
			classeDescricaoProduto.addClass('tj-produtos-observacoes');
		}
		
	
		
		this.modal.modal('hide');
		}
  
  
	return CadastroRapidoObservacoesProdutos;
})();

$(function(){ 
	 var ristorante = new  Ristorante.CadastroRapidoObservacoesProdutos();
	 ristorante.iniciar();
});