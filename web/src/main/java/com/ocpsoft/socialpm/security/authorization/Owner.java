package com.ocpsoft.socialpm.security.authorization;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.seam.security.annotations.SecurityBindingType;

/**
* @author <a href="mailto:bleathem@gmail.com">Brian Leathem</a>
* @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
*/
@SecurityBindingType
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
public @interface Owner {
}