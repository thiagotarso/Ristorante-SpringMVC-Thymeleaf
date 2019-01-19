package br.com.Tjsistemas.ristorante.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.com.Tjsistemas.ristorante.config.JPAConfig;
import br.com.Tjsistemas.ristorante.config.SecurityConfig;
import br.com.Tjsistemas.ristorante.config.ServiceConfig;
import br.com.Tjsistemas.ristorante.config.WebConfig;

//@EnableTransactionManagement
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { JPAConfig.class ,ServiceConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
    
	@Override
	protected Filter[] getServletFilters() {
		HttpPutFormContentFilter httpPutFormContentFilter = new HttpPutFormContentFilter(); 
		return new Filter[] {httpPutFormContentFilter};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
      registration.setMultipartConfig(new MultipartConfigElement("")); 
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.setInitParameter("spring.profiles.default", "local");
	}
}