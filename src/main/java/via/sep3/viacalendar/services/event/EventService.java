package via.sep3.viacalendar.services.event;

import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.gRPC.Calendar.EventProtoList;
@Service
public interface EventService {
    EventProto create(EventProto payload);
    void update(EventProto payload);
    void delete(int id);
    EventProto getSingle(int id);
    EventProtoList getMany();
}
