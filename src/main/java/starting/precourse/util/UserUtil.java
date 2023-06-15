package starting.precourse.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import starting.precourse.entity.User;
import starting.precourse.exception.TargetNotFoundException;
import starting.precourse.repository.UserRepository;

public class UserUtil {
    private final UserRepository userRepository;

    public UserUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userRepository.findByName(name).orElseThrow(
                () -> new TargetNotFoundException("해당 유저가 없습니다.")
        );
    }
}
