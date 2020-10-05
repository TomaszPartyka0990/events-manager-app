package pl.sda.partyka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.partyka.domain.Role;
import pl.sda.partyka.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepo;

    public void addRole(Role role){
        roleRepo.save(role);
    }

    public Role getRoleByName(String name){
        return roleRepo.getByName(name);
    }
}
