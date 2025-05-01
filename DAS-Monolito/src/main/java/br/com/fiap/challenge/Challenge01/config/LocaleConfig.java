package br.com.fiap.challenge.Challenge01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.forLanguageTag("pt-BR"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    public String formatDate(java.time.LocalDate date, java.util.Locale locale) {
        if (date == null) {
            return "";
        }
        java.time.format.DateTimeFormatter formatter;
        if (locale != null && locale.getLanguage().equals(new java.util.Locale("pt").getLanguage())) {
            formatter = java.time.format.DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", locale);
        } else {
            formatter = java.time.format.DateTimeFormatter.ofPattern("MMMM d, yyyy", locale != null ? locale : java.util.Locale.ENGLISH);
        }
        return date.format(formatter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
