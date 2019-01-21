var Ristorante = Ristorante || {};

Ristorante.Login = (function(){
	
	function Login(){
		this.limpaCookie = $('.js-limpaCookies');
		this.inputUsuario = $('.js-usuario');
		this.selectEmpresas = $('#empresas');
		
	    this.v2 = parseInt(document.documentElement.clientHeight);
	    this.v1 = parseInt(document.documentElement.clientWidth);
	} 
	
	Login.prototype.iniciar = function(){
	  document.body.style.backgroundImage = "url('https://unsplash.it/g/"+this.v1+"/"+this.v2+"/?random')";	 
		
	  this.inputUsuario.focus();
	  this.inputUsuario.val(getCookie('usuario'));
	  
	  this.limpaCookie.on('click', deleteCookie.bind(this));
      this.inputUsuario.on('blur', onEmpresas.bind(this));
	}
	
	//deletando cookies
	function deleteCookie() {
	    var testCookie = 'testCookie';
		
	       if (getCookie(testCookie)) {
	              document.cookie = testCookie + "=" + "; expires=Thu, 01-Jan-70 00:00:01 GMT";
	       }
	}
	
	function getCookie(name) {
	    var cookies = document.cookie;
	    var prefix = name + "=";
	    var begin = cookies.indexOf("; " + prefix);
	 
	    if (begin == -1) {
	 
	        begin = cookies.indexOf(prefix);
	         
	        if (begin != 0) {
	            return null;
	        }
	 
	    } else {
	        begin += 2;
	    }
	 
	    var end = cookies.indexOf(";", begin);
	     
	    if (end == -1) {
	        end = cookies.length;                        
	    }
	 
	    return unescape(cookies.substring(begin + prefix.length, end));
	}
	
	function onEmpresas(){
		
        	$.ajax({
				url: this.inputUsuario.data('url') + 'empresas',
				method: 'GET',
				contentType: 'application/json',
				data:{
					userName: this.inputUsuario.val()
				},
				success: onPesquisaConcluida.bind(this),
				error: onErroPesquisa.bind(this)
		});
	}
	
	function onPesquisaConcluida(resultado){
		this.selectEmpresas.removeAttr('disabled');
		this.selectEmpresas.empty()
		
		for (i in resultado) {
			this.selectEmpresas.append('<option value='+ resultado[i].id +'>'+resultado[i].codigo + ' '+ resultado[i].razaoSocial +'</option>');
			this.selectEmpresas.val(resultado[i].id);
		}
	}
	
	function onErroPesquisa(){
		this.selectEmpresas.attr('disabled', true);
		this.selectEmpresas.empty();
		
		this.selectEmpresas.append('<option value= 0> Empresas </option>');
	}
	
	return Login;
})();

$(function(){ 
	 var ristorante = new  Ristorante.Login();
	 ristorante.iniciar();
});