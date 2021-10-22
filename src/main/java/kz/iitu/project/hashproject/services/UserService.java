package kz.iitu.project.hashproject.services;

import kz.iitu.project.hashproject.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Users getUserByEmail(String email);
    Users createUser(Users user);

}
