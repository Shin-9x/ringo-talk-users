package it.fartingbrains.ringotalkuser.rest.controller;

import it.fartingbrains.ringotalkuser.repository.ProfilePicturesRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfilePicturesRestController {
    private ProfilePicturesRepository profilePicturesRepository;

    public ProfilePicturesRestController(ProfilePicturesRepository profilePicturesRepository) {
        this.profilePicturesRepository = profilePicturesRepository;
    }
}
