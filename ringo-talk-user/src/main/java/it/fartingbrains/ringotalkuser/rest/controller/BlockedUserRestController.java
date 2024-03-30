package it.fartingbrains.ringotalkuser.rest.controller;

import it.fartingbrains.ringotalkuser.entity.UserDetails;
import it.fartingbrains.ringotalkuser.rest.service.UserService;
import it.fartingbrains.ringotalkuser.rest.util.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlockedUserRestController {
    private static final Logger _logger = LoggerFactory.getLogger(BlockedUserRestController.class);
    private UserService userService;

    public BlockedUserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/rtu/user/block/list-blocked")
    public void listBlocked(
            @RequestHeader("authorization") String token, @RequestHeader("requestUsername") String requestUsername
    ) {
        StopWatch watch = new StopWatch();
        watch.start();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format("[blockUser] START listBlocked for [%s].", requestUsername
            ));
        }

        RestUtil.authenticate(token, requestUsername, userService);

        UserDetails userDetails = userService.findByUsername(requestUsername);

        //TODO

        watch.stop();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format("[blockUser] END listBlocked. Elapsed time [%d ms]", watch.getTotalTimeMillis()));
        }
    }

    @PostMapping("/rtu/user/block/{username}")
    public void blockUser(
            @RequestHeader("authorization") String token, @RequestHeader("requestUsername") String requestUsername,
            @PathVariable("username") String usernameToBlock
    ) {
        StopWatch watch = new StopWatch();
        watch.start();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format("[blockUser] START blocking user: request [%s], toBlock [%s].",
                    requestUsername, usernameToBlock
            ));
        }

        RestUtil.authenticate(token, requestUsername, userService);

        UserDetails userWhoBlock = userService.findByUsername(requestUsername);
        UserDetails userToBlock = userService.findByUsername(usernameToBlock);

        userService.block(userWhoBlock, userToBlock);

        watch.stop();
        if(_logger.isInfoEnabled()) {
            _logger.info(String.format("[blockUser] END blockUser. Elapsed time [%d ms]", watch.getTotalTimeMillis()));
        }
    }
}
