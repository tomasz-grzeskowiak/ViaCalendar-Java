package via.sep3.viacalendar.services.calendar;

import via.sep3.viacalendar.gRPC.Calendar;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProto;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProtoList;

public interface CalendarService {

    CalendarProto create(CalendarProto payload);
    void update(CalendarProto payload);
    void delete(int id);
    CalendarProto getSingle(int id);
    CalendarProtoList getMany();
}
