package via.sep3.viacalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import via.sep3.viacalendar.networking.ViaServer;

@SpringBootApplication(scanBasePackages = "via.sep3.viacalendar")
public class StartServer {

    private static final Logger log = LoggerFactory.getLogger(StartServer.class);

    public static void main(String[] args) {
        ApplicationContext context =SpringApplication.run(StartServer.class, args);
        ViaServer viaServer = context.getBean(ViaServer.class);
        viaServer.start();
        log.info("Calendar gRPC Server started");
    }
}
