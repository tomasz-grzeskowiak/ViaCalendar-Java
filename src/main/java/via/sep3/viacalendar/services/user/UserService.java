package via.sep3.viacalendar.services.user;

import via.sep3.viacalendar.gRPC.Calendar.UserProto;
import via.sep3.viacalendar.gRPC.Calendar.UserProtoList;

public interface UserService {
    UserProto create(UserProto payload);
    void update(UserProto payload);
    void delete(int id);
    UserProto getSingle(int id);
    UserProtoList getMany();
}
