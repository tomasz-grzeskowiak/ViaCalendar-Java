package via.sep3.viacalendar.startup;

import org.springframework.stereotype.Service;
import via.sep3.viacalendar.networking.handlers.ViaCalendarHandler;
import via.sep3.viacalendar.networking.handlers.EventHandler;
import via.sep3.viacalendar.services.event.EventService;
import via.sep3.viacalendar.services.event.EventServiceDatabase;
@Service
public class ServiceProvider {
        private EventHandler eventHandler;

        public EventService getCompanyService(){
            return GlobalContext.getBean(EventServiceDatabase.class);
        }
        public ViaCalendarHandler getEventHandler(){
            if (eventHandler == null) {
                eventHandler = new EventHandler(getCompanyService());
            }
            return eventHandler;
        }
}
