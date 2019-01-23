var Ristorante = Ristorante || {};

Ristorante.GraficoComandasPorMes = ( function(){
    
	function GraficoComandasPorMes() {
		this.ctx = $('#GraficoComandas')[0].getContext('2d');
	}
	
	GraficoComandasPorMes.prototype.iniciar = function(){
		var graficoComandaPorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels: ['jan', 'fev', 'mar', 'abr' ],
		    	datasets:[{
                  label: 'Venda por Mês',
                  backgroundColor: 'rgba(26,179,148,0.5)',
                  pointBorderColor: 'rgba(26,179,148,1)',
                  pointBackgroundColor: '#fff',
                  data: ['100', '200', '300', '400']	  
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