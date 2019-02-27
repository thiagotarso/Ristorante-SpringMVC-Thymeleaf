var Ristorante = Ristorante || {};

Ristorante.CarregaMesas = (function(){
	
	function CarregaMesas(){
	} 
	
	CarregaMesas.prototype.iniciar = function(){
		tempoComandas();
		
		setInterval(tempoComandas, 1000); 
	}
	
	function onSituacaoMesas(event, hours, minutes){
		if (hours == 0) {
			
			if (minutes <= 15) {
				event.parent().addClass("success");}
		   else {
			   event.parent().addClass("primary");}
		 }
		   
		else{
			if (hours >= 2) {
				event.parent().addClass("danger");}
			 
			else if (hours == 1 && minutes >= 31 ) {
				event.parent().addClass("danger");
				}
				
			else if (hours == 1 && minutes <= 30) {
				event.parent().addClass("warning");
			  }
		 }
		
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
			
			onSituacaoMesas($(this) ,hours, minutes);
		})
	}
	
	return CarregaMesas;
})();

$(function(){ 
	 var ristorante = new  Ristorante.CarregaMesas();
	 ristorante.iniciar();
});