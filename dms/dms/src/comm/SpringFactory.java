package comm;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.ArrayList;
import java.util.List;


public class SpringFactory {
    private static SpringFactory instance;
    private static BeanFactory factory;

    private ApplicationContext context=null;

    private SpringFactory() {
        context = new ClassPathXmlApplicationContext(new String[]
                {"conf/applicationContext.xml","classpath*:conf/spring-*.xml"});
        factory = (BeanFactory) context;

    }

    public ApplicationContext getContext(){
        return context;
    }

    public static synchronized BeanFactory getInstance() {
        if (instance == null) {
            instance = new SpringFactory();
        }
        return factory;
    }
}
