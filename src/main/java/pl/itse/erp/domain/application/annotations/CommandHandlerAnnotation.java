package pl.itse.erp.domain.application.annotations;

import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Borys on 2017-04-18.
 */
@Component
@Transactional
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandHandlerAnnotation {
}
