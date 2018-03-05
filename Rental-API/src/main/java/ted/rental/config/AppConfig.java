package ted.rental.config;

import java.util.Arrays;

import javax.ws.rs.ext.RuntimeDelegate;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationInInterceptor;
import org.apache.cxf.jaxrs.validation.JAXRSBeanValidationOutInterceptor;
import org.apache.cxf.jaxrs.validation.ValidationExceptionMapper;
import org.apache.cxf.message.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import ted.rental.database.DataAccessObject;
import ted.rental.endpoints.AuthenticationEndpoint;
import ted.rental.endpoints.JaxRsApiApplication;
import ted.rental.endpoints.UserEndpoint;
import ted.rental.services.UserService;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Configuration
public class AppConfig {
    @Bean( destroyMethod = "shutdown" )

    public SpringBus cxf() {
        return new SpringBus();
    }

    @Bean @DependsOn( "cxf" )
    public Server jaxRsServer() {
        final JAXRSServerFactoryBean factory = RuntimeDelegate.getInstance().createEndpoint(
                jaxRsApiApplication(),
                JAXRSServerFactoryBean.class
        );

        factory.setServiceBeans(
                Arrays.< Object >asList(
                        userResource()
                )
        );

        factory.setAddress(
                factory.getAddress()
        );

        factory.setInInterceptors(
                Arrays.< Interceptor< ? extends Message > >asList(
                        new JAXRSBeanValidationInInterceptor()
                )
        );

        factory.setOutInterceptors(
                Arrays.< Interceptor< ? extends Message > >asList(
                        new JAXRSBeanValidationOutInterceptor()
                )
        );

        factory.setProviders(
                Arrays.asList(
                        new ValidationExceptionMapper(),
                        new JacksonJsonProvider()
                )
        );

        return factory.create();
    }

    @Bean
    public JaxRsApiApplication jaxRsApiApplication() {
        return new JaxRsApiApplication();
    }

    @Bean
    public UserEndpoint userResource() {
        return new UserEndpoint();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public AuthenticationEndpoint authentication() {
        return new AuthenticationEndpoint();
    }

}
