package pl.sda.partyka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.partyka.domain.Role;
import pl.sda.partyka.domain.User;
import pl.sda.partyka.dto.UserCreateRequest;
import pl.sda.partyka.repository.UserRepository;
import pl.sda.partyka.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final RoleService roleService;

    public void addUser(UserCreateRequest userToAdd){
        List<Role> roles = userToAdd.getRoles();
        roles.add(roleService.getRoleByName(Utils.DEFAULT_ROLE));
        User user = User.builder()
                .login(userToAdd.getLogin())
                .password(userToAdd.getPassword())
                .displayName(userToAdd.getDisplauName())
                .roles(roles)
                .createdEvents(new ArrayList<>())
                .eventsSignedTo(new ArrayList<>())
                .createdComments(new ArrayList<>())
                .build();
        userRepo.save(user);
    }
}
