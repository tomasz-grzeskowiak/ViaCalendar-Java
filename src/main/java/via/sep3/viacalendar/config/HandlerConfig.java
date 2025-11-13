package via.sep3.viacalendar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;
import via.sep3.viacalendar.networking.handlers.ViaCalendarHandler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class HandlerConfig {

    @Bean
    public Map<HandlerTypeProto, ViaCalendarHandler> handlerMap(
            List<ViaCalendarHandler> handlers) {

        return handlers.stream()
                .collect(Collectors.toMap(ViaCalendarHandler::getType, Function.identity()));
    }
}