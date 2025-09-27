package tg.ceel.cj.casierapi.security;

import org.springframework.stereotype.Service;
import tg.ceel.cj.casierapi.entities.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompteActives {

    public List<User> logins;

    public CompteActives() {
        this.logins = new ArrayList<>();
    }

    public List<User> getLogins() {
        return logins;
    }

    public void setLogins(List<User> logins) {
        this.logins = logins;
    }
}
