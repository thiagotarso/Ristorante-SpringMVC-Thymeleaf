package br.com.Tjsistemas.ristorante.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.Tjsistemas.ristorante.model.EmpresaUsuario;
import br.com.Tjsistemas.ristorante.model.Usuario;

@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
    private AppUserDetailsService userDetailService;
    
    @Autowired
	public PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication){

    	String login = (String) authentication.getPrincipal();
        String senha = (String) authentication.getCredentials();
        String empresa = "";
        
    	String[] split = login.split(":");
    	if(split.length < 2){
    		System.out.println("User did not enter both username and corporate domain.");
    		throw new UsernameNotFoundException("Must specify both username and corporate domain");
    	}

    	login = split[0];
    	empresa = split[1];
        
        // Excecao de usuario não autenticado
        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(senha)) {
            throw new AuthenticationServiceException("Erro na localizacao do LOGIN!!!");
        }
        //Aqui eu pego as informações do usuário pelo próprio spring security
        UserDetails usuarioDetails = userDetailService.loadUserByUsername(login);

        try{
             //autentico no sistema
            if( usuarioDetails != null && this.passwordEncoder.matches(senha, usuarioDetails.getPassword()) == true){
            	UsuarioSistema usuarioSistema = (UsuarioSistema) usuarioDetails;

            	if(confirmacaoAutorizacaoEmpresa(empresa, usuarioSistema.getUsuario()) == true){
            		usuarioSistema.getUsuario().setEmpresa(Long.parseLong(empresa));
            		
            		return new MyAuthenticationToken(usuarioSistema , usuarioSistema.getAuthorities());
            	}else{
	                  throw new AuthenticationServiceException("Empresa não Liberada!");
	              } 

            }else{
                throw new AuthenticationServiceException("Usuário não autenticado!");
            }

        }catch(AuthenticationServiceException e){
            throw e;
        }catch(Throwable e){
            throw new AuthenticationServiceException("Ocorreu um erro no ato da autenticação!", e);
        }
    }
    
    public boolean confirmacaoAutorizacaoEmpresa(String empresa, Usuario usuario ) {
        Long empresaSelecionada = Long.parseLong(empresa);
        boolean ativa = false;	
        
        for (EmpresaUsuario  empresaUsuario : usuario.getEmpresaUsuario()) {
	       	if(empresaUsuario.getEmpresa().getId() == empresaSelecionada) {
	       		ativa = true;
	       	  }
   	       }
         return ativa;   	
       }
    
    @Override
    public boolean supports(Class<? extends Object> authentication){
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication)
                && authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}