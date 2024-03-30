package it.fartingbrains.ringotalkuser.rest.controller;

import it.fartingbrains.ringotalkuser.entity.UserDetails;
import it.fartingbrains.ringotalkuser.rest.dto.jwt.JWTTokenDTO;
import it.fartingbrains.ringotalkuser.rest.dto.user.UserBiographyDTO;
import it.fartingbrains.ringotalkuser.rest.dto.user.UserDTO;
import it.fartingbrains.ringotalkuser.rest.dto.user.UserLoginDTO;
import it.fartingbrains.ringotalkuser.rest.dto.user.UserPasswordDTO;
import it.fartingbrains.ringotalkuser.rest.service.UserService;
import it.fartingbrains.ringotalkuser.rest.util.RestUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {
    private static final Logger _logger = LoggerFactory.getLogger(UserRestController.class);
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/rtu/user")
    public UserDTO register(@Valid @RequestBody UserDetails userDetails) {
        StopWatch watch = new StopWatch();
        watch.start();
        if(_logger.isInfoEnabled()) { _logger.info("[register] START user registration."); }

        UserDTO registeredUser = userService.toDTO(userService.registerUser(userDetails));

        watch.stop();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format(
                    "[register] END user registration. Elapsed time [%d ms]", watch.getTotalTimeMillis()
            ));
        }

        return registeredUser;
    }

    @PostMapping("/rtu/login")
    public JWTTokenDTO login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        StopWatch watch = new StopWatch();
        watch.start();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format("[register] START login for [%s].", userLoginDTO.getUsername()));
        }

        UserDetails userDetails = userService.authenticateUser(userLoginDTO.getUsername(), userLoginDTO.getPassword());

        watch.stop();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format(
                    "[register] END login for [%s]. Elapsed time [%d ms]",
                    userLoginDTO.getUsername(), watch.getTotalTimeMillis()
            ));
        }

        return userService.generateJWTToken(userDetails);
    }

    @GetMapping("/rtu/user")
    public List<UserDTO> getAllUsers(
            @RequestHeader("authorization") String token, @RequestHeader("requestUsername") String requestUsername
    ) {
        StopWatch watch = new StopWatch();
        watch.start();
        if(_logger.isInfoEnabled()) { _logger.info("[getAllUsers] START getAllUsers."); }

        RestUtil.authenticate(token, requestUsername, userService);

        watch.stop();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format("[getAllUsers] END getAllUsers. Elapsed time [%d ms]", watch.getTotalTimeMillis()));
        }

        return userService.findAll().stream().map(userService::toDTO).toList();
    }

    @GetMapping("/rtu/user/{username}")
    public UserDTO getUser(
            @RequestHeader("authorization") String token, @RequestHeader("requestUsername") String requestUsername,
            @PathVariable("username") String username
    ) {
        StopWatch watch = new StopWatch();
        watch.start();
        if(_logger.isInfoEnabled()) { _logger.info("[getUser] START getAllUsers."); }

        RestUtil.authenticate(token, requestUsername, userService);

        UserDetails searchedUser = userService.findByUsername(username);

        watch.stop();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format("[getUser] END getAllUsers. Elapsed time [%d ms]", watch.getTotalTimeMillis()));
        }

        return userService.toDTO(searchedUser);
    }

    @PatchMapping("/rtu/user/{username}/biography")
    public UserDTO updateBiography(
            @RequestHeader("authorization") String token, @RequestHeader("requestUsername") String requestUsername,
            @PathVariable("username") String username, @RequestBody UserBiographyDTO userBiographyDTO
    ) {
        StopWatch watch = new StopWatch();
        watch.start();
        if(_logger.isInfoEnabled()) { _logger.info("[updateBiography] START updateBiography."); }

        RestUtil.authenticate(token, requestUsername, userService);

        UserDetails searchedUser = userService.findByUsername(username);
        searchedUser.setBiography(userBiographyDTO.getBiography());
        userService.updateUserDetails(searchedUser);

        watch.stop();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format("[updateBiography] END updateBiography. Elapsed time [%d ms]",
                    watch.getTotalTimeMillis()
            ));
        }

        return userService.toDTO(searchedUser);
    }

    @PatchMapping("/rtu/user/{username}/password")
    public UserDTO updatePassword(
            @RequestHeader("authorization") String token, @RequestHeader("requestUsername") String requestUsername,
            @PathVariable("username") String username, @RequestBody UserPasswordDTO userPasswordDTO
    ) {
        StopWatch watch = new StopWatch();
        watch.start();
        if(_logger.isInfoEnabled()) { _logger.info("[updatePassword] START updatePassword."); }

        RestUtil.authenticate(token, requestUsername, userService);

        UserDetails searchedUser = userService.findByUsername(username);
        userService.updateUserPassword(searchedUser, userPasswordDTO);

        watch.stop();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format("[updatePassword] END updatePassword. Elapsed time [%d ms]",
                    watch.getTotalTimeMillis()
            ));
        }
        return userService.toDTO(searchedUser);
    }

    @Transactional
    @DeleteMapping("/rtu/user/{username}")
    public void deleteUser(
            @RequestHeader("authorization") String token, @RequestHeader("requestUsername") String requestUsername,
            @PathVariable("username") String username
    ) {
        StopWatch watch = new StopWatch();
        watch.start();
        if(_logger.isInfoEnabled()) { _logger.info("[deleteUser] START deleteUser."); }

        RestUtil.authenticate(token, requestUsername, userService);

        UserDetails searchedUser = userService.findByUsername(username);
        userService.deleteByUsername(searchedUser);

        watch.stop();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format("[deleteUser] END deleteUser. Elapsed time [%d ms]",
                    watch.getTotalTimeMillis()
            ));
        }
    }
}
