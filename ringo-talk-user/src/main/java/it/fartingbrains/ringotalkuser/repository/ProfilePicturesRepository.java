package it.fartingbrains.ringotalkuser.repository;

import it.fartingbrains.ringotalkuser.entity.ProfilePictures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilePicturesRepository extends JpaRepository<ProfilePictures, Long> {
}
