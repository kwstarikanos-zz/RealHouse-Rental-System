package ted.rental.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

/*
* http://stackoverflow.com/questions/38520881/using-name-binding-annotations-in-jersey
* Can be used on Classes-Interfaces and Methods
*/
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Secured {
    Role[] roles() default {};
    String owner() default "[unasgned]";
}
