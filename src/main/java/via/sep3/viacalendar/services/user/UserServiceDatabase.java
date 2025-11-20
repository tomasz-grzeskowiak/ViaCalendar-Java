package via.sep3.viacalendar.services.user;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar;
import via.sep3.viacalendar.model.Event;
import via.sep3.viacalendar.model.User;
import via.sep3.viacalendar.repositories.database.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceDatabase implements UserService {
    private final UserRepository userRepository;
    public UserServiceDatabase(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Transactional
    @Override
    public Calendar.UserProto create(Calendar.UserProto payload) {
        User user = new User();

        user.setUsername(payload.getUsername());
        user.setPassword(payload.getPassword());
        user.setFirstName(payload.getFirstName());
        user.setLastName(payload.getLastName());
        user.setEmail(payload.getEmail());
        User created = userRepository.save(user);
        return Calendar.UserProto.newBuilder()
                .setId(created.getId())
                .setUsername(created.getUsername())
                .setPassword(created.getPassword())
                .setEmail(created.getEmail())
                .setFirstName(created.getFirstName())
                .setLastName(created.getLastName())
                .build();
    }

    @Transactional
    @Override
    public void update(Calendar.UserProto payload) {
        User toUpdate = userRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        toUpdate.setUsername(payload.getUsername());
        toUpdate.setPassword(payload.getPassword());
        toUpdate.setEmail(payload.getEmail());
        toUpdate.setFirstName(payload.getFirstName());
        toUpdate.setLastName(payload.getLastName());

        User updated = userRepository.save(toUpdate);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Calendar.UserProto getSingle(int id) {
        Optional<User> fetched = userRepository.findById(id);
        User user = fetched.orElseThrow(() -> new RuntimeException("Event not found"));
        return Calendar.UserProto.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .build();

    }

    @Override
    public Calendar.UserProtoList getMany() {
        List<User> users = userRepository.findAll();
        Calendar.UserProtoList.Builder builder = Calendar.UserProtoList.newBuilder();
        for (User user : users) {
            Calendar.UserProto userProto = Calendar.UserProto.newBuilder()
                    .setId(user.getId())
                    .setUsername(user.getUsername())
                    .setFirstName(user.getFirstName())
                    .setLastName(user.getLastName())
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword())
                    .build();
            builder.addUsers(userProto);
        }
        return builder.build();
    }
}
