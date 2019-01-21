var Ristorante = Ristorante || {};

Ristorante.TabelaMesas = (function() {
	
   function TabelaMesas(){
	   this.produtoContainer = $('.js-tabela-produtos-container');
	   this.mesas = $('.js-mesas');
	   this.containerMesas = $('.js-tabela-mesas-container');
	   this.uuid = $('#uuid').val();
   }
   
   TabelaMesas.prototype.iniciar =  function(){
	   $("#example-getting-started").on('change', onMesaSelecionada.bind(this));
	   bindTabelaMesa.call(this);
   }
   
   function onMesaAtualizadaNoServidor(html){
		this.containerMesas.html(html);
		
		this.mesas = $('.js-mesa-selecionada');
		this.mesas.on('click', onRemoverMesaComanda.bind(this));
  }
   
   function onMesaSelecionada(){
	   var valorSelecionado = $(event.currentTarget);
		var res = $.ajax({
			url: this.produtoContainer.data('url') + '/mesa',
			method: 'POST',
			data:{
				idMesa: valorSelecionado.val(),
				uuid: this.uuid
			}
		});
		 
		onOptionaMesas(valorSelecionado.val(), 'ESCONDER');
		 res.done(onMesaAtualizadaNoServidor.bind(this));
	}
   
	function onRemoverMesaComanda(event){
		var mesaSelecionado = $(event.currentTarget);
		var idMesa = mesaSelecionado.data('id');
		
		var res = $.ajax({
			url: this.produtoContainer.data('url') + '/mesa/'+ this.uuid +'/' + idMesa,
			method: 'DELETE'
		});
		
		onOptionaMesas(idMesa, 'APARECER');
		res.done(onMesaAtualizadaNoServidor.bind(this));
	 }
   
//   ESCONDER Para retirar do optional a mesa selecionada
//   APARECER Para voltar ao optional a mesa selecionada
   function onOptionaMesas(idMesa, acaoMesas){
	 
	   this.lista =  $(".multiselect-container"); // ul lista de mesas                   ...  $('li:not(.multiselect-group) input' caso tenha duvidas.
	   this.lista.children("li").each(function() {
		   
		   var html = $(this).children("a");
		   var html2 =html.children("label");
		   var html3 = html2.children("input");

			   if(html3.val() == idMesa){
				  if(acaoMesas == 'APARECER') { 
				    $(this).removeClass("active")
				    $(this).removeClass("hidden");
				    
			      } 
				  else if(acaoMesas == 'ESCONDER'){
			    		$(this).addClass("hidden");
				    }
			      }
		});
  }
   
 function bindTabelaMesa(){
		  var tabelaItens = $('.js-tabela-mesas');
		  $('.js-remover-mesa-comanda-btn').on('click', onRemoverMesaComanda.bind(this));
		  return tabelaItens;
	}
   
   return TabelaMesas;
	
})();

$(function(){
    var tabelaMesas = new Ristorante.TabelaMesas();	
    tabelaMesas.iniciar();

});
