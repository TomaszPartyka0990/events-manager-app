package pl.sda.partyka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.partyka.domain.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(String name);
}
