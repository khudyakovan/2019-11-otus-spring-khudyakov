package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.homework.entity.Role;
import ru.otus.homework.entity.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final static boolean ENABLED = true;
    private final static boolean ACCOUNT_NON_EXPIRED = true;
    private final static boolean CREDENTIALS_NON_EXPIRED = true;
    private final static boolean ACCOUNT_NON_LOCKED = true;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userService.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid user: " + s);
        }else {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return this.convertUserForAuthentication(user, authorities);
        }
    }

    private UserDetails convertUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                ENABLED,
                ACCOUNT_NON_EXPIRED,
                CREDENTIALS_NON_EXPIRED,
                ACCOUNT_NON_LOCKED,
                authorities);
    }

    private List<GrantedAuthority> getUserAuthority(List<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

}
