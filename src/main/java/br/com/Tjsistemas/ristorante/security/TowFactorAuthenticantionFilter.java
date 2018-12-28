package br.com.Tjsistemas.ristorante.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class TowFactorAuthenticantionFilter extends UsernamePasswordAuthenticationFilter {

	private String extraParameter = "empresa";
	private String delimiter =":" ;
	
	protected String obtainUsername(HttpServletRequest request) {
	    String userName = request.getParameter(getUsernameParameter());
	    String extraInput = request.getParameter(getExtraParameter());
		
	    String combinedUserName = userName + delimiter + extraInput;
		return combinedUserName;
	}

	public String getExtraParameter() {
		return extraParameter;
	}

	public void setExtraParameter(String extraParameter) {
		this.extraParameter = extraParameter;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
}
