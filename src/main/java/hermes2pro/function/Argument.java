package hermes2pro.function;

import java.lang.annotation.Repeatable;

@Repeatable(value = Arguments.class)
public @interface Argument {
    String name();
    String description();
    String type();
}
