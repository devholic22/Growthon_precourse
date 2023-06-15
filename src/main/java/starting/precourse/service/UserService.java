package starting.precourse.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import starting.precourse.dto.LoginDto;
import starting.precourse.dto.TokenDto;
import starting.precourse.dto.UserDto;
import starting.precourse.entity.User;
import starting.precourse.exception.AlreadyExistUserException;
import starting.precourse.exception.LoginException;
import starting.precourse.exception.NotQualifiedDtoException;
import starting.precourse.exception.TargetNotFoundException;
import starting.precourse.jwt.JwtFilter;
import starting.precourse.jwt.TokenProvider;
import starting.precourse.repository.UserRepository;

import java.util.Collections;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }

    public User signup(UserDto userDto) {

        if (userDto.getName() == null || userDto.getEmail() == null)
            throw new NotQualifiedDtoException("name 또는 email이 비어있습니다.");

        if (userRepository.findByName(userDto.getName()).isPresent())
            throw new AlreadyExistUserException("이미 가입되어 있는 유저입니다.");

        User newUser = new User(userDto.getEmail(), userDto.getName());

        return userRepository.save(newUser);
    }

    public ResponseEntity<TokenDto> login(LoginDto loginDto) {

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginDto.getName(),null,
                Collections.singleton(simpleGrantedAuthority));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByName(loginDto.getName())
                .orElseThrow(() -> new TargetNotFoundException("없는 유저입니다."));

        if (!user.getEmail().equals(loginDto.getEmail())) {
            throw new LoginException("이메일이 일치하지 않습니다.");
        }

        String jwt = tokenProvider.createToken(authentication, user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }
}
