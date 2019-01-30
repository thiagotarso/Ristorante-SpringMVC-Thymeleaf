var Ristorante = Ristorante || {};

Ristorante.GraficoComandasPorMes = ( function(){
    
	function GraficoComandasPorMes() {
		this.ctx = $('#GraficoComandas')[0].getContext('2d');
		this.url = $('.js-url-inicial');
	}
	
	GraficoComandasPorMes.prototype.iniciar = function(){
		$.ajax({
			url: this.url.data('url-inicial') + 'comanda/totalPorMes',
			method: 'GET',
			success: onDadosRecebidos.bind(this),
		})
	}
	
	function onDadosRecebidos(comandaMes){
	       var meses = [];
		   var valores = [];	
		   comandaMes.forEach(function(obj){
			   console.log(obj.mes);
			   
			  meses.unshift(obj.mes);
			  valores.unshift(obj.total);
		   });
		   
			var graficoComandaPorMes = new Chart(this.ctx, {
			    type: 'line',
			    data: {
			    	labels: meses,
			    	datasets:[{
	                  label: 'Comandas por Mês',
	                  backgroundColor: 'rgba(26,179,148,0.5)',
	                  pointBorderColor: 'rgba(26,179,148,1)',
	                  pointBackgroundColor: '#fff',
	                  data: valores	  
			    	}]
			    }
			});
	}
	
	return GraficoComandasPorMes;
})();

$(function(){
	var graficoComandasPorMes = new Ristorante.GraficoComandasPorMes();
	graficoComandasPorMes.iniciar();
});