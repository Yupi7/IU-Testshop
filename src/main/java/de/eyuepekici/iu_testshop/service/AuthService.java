package de.eyuepekici.iu_testshop.service;

import de.eyuepekici.iu_testshop.model.AppUser;
import de.eyuepekici.iu_testshop.repository.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;

    public AuthService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public boolean login(String username, String password) {

        return appUserRepository.findByUsername(username)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public AppUser register(AppUser user) {
        return appUserRepository.save(user);
    }
}