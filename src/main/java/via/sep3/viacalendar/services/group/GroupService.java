package via.sep3.viacalendar.services.group;

import via.sep3.viacalendar.gRPC.Calendar;

public interface GroupService {
      Calendar.GroupProto create(Calendar.GroupProto payload);
      void update(Calendar.GroupProto payload);
      void delete(int id);
      Calendar.GroupProto getSingle(int id);
      Calendar.GroupProtoList getMany();
}
