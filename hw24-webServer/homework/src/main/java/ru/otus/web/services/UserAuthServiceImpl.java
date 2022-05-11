package ru.otus.web.services;

public class UserAuthServiceImpl implements UserAuthService {

    @Override
    public boolean authenticate(String login, String password) {
        if ("admin".equals(login) && "admin".equals(password)) {
            return true;
        } else {
            return false;
        }
    }

}
