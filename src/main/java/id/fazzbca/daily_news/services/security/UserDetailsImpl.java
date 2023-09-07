package id.fazzbca.daily_news.services.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import id.fazzbca.daily_news.models.Admin;
import id.fazzbca.daily_news.models.Creator;
import id.fazzbca.daily_news.models.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String email;
    private Boolean isDeleted;

    public static UserDetails buid(User user) {
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("USER");
        return new UserDetailsImpl(authorities, user.getPassword(), user.getEmail(), user.isDeleted());
    }

    public static UserDetails buid(Admin admin) {
        List<GrantedAuthority> adminAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN");
        return new UserDetailsImpl(adminAuthorities, admin.getPassword(), admin.getEmail(), admin.isDeleted()); 
    }

    public static UserDetails buid(Creator creator) {
        List<GrantedAuthority> creatorAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("CREATOR");
        return new UserDetailsImpl(creatorAuthorities, creator.getPassword(), creator.getEmail(), creator.isDeleted()); 
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.isDeleted;
    }
}
