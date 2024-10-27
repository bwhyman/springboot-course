package org.example.springexamples.example03.aopadvanced;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface MyAuthority {
    MyAuthorityType[] value() default MyAuthorityType.USER;

    enum MyAuthorityType {
        USER, ADMIN, SUPER_ADMIN
    }
}
