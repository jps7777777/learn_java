package annotation01;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target(value = { FIELD, METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestColumn {

    String value() default "qy_";

}
