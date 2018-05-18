package me.soulyana.bullhorn;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUDS implements UserDetailsService {

    AppUserRepository userRepo;

    public SSUDS(AppUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser thisUser = userRepo.findByUsername(s);

        if(thisUser==null) {
            throw new UsernameNotFoundException("Your login attempt was not succesful, try again" + s.toString());
        }
        System.out.println(thisUser.getUsername() + "has been found");
        return new User(thisUser.getUsername(), thisUser.getPassword(), userAuthorities(thisUser));
    }

    public Set<GrantedAuthority> userAuthorities(AppUser thisUser) {
        Set<GrantedAuthority> myAuthorities = new HashSet<>();

        for(AppRole eachRole: thisUser.getRoles()) {
            myAuthorities.add(new SimpleGrantedAuthority(eachRole.getName()));
        }
        return myAuthorities;
    }
}
