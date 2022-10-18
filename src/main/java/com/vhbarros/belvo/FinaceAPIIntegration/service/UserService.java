package com.vhbarros.belvo.FinaceAPIIntegration.service;

import com.vhbarros.belvo.FinaceAPIIntegration.model.Link;
import com.vhbarros.belvo.FinaceAPIIntegration.model.Role;
import com.vhbarros.belvo.FinaceAPIIntegration.model.User;
import com.vhbarros.belvo.FinaceAPIIntegration.repository.LinkRepository;
import com.vhbarros.belvo.FinaceAPIIntegration.repository.RoleRepository;
import com.vhbarros.belvo.FinaceAPIIntegration.repository.UserRepository;
import com.vhbarros.belvo.client.api.AccountsApi;
import com.vhbarros.belvo.client.api.InstitutionsApi;
import com.vhbarros.belvo.client.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private LinkRepository linkRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       LinkRepository linkRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.linkRepository = linkRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    public List<Link>  findByUserId(int id) {
        List<Link> links = linkRepository.getAllByUserId(id);
        return links;
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    public Link saveLink(Link link) {
        return linkRepository.save(link);
    }

    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setUsername("");
        apiClient.setPassword("");
        apiClient.setDebugging(true);
        return apiClient;
    }

    public AccountsApi accountApi() {
        return new AccountsApi(apiClient());
    }

    public InstitutionsApi institutionsApi() {
        return new InstitutionsApi(apiClient());
    }

}
