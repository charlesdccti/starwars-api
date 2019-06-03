package br.com.starwars.api.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateBean implements RestTemplateCustomizer {

	@Override
	public void customize(RestTemplate restTemplate) {
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());		
	}
	
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, CustomClientHttpRequestInterceptor interceptors) {	
        return builder.interceptors(interceptors).build();
    }
}
