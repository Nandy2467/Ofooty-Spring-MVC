package com.nandeep.ofooty.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.nandeep.ofooty.util.KeyFacts;
import com.nandeep.ofooty.util.LookUpMatchRequestUtil;
import com.nandeep.ofooty.util.LookUpTeamList;
import com.nandeep.ofooty.util.MatchRequestUtil;
import com.nandeep.ofooty.util.NextDate;
import com.nandeep.ofooty.util.ProfilePicPath;
import com.nandeep.ofooty.util.TeamList;

@Configuration
public class ConfigMVC extends WebMvcConfigurerAdapter {
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
	      .addResourceHandler("/propic/**")
	      .addResourceLocations("file:///C:/SpringPhoto/");
		
		 registry
		 .addResourceHandler("/resources/**")
		 .addResourceLocations("/resources/");
	}

	@Bean
	public TemplateResolver springResourceTemplateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setOrder(1);
		return resolver;
	}

	@Bean
	public SpringTemplateEngine getTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(springResourceTemplateResolver());
		return templateEngine;
	}

	@Bean
	public ViewResolver getThymeleafViewResolver() {
		ThymeleafViewResolver viewresolver = new ThymeleafViewResolver();
		viewresolver.setTemplateEngine(getTemplateEngine());
		viewresolver.setViewNames(new String[] { "thymeleaf/*" });
		return viewresolver;
	}
	
	
	// ------------------------- Spring MVC view resolver --------------------------

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver internalViewResolver = new InternalResourceViewResolver();
		internalViewResolver.setPrefix("/WEB-INF/views/");
		internalViewResolver.setViewNames("jsp/*");
		internalViewResolver.setSuffix(".jsp");
		internalViewResolver.setOrder(1);
		return internalViewResolver;
	}
	
	
	// ------spring filter to avoid xss attack  ---------------------
	
	@Bean
	public FilterRegistrationBean xssFilterBean(){
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(cleanFilter());
		frb.addUrlPatterns("/**");
		frb.setName("cleanFilter");
		frb.setOrder(1);
		return frb;
	}
	
	@Bean(name="cleanFilter")
	public XSSFilter cleanFilter(){
		return new XSSFilter();
	}
	
	// --------------------------- Beans required as utils in application--------------
	
	
	@Bean
	public KeyFacts keyfacts(){
		return new KeyFacts();
	}

	
	@Bean
	public NextDate nextDates(){
		return new NextDate();
	}

	//  ----  look up bean ( advanced concepts) -------------------------------
	
	@Bean
	@Scope(value="prototype")
	public TeamList teamList(){
		return new TeamList();
	}
	
	
	@Bean
	public LookUpTeamList lookUpTeamList(){
		return new LookUpTeamList() {
			
			@Override
			public TeamList createTeamList() {
				return new TeamList();
			}
		};
	}
	
	@Bean
	public ProfilePicPath propicpath(){
		return new ProfilePicPath();
	}
	
	@Bean
	@Scope(value="prototype")
	public MatchRequestUtil matchRequestUtil(){
		return new MatchRequestUtil();
	}
	
	@Bean
	public LookUpMatchRequestUtil lookUpMatchRequestUtil(){
		return new LookUpMatchRequestUtil() {
			
			@Override
			public MatchRequestUtil createMRUtil() {
				return matchRequestUtil();
			}
		};
	}
}
