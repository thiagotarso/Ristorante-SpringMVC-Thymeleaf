var Ristorante = Ristorante || {};


Ristorante.comandasPreparos = (function(){
	
	function comandasPreparos(tabelaItens){
		this.encerrarPreparo= $('.js-encerrar-preparo-btn'); 
	}
	
	comandasPreparos.prototype.iniciar = function(){
		this.encerrarPreparo.on('click', encerrandoComanda.bind(this));
	}	
	
	
	function encerrandoComanda(evento){
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = this.encerrarPreparo.data('url');
        var id = this.encerrarPreparo.data('id-comanda');		
		var codigo = this.encerrarPreparo.data('codigo');
		var camareiro = this.encerrarPreparo.data('camareiro');
		
		swal({
			title: 'Tem certeza?',
			text: 'Encerramento Comanda "' + botaoClicado.data('idComanda')  + '"? Você não poderá recuperar depois.',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, Encerre agora!',
			closeOnConfirm: false
		}, onEncerramentoConfirmado.bind(this, url,botaoClicado.data('idComanda'),botaoClicado.data('preparo')));
	}
		
    function onEncerramentoConfirmado(url,id, preparo){
		$.ajax({
			url: url,
			method: 'PUT',
			data:{
				idComanda:id,
				localPreparo: preparo
			},
		  success: onEncerramentoRealizado.bind(this),
		  error:  onErrorEnceramento.bind(this)
		});
	}
    
    function onEncerramentoRealizado(){
    	location.reload(true);
    }
    
    function onErrorEnceramento(){
    	swal('Oops', e.responseText,  'error');
    }
	
	return comandasPreparos;
})();

$(function(){
	var comandasPreparos = new Ristorante.comandasPreparos();
	comandasPreparos.iniciar();
	
});