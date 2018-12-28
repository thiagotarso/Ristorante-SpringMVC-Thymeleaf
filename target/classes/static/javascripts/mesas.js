var Ristorante = Ristorante || {};

Ristorante.CarregaMesas = (function(){
	
	function CarregaMesas(){
	} 
	
	CarregaMesas.prototype.iniciar = function(){
		onSituacaoMesas();
		tempoComandas();
		
		setInterval(tempoComandas, 1000); // atualiza de 1 em 10 segundos
	}
	
	function onSituacaoMesas(){
		$('.mesas-small').each(function(){
			this.SituacaoMesas =  $(this).children('div.js-situacao-mesas');
			$(this).addClass(this.SituacaoMesas.text());
		})
		
	}
	
	function tempoComandas(){
		$('.tempo-comandas').each(function(){
			
			this.tempoStatico = $(this).children('div.tempo-static');
			this.tempoCalculado = $(this).children('div.tempo-calculado');
			
			var dataSalva = Date.parse(this.tempoStatico.text());
		
			var data = new Date();
			var dataAtual= Date.parse(data); // data atual em milesgundos
			
			var interval = (dataAtual - dataSalva); 
			
			// Set the unit values in milliseconds.
			var msecPerMinute = 1000 * 60;
			var msecPerHour = msecPerMinute * 60;
			var msecPerDay = msecPerHour * 24;
			
			// Calculate how many days the interval contains. Subtract that
			// many days from the interval to determine the remainder.
			var days = Math.floor(interval / msecPerDay );
			interval = interval - (days * msecPerDay );

			// Calculate the hours, minutes, and seconds.
			var hours = Math.floor(interval / msecPerHour );
			interval = interval - (hours * msecPerHour );

			var minutes = Math.floor(interval / msecPerMinute );
			interval = interval - (minutes * msecPerMinute );

			var seconds = Math.floor(interval / 1000 );
//			console.log(days + " days, " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds.");
			this.tempoCalculado.text(hours+ "H :" + minutes + "M :" + seconds +"S" );
		})
	}
	
	return CarregaMesas;
})();

$(function(){ 
	 var ristorante = new  Ristorante.CarregaMesas();
	 ristorante.iniciar();
});