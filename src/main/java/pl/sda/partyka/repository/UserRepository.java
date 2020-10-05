package pl.sda.partyka.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.partyka.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
