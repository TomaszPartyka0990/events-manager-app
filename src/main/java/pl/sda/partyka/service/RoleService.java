package pl.sda.partyka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.sda.partyka.domain.Role;
import pl.sda.partyka.repository.RoleRepository;
import pl.sda.partyka.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Role> getAllWithoutDefault(){
        return roleRepo.findAll()
                .stream()
                .filter(role -> !Utils.DEFAULT_ROLE.equals(role.getName()))
                .collect(Collectors.toList());
    }
}
