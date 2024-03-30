package it.fartingbrains.ringotalkuser.repository;

import it.fartingbrains.ringotalkuser.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {
    public UserDetails findByUsername(String username);
    public void deleteByUsername(String username);
}
