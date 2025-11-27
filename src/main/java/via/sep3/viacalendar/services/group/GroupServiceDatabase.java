package via.sep3.viacalendar.services.group;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar;
import via.sep3.viacalendar.model.Group;
import via.sep3.viacalendar.repositories.database.GroupRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceDatabase implements GroupService {
      private final GroupRepository groupRepository;
      public GroupServiceDatabase(GroupRepository groupRepository) {
            this.groupRepository = groupRepository;
      }
      @Transactional
      @Override
      public Calendar.GroupProto create(Calendar.GroupProto payload) {
            Group group = new Group();
            group.setName(payload.getName());
            Group created = groupRepository.save(group);
            return Calendar.GroupProto.newBuilder()
                .setId(created.getId())
                .setName(created.getName())
                .build();
      }
      @Transactional
      @Override
      public void update(Calendar.GroupProto payload) {
            Group toUpdate = groupRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
            toUpdate.setName(payload.getName());
            Group updated = groupRepository.save(toUpdate);
      }
      @Transactional
      @Override
      public void delete(int id) {
            groupRepository.deleteById(id);
      }
      
      @Override
      public Calendar.GroupProto getSingle(int id) {
            Optional<Group> fetched = groupRepository.findById(id);
            Group group = fetched.orElseThrow(() -> new RuntimeException("Group not found"));
            return Calendar.GroupProto.newBuilder()
                .setName(group.getName())
                .setId(group.getId())
                .build();
            
      }
      
      @Override
      public Calendar.GroupProtoList getMany() {
            List<Group> groups = groupRepository.findAll();
            Calendar.GroupProtoList.Builder builder = Calendar.GroupProtoList.newBuilder();
            for (Group group : groups) {
                  Calendar.GroupProto groupProto = Calendar.GroupProto.newBuilder()
                      .setId(group.getId())
                      .setName(group.getName())
                      .build();
                  builder.addGroups(groupProto);
            }
            return builder.build();
      }
}
