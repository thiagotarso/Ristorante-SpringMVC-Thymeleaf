package br.com.Tjsistemas.ristorante.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.Tjsistemas.ristorante.service.ClienteService;

@Configuration
@ComponentScan(basePackageClasses = ClienteService.class)
public class ServiceConfig {
}
