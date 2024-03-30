package it.fartingbrains.ringotalkuser.rest.service;

import it.fartingbrains.ringotalkuser.entity.BlockedUser;
import it.fartingbrains.ringotalkuser.entity.UserDetails;
import it.fartingbrains.ringotalkuser.jwt.JWTUtils;
import it.fartingbrains.ringotalkuser.repository.BlockedUserRepository;
import it.fartingbrains.ringotalkuser.repository.UserRepository;
import it.fartingbrains.ringotalkuser.rest.dto.jwt.JWTTokenDTO;
import it.fartingbrains.ringotalkuser.rest.dto.user.UserDTO;
import it.fartingbrains.ringotalkuser.rest.dto.user.UserPasswordDTO;
import it.fartingbrains.ringotalkuser.rest.exception.UnauthorizedUserException;
import it.fartingbrains.ringotalkuser.rest.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private static final Logger _logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private BlockedUserRepository blockedUserRepository;
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private JWTUtils jwtUtils;

    public UserService(UserRepository userRepository, BlockedUserRepository blockedUserRepository, JWTUtils jwtUtils) {
        this.userRepository = userRepository;
        this.blockedUserRepository = blockedUserRepository;
        this.jwtUtils = jwtUtils;
    }

    public UserDetails registerUser(UserDetails userDetails) {
        _setNewPassword(userDetails, userDetails.getPassword());

        LocalDateTime now = LocalDateTime.now();
        userDetails.setCreateDate(now);
        userDetails.setModifiedDate(now);

        return userRepository.save(userDetails);
    }

    private void _setNewPassword(UserDetails userDetails, String password) {
        // Generate random salt
        String salt = BCrypt.gensalt();

        // Encrypt password with salt
        String hashedPassword = bCryptPasswordEncoder.encode(password + salt);

        // Aggiorna i dettagli dell'utente con la password criptata e il sale
        userDetails.setPassword(hashedPassword);
        userDetails.setSalt(salt);
    }

    public UserDetails authenticateUser(String username, String password) {
        UserDetails userDetails = userRepository.findByUsername(username);

        String errorMessage;
        if(userDetails == null) {
            errorMessage = String.format("[authenticateUser] No user found with username [%s].", username);
            if(_logger.isErrorEnabled()) { _logger.error(errorMessage); }
            throw new UserNotFoundException(errorMessage);
        }

        boolean match =
                bCryptPasswordEncoder.matches(password + userDetails.getSalt(), userDetails.getPassword());

        if(!match) {
            errorMessage = "[authenticateUser] The password doesn't match.";
            if(_logger.isErrorEnabled()) { _logger.error(errorMessage); }
            throw new UnauthorizedUserException(errorMessage);
        }

        return userDetails;
    }

    public boolean validateToken(String token, String username) {
        return jwtUtils.validateToken(token, username);
    }

    public JWTTokenDTO generateJWTToken(UserDetails userDetails) {
        return jwtUtils.generateToken(userDetails);
    }

    public List<UserDetails> findAll() {
        return userRepository.findAll();
    }

    public UserDetails findByUsername(String username) {
        UserDetails user = userRepository.findByUsername(username);

        if(user == null) {
            String errMessage =
                    String.format("[findByUsername] Unable to find user searched by username [%s]", username);
            if(_logger.isErrorEnabled()) { _logger.error(errMessage); }
            throw new UserNotFoundException(errMessage);
        }

        return user;
    }

    public UserDetails updateUserDetails(UserDetails userDetails) {
        userDetails.setModifiedDate(LocalDateTime.now());
        return userRepository.save(userDetails);
    }

    public UserDTO toDTO(UserDetails userDetails) {
        return new UserDTO(
                userDetails.getUsername(), userDetails.getCreateDate(), userDetails.getModifiedDate(),
                userDetails.getBiography()
        );
    }

    public UserDetails updateUserPassword(UserDetails userDetails, UserPasswordDTO newPassword) {
        _setNewPassword(userDetails, newPassword.getPassword());
        return updateUserDetails(userDetails);
    }

    public void deleteByUsername(UserDetails searchedUser) {
        userRepository.deleteByUsername(searchedUser.getUsername());
    }

    public void block(UserDetails userWhoBlock, UserDetails userToBlock) {
        BlockedUser blockedUser = new BlockedUser();

        blockedUser.setUserId(userWhoBlock.getId());
        blockedUser.setBlockedUserId(userToBlock.getId());
        blockedUser.setCreateDate(LocalDateTime.now());

        blockedUserRepository.save(blockedUser);
    }
}
