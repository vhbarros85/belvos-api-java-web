package com.vhbarros.belvo.FinaceAPIIntegration.controller;

import javax.validation.Valid;

import com.vhbarros.belvo.FinaceAPIIntegration.backend.model.Token;
import com.vhbarros.belvo.FinaceAPIIntegration.backend.model.connect.BelvoAPIClient;
import com.vhbarros.belvo.FinaceAPIIntegration.model.Link;
import com.vhbarros.belvo.FinaceAPIIntegration.model.User;
import com.vhbarros.belvo.FinaceAPIIntegration.service.UserService;
import com.vhbarros.belvo.client.api.AccountsApi;
import com.vhbarros.belvo.client.api.InstitutionsApi;
import com.vhbarros.belvo.client.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping(value={"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }


    @GetMapping(value="/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the user name provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @GetMapping(value="/admin/home")
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        /*AccountsApi accountsApi = userService.accountApi();
        for(Link link : user.getLinks()){
            accountsApi.listAccounts(1,10,link.getLink()),)
        }*/

        List<Link> links = userService.findByUserId(user.getId());
        InstitutionsApi institutionsApi = userService.institutionsApi();
        InstitutionsPaginatedResponse institutionsPaginatedResponse = institutionsApi.listInstitutions(1,100,new InstitutionFilters());
        List<Institution> institutions = institutionsPaginatedResponse.getResults();

        List<Institution> clientInstitutions = new ArrayList<Institution>();

        for(Link link : links){

            for(Institution institution : institutions){
                System.out.println("institution.getName()");
                if(link.getInstitution().equals(institution.getName())){
                    System.out.println("institution.getName()");
                    clientInstitutions.add(institution);
                }
            }
        }
        System.out.println();
        modelAndView.addObject("institutions",clientInstitutions);

        /*AccountsApi accountsApi = userService.accountApi();
        for(Link link : links){
            AccountFilters filters = new AccountFilters();
            filters.setLink(link.getLink());
            AccountsPaginatedResponse response = accountsApi.listAccounts(1,100, UUID.nameUUIDFromBytes(link.getLink().getBytes()),filters);
            List<Account> accounts = response.getResults();
        }*/
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }
    @GetMapping(value="/access_token")
    @ResponseBody
    public Token getAccessToken() throws IOException {
        return new BelvoAPIClient().getAccessToken();
    }

    @PostMapping(value="/saveId")
    @ResponseBody
    public String saveId(Link link) throws IOException {
        userService.saveLink(link);
        return "";
    }
}
