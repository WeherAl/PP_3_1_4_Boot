package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    void saveUser(User user);

    void saveUser(User user, String[] role);

    User getUserById(Long id);

    User getUserByUserName(String username);

    void deleteUserById(Long id);

    List<User> getListOfUsers();

    List<GrantedAuthority> getUserAuthority(Set<Role> userRoles);

    UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities);

    void updateUser(User user, long id, String[] roles);

    User convertToUser(UserDTO userDTO);

    UserDTO convertToUserDTO(User user);

}
