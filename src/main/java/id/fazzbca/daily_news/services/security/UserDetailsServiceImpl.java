package id.fazzbca.daily_news.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import id.fazzbca.daily_news.models.User;
import id.fazzbca.daily_news.repositories.AdminRepository;
import id.fazzbca.daily_news.repositories.UserRepository;
import id.fazzbca.daily_news.repositories.CreatorRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    CreatorRepository creatorRepository;

    //email sebagai username saat login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        if (!userRepository.existsByEmail(username)) {
            throw new UsernameNotFoundException(username + " is not found");
        }

        User user = userRepository.findByEmail(username);

        return UserDetailsImpl.buid(user);
    }
    
}