package annotation01;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
//@Deprecated
public @interface SecondDefined {

    String desc() default "kitty.";
    int age() default 18;

}
