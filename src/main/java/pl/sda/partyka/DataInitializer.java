package pl.sda.partyka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.sda.partyka.domain.Role;
import pl.sda.partyka.domain.User;
import pl.sda.partyka.dto.EventCreateRequest;
import pl.sda.partyka.dto.UserCreateRequest;
import pl.sda.partyka.service.EventService;
import pl.sda.partyka.service.RoleService;
import pl.sda.partyka.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static pl.sda.partyka.utils.Utils.DEVELOP_PROFILE;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final RoleService roleService;
    private final UserService userService;
    private final EventService eventService;

    @Value("${running.profile}")
    private String runningProfile;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (runningProfile.equals(DEVELOP_PROFILE)) {
            Role organizer = roleService.addRole(new Role("ORGANIZER"));
            roleService.addRole(new Role("USER"));
            UserCreateRequest userCreateRequest = new UserCreateRequest();
            userCreateRequest.setLogin("asd@asd.pl");
            userCreateRequest.setPassword("12345678");
            userCreateRequest.setRepeatedPassword("12345678");
            userCreateRequest.setDisplauName("ASD");
            List<Role> roles = new ArrayList<>();
            roles.add(organizer);
            userCreateRequest.setRoles(roles);
            User user = userService.addUser(userCreateRequest);
            LocalDate now = LocalDate.now();
            for (int i=0; i<60;i++) {
                EventCreateRequest eventCreateRequest = new EventCreateRequest();
                eventCreateRequest.setTitle("Event" + i);
                if (i<41) {
                    eventCreateRequest.setStartingDate(now.toString());
                    eventCreateRequest.setEndingDate(now.plusDays(1).toString());
                } else {
                    eventCreateRequest.setStartingDate(now.plusDays(1).toString());
                    eventCreateRequest.setEndingDate(now.plusDays(1).toString());
                }
                eventCreateRequest.setDescription("This event was added for test purposes only !!");
                eventService.addEvent(eventCreateRequest, user);
            }
        }
    }
}
