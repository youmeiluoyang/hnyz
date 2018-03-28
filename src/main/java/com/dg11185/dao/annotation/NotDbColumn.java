package com.dg11185.dao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NoDbColumn indicates a field isn't mapped to a db table field.
 * when query,this field is filled will result,but when insert and update,
 * this field is ignored
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotDbColumn {

}
