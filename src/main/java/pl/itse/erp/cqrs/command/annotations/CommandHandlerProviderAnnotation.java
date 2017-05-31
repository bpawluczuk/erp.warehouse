package pl.itse.erp.cqrs.command.annotations;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Borys on 2017-04-18.
 */
@Service
@Transactional
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandHandlerProviderAnnotation {
}
