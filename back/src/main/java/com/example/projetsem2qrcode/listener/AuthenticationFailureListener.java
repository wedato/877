package com.example.projetsem2qrcode.listener;


import com.example.projetsem2qrcode.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

// chaque fois qu'un user fail son login
@Component
public class AuthenticationFailureListener {
    private LoginAttemptService loginAttemptService;

    @Autowired
    public AuthenticationFailureListener(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event)  {
        Object principal = event.getAuthentication().getPrincipal();
        // safe check pour voir que c bien un string
        if (principal instanceof String){
            String usernamme = (String) event.getAuthentication().getPrincipal();
            loginAttemptService.addUserToLoginAttemptCache(usernamme);
        }
    }
}
