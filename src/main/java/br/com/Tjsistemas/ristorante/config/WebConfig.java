package br.com.Tjsistemas.ristorante.config;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;

import br.com.Tjsistemas.ristorante.Controller.ClienteController;
import br.com.Tjsistemas.ristorante.Controller.converter.CamareiroConverter;
import br.com.Tjsistemas.ristorante.Controller.converter.ClienteConverter;
import br.com.Tjsistemas.ristorante.Controller.converter.GrupoConverter;
import br.com.Tjsistemas.ristorante.Controller.converter.MesaConverter;
import br.com.Tjsistemas.ristorante.Controller.converter.ProdutoConverter;
import br.com.Tjsistemas.ristorante.session.TabelaItensSession;
import br.com.Tjsistemas.ristorante.thymeleaf.RistoranteDialect;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
@ComponentScan(basePackageClasses = {ClienteController.class, TabelaItensSession.class})
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableAsync
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	 @Bean
		public ViewResolver viewResolver() {
			ThymeleafViewResolver resolver = new ThymeleafViewResolver();
			resolver.setTemplateEngine(templateEngine());
			resolver.setCharacterEncoding("UTF-8");
			return resolver;
		}
	 
	 @Bean
	 public TemplateEngine templateEngine() {
			SpringTemplateEngine engine = new SpringTemplateEngine();
			engine.setEnableSpringELCompiler(true);
			engine.setTemplateResolver(templateResolver());
			
			engine.addDialect(new LayoutDialect()); // add layout
			engine.addDialect(new RistoranteDialect()); // tags
			engine.addDialect(new DataAttributeDialect());
			engine.addDialect(new SpringSecurityDialect());
			return engine;
		}
	 
	
	 private ITemplateResolver templateResolver() {
			SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
			resolver.setCharacterEncoding("UTF-8");
			resolver.setApplicationContext(applicationContext);
			resolver.setPrefix("classpath:/templates/");
			resolver.setSuffix(".html");
			resolver.setTemplateMode(TemplateMode.HTML);
			resolver.setCacheable(false); // desabilitando o cache somente para ambiente de testes
			return resolver;
	   }
	 
	 @Override
	 public void addResourceHandlers(ResourceHandlerRegistry registry) {
           registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	 }
	 
	 @Bean
		public FormattingConversionService mvcConversionService() {
			DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
			conversionService.addConverter(new ClienteConverter());
			conversionService.addConverter(new MesaConverter());
			conversionService.addConverter(new CamareiroConverter());
			conversionService.addConverter(new ProdutoConverter());
			conversionService.addConverter(new GrupoConverter());
			
			NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
			conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
			
			NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
			conversionService.addFormatterForFieldType(Integer.class, integerFormatter);
			
			// API de datas apartir java 8
			DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
			dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			dateTimeFormatter.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
			dateTimeFormatter.registerFormatters(conversionService);
			
			return conversionService;
		}
	 
	 @Bean
		public LocaleResolver localeResolver() {
			return new FixedLocaleResolver(new Locale("pt", "BR"));
		}
		
		@Bean
		public MessageSource messageSource(){
		  ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		  bundle.setBasename("classpath:/messages");
		  bundle.setDefaultEncoding("UTF-8");	
		  
		  return bundle;
		}
		
		// linha 54 vendaController.
	  @Bean
	  public DomainClassConverter<?> domainClassConverter(){
		  return new DomainClassConverter<FormattingConversionService>(mvcConversionService());
	  }
}
