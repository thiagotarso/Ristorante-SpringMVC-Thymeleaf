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

Ristorante.Security = (function(){
	
	function Security(){
		this.token = $('input[name=_crsf]').val();
		this.header = $('input[name=_crsf_header]').val();
	} 
	
	Security.prototype.enable = function(){
//		 toda vez que o ajax for chamado ele inseri o token crsf
       $(document).ajaxSend(function(event, jqxhr, settings){
    	   jqxhr.setRequestHeader(this.header, this.token);
    	   
      }.bind(this));		
	}
	
	return Security;
})();

$(function(){ 
	 var maskMoney = new  Ristorante.maskMoney();
	 maskMoney.enable();
	 
	 var security = new Ristorante.Security();
	 security.enable();
});