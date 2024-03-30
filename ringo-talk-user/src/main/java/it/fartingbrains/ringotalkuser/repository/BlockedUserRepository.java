package it.fartingbrains.ringotalkuser.repository;

import it.fartingbrains.ringotalkuser.entity.BlockedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockedUserRepository extends JpaRepository<BlockedUser, Long> {
    List<BlockedUser> findByUserId(long userId);
}
