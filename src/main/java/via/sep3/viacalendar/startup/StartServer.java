package via.sep3.viacalendar.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import via.sep3.viacalendar.networking.ViaServer;

@EnableJpaRepositories("via.sep3.viacalendar.repositories.database")
@EntityScan("via.sep3.viacalendar.model")
@SpringBootApplication(scanBasePackages = "via.sep3.viacalendar")
public class StartServer {

    public static void main(String[] args) {
        GlobalContext.setContext(SpringApplication.run(StartServer.class, args));
        ViaServer viaServer = GlobalContext.getContext().getBean(ViaServer.class);
        viaServer.start();
        System.out.println("Calendar gRPC Server started");
    }
}
