package pl.itse.erp.cqrs.command.provider.handler;

import java.util.HashMap;
import java.util.Map;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import pl.itse.erp.cqrs.command.annotations.CommandHandlerProviderAnnotation;
import pl.itse.erp.cqrs.command.handler.CommandHandler;

/**
 * Created by Borys on 2017-04-11.
 */
@CommandHandlerProviderAnnotation
public class CommandHandlerProviderImpl implements CommandHandlerProvider, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    private Map<Class<?>, String> handlers = new HashMap<Class<?>, String>();

    @SuppressWarnings("unchecked")
    public CommandHandler<Object, Object> getHandler(Object command) {

        String beanName = handlers.get(command.getClass());
        if (beanName == null) {
            throw new RuntimeException("... handler not found. Class is " + command.getClass());
        }

        CommandHandler<Object, Object> handler = beanFactory.getBean(beanName, CommandHandler.class);
        return handler;
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {

        handlers.clear();
        String[] commandHandlersNames = beanFactory.getBeanNamesForType(CommandHandler.class);
        for (String beanName : commandHandlersNames) {
            BeanDefinition commandHandler = beanFactory.getBeanDefinition(beanName);
            try {
                Class<?> handlerClass = Class.forName(commandHandler.getBeanClassName());
                handlers.put(getHandledCommandType(handlerClass), beanName);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Class<?> getHandledCommandType(Class<?> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        ParameterizedType type = findByRawType(genericInterfaces, CommandHandler.class);
        return (Class<?>) type.getActualTypeArguments()[0];
    }

    private ParameterizedType findByRawType(Type[] genericInterfaces, Class<?> expectedRawType) {
        for (Type type : genericInterfaces) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parametrized = (ParameterizedType) type;
                if (expectedRawType.equals(parametrized.getRawType())) {
                    return parametrized;
                }
            }
        }
        throw new RuntimeException();
    }

}
