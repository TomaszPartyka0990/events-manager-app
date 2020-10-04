package pl.sda.partyka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.sda.partyka.domain.Role;
import pl.sda.partyka.service.RoleService;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final RoleService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        service.addRole(new Role("ORGANIZER"));
        service.addRole(new Role("USER"));
    }
}
