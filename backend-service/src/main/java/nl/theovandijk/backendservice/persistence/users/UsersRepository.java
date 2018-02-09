package nl.theovandijk.backendservice.persistence.users;

import nl.theovandijk.backendservice.representations.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User,Long> {
}
