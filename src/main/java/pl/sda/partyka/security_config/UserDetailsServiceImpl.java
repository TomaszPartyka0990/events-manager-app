package pl.sda.partyka.security_config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.sda.partyka.domain.User;
import pl.sda.partyka.service.UserService;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = userService.getUserByLogin(login);

        if (user == null){
            throw new UsernameNotFoundException("User with provided login doesn't exists");
        }

        return new MyUserDetails(user);
    }
}
