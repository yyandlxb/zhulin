package cn.hlvan;

import cn.hlvan.configure.SqlDateTimeFormatAnnotationFormatterFactory;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import org.jooq.Query;
import org.jooq.Table;
import org.jooq.TableRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

@SpringBootApplication(scanBasePackages = {"cn.hlvan"})
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAsync
@EnableScheduling
@EnableAutoConfiguration
public class Bootstrap implements WebMvcConfigurer, Jackson2ObjectMapperBuilderCustomizer {

    private HandlerInterceptor[] interceptors;

    private HandlerMethodArgumentResolver[] argumentResolvers;

    @Autowired
    public void setInterceptors(HandlerInterceptor[] interceptors) {
        this.interceptors = interceptors;
    }
    @Autowired
    public void setArgumentResolvers(HandlerMethodArgumentResolver[] argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Stream.of(interceptors).forEach(registry::addInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.addAll(Arrays.asList(this.argumentResolvers));
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new SqlDateTimeFormatAnnotationFormatterFactory());
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder.mixIn(Table.class, IgnoreType.class);
        jacksonObjectMapperBuilder.mixIn(Pageable.class, IgnoreType.class);
        jacksonObjectMapperBuilder.mixIn(Sort.class, IgnoreType.class);
        jacksonObjectMapperBuilder.mixIn(Query.class, IgnoreType.class);
    }

    @JsonIgnoreType
    private static class IgnoreType {
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(4);
        taskExecutor.setMaxPoolSize(16);
        taskExecutor.setQueueCapacity(500);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        return taskExecutor;
    }


    @Bean
    public  WebMvcConfigurer corsConfigurer(){

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET","POST")
                        .allowCredentials(true).maxAge(3600);
            }
        };
    }

    public static void main(String... args) {
        SpringApplication.run(Bootstrap.class, args);
    }

}
