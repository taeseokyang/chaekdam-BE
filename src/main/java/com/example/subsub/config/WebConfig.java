package com.example.subsub.config;
import com.example.subsub.utils.FilePath;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.socket.config.annotation.WebMvcStompEndpointRegistry;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("*")
                .maxAge(3600);
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//        registry.addResourceHandler("/upload/**")
//                .addResourceLocations("file:"+ FilePath.IMAGEPATH)
//                .setCachePeriod(60*10*6)
//                .resourceChain(true)
//                .addResolver(new PathResourceResolver());
//
//    }
}