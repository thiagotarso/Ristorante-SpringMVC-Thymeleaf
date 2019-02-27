var Ristorante = Ristorante || {};

Ristorante.maskMoney = (function(){
	
	 function maskMoney(){
		 this.somenteNumeros = $('.js-somente-numeros');
		 this.porcentagem = $('.js-porcentagem');
		 this.decimal = $('.js-decimal');
		 this.plain = $('.js-plain');
	 }
	 maskMoney.prototype.enable = function(){
         this.somenteNumeros.mask('9#');
//		 this.decimal.maskMoney({decimal: ',', thousands: '.' });
//		 this.plain.maskMoney({precision: 0, thousands: '.'});
         this.porcentagem.maskNumber({decimal: '.'})
         this.decimal.maskNumber({decimal: ',', thousands: '.' });
 		 this.plain.maskNumber({integer: true, thousands: '.'});
         
	 }
	 
	 return maskMoney;
})();

numeral.language('pt-br');

Ristorante.formataMoeda = function(valorTotal) {
	numeral.language('pt-br');
	return numeral(valorTotal).format('0,0.00');
}

Ristorante.recuperaValor = function(valorFormatado){
	return numeral().unformat(valorFormatado);
}

//Ristorante.MaskDate = (function(){
//	   
//	   function MaskDate(){
//		   this.inputDate = $('.js-date');
//	   }
//	   
//	   MaskDate.prototype.enable = function(){
//      	this.inputDate.mask('00/00/0000');	
//      	this.inputDate.datepicker({
//      		orientation: 'button',
//      		language: 'pt-BR',
//      		autoclose: 'true'
//      	})
//	   }
//	   return MaskDate;
//	   
//})();

Ristorante.Security = (function(){
	
	function Security(){
		this.token = $('input[name=_crsf]').val();
		this.header = $('input[name=_crsf_header]').val();
	} 
	
	Security.prototype.enable = function(){
       $(document).ajaxSend(function(event, jqxhr, settings){
    	   jqxhr.setRequestHeader(this.header, this.token);
    	   
      }.bind(this));		
	}
	
	return Security;
})();

$(function(){ 
	 var maskMoney = new  Ristorante.maskMoney();
	 maskMoney.enable();
	 
//	 var maskDate =  new Ristorante.MaskDate();
//	 maskDate.enable();
	 
	 var security = new Ristorante.Security();
	 security.enable();
});