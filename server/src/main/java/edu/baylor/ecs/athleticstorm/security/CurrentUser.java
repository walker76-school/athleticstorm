/*
 * Filename: CurrentUser.java
 * Author: Andrew Walker
 * Date Last Modified: 1/30/2020
 */

package edu.baylor.ecs.athleticstorm.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * Current User annotation
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

}
