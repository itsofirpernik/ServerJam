package com.perniktv.serverjam.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CommandHandler {
    String name();
    String[] aliases() default {};
    String[] parent() default {};
    String description() default "";
    String usage() default "";
    String permission() default "";
    String noPermissionMessage() default "";
}
