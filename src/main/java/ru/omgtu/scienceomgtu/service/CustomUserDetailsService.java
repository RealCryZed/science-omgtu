package ru.omgtu.scienceomgtu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.omgtu.scienceomgtu.model.User;
import ru.omgtu.scienceomgtu.repository.UserRepository;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));

        return userDetails;

//        return userRepository.findByUsername(username)
//                .map(user -> new org.springframework.security.core.userdetails.User(
//                        user.getUsername(),
//                        user.getPassword(),
//                        true,
//                        true,
//                        true,
//                        true,
//                        getAuthorities("ADMIN")
//                ))
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        User userForm = userRepository.findByUsername(username);
//
//        if (userForm == null) {
//            throw new UsernameNotFoundException("User not authorized.");
//        }
//
//        List<GrantedAuthority> grantList = new ArrayList<>();
//        GrantedAuthority authority = new SimpleGrantedAuthority(userForm.getRole().toString());
//        grantList.add(authority);
//
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String password = encoder.encode(userForm.getPassword());
//
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, password, grantList);
//        System.out.println(userDetails.getAuthorities());
//        return userDetails;
//    }

//    @Autowired
//    public CustomUserDetailsService() {
//
//    }
//
//    /**
//     * Finds user with particular name in database, and if it's not null, grants them authorities.
//     * @param username the name of a user
//     * @return user with given authorities
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        User user = userRepository.findByLogin(username);
//        if (user != null) {
//            Set<Role> roles = new HashSet<>();
//            roles.add(user.getRole());
//            List<GrantedAuthority> authorities = getUserAuthority(roles);
//            return buildUserForAuthentication(user, authorities);
//        } else {
//            throw new UsernameNotFoundException(username + " not found");
//        }
//    }
//
//    /**
//     * Creates set of authorities and add roles to it.
//     * @param userRoles roles available for user
//     * @return list of authorities
//     */
//    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
//        Set<GrantedAuthority> roles = new HashSet<>();
//        userRoles.forEach((role) -> {
//            roles.add(new SimpleGrantedAuthority(role.getName()));
//        });
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
//        return grantedAuthorities;
//    }
//
//    /**
//     * Builds user for authentication with granted authorities.
//     * @param user given User with credentials
//     * @param authorities list of available authorities for this user
//     * @return new User with credentials
//     */
//    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
//        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
//    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByLogin(username);
//        System.out.println("Found user: " + user.getLogin() + ", " + user.getPassword());
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        return new org.springframework.security.core.userdetails.User(
//                user.getLogin(),
//                user.getPassword(),
//                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()))
//        );
//    }
}