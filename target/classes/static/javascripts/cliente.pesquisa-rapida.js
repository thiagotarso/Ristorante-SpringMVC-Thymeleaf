var Ristorante = Ristorante || {};

Ristorante.PesquisaRapidaCliente = (function(){
	 
	function PesquisaRapidaCliente(){
		this.pesquisaRapidaClienteModal = $('#pesquisaRapidaCliente');
		this.nomeClienteModal = $("#nomeClienteModal");
		this.pesquisaRapidaBtn = $(".js-pesquisa-rapida-cliente-btn");
		this.containerTabelaCliente= $('#containerTabelaRapidaCliente');
		this.htmlTabelaPesquisaCliente = $('#tabela-pesquisa-rapida-cliente').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisaCliente);
		this.mensagemErro= $('.js-mensagem-erro');
	}
	
	PesquisaRapidaCliente.prototype.iniciar = function(){
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicada.bind(this));
		this.pesquisaRapidaClienteModal.on('shown.bs.modal', onModalShow.bind(this));
	}

	 function onModalShow() {
		   this.nomeClienteModal.focus();
	   }
	 
	 function onPesquisaRapidaClicada(event){
		 event.preventDefault();
		 $.ajax({
			 url: this.pesquisaRapidaClienteModal.find('form').attr('action'),
			 method: 'GET',
			 contentType: 'application/json',
			 data: {
				 nome: this.nomeClienteModal.val()
			 },
			success: onPesquisaConcluida.bind(this), 
		    error:   onErrorPesquisa.bind(this)
		 
		 });
		 
	 } 
	 
	 function onPesquisaConcluida(resultado){
		 this.mensagemErro.addClass('hidden');
		 
		 var html = this.template(resultado);
		 this.containerTabelaCliente.html(html);
		 
		 var tabelClientePesquisarapida = new Ristorante.TabelaClientePesquisaRapida(this.pesquisaRapidaClienteModal);
		 tabelClientePesquisarapida.iniciar();
	 }
	 
	 function onErrorPesquisa(){
		 this.mensagemErro.removeClass('hidden');
	 }
	 
	
	return PesquisaRapidaCliente;
})();

Ristorante.TabelaClientePesquisaRapida = (function(){
	
	function TabelaClientePesquisaRapida(modal){
		   this.modalCliente = modal;
	       this.cliente= $('.js-cliente-pesquisa-rapida');
	}
	
	TabelaClientePesquisaRapida.prototype.iniciar = function(){
		  this.cliente.on('click', onClienteSelecionado.bind(this));
	}
	
	function onClienteSelecionado(evento){
		this.modalCliente.modal('hide');

		var clienteSelecionado = $(evento.currentTarget);
     $('#nomeCliente').val(clienteSelecionado.data('cliente-nome')); 
     $('#idCliente').val(clienteSelecionado.data('id')); 
	}
	
	return TabelaClientePesquisaRapida;
	
})();

$(function(){ 
	 var ristorante = new  Ristorante.PesquisaRapidaCliente();
	 ristorante.iniciar();
});