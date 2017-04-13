import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Sebi on 29-Mar-17.
 */
public class StartRpcServer {

    public static void main(String[] args){
        ApplicationContext factory =
                new ClassPathXmlApplicationContext("classpath:server-spring.xml");
    }
}
