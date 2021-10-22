package kz.iitu.project.hashproject.services.impl;

import kz.iitu.project.hashproject.entities.Roles;
import kz.iitu.project.hashproject.entities.Users;
import kz.iitu.project.hashproject.repositories.RoleRepository;
import kz.iitu.project.hashproject.repositories.UserRepository;
import kz.iitu.project.hashproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users createUser(Users user) {
            Users checkUser = userRepository.findByEmail(user.getEmail());
        if (checkUser == null){
            Roles role= roleRepository.findByRole("ROLE_USER");
            if (role != null){
                ArrayList<Roles> roles = new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }

        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Users myUser = userRepository.findByEmail(s);
        if (myUser != null){
            User secUser = new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
            return secUser;
        }

        throw new UsernameNotFoundException("User Not Found");
    }
}
