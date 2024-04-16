package hermes2pro.function;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.RUNTIME )
public @interface Arguments {
    Argument[] value() default{};
}
