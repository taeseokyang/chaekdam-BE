//package com.example.subsub.security;
//
//import com.example.subsub.domain.User;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//public class CustomUserDetails implements UserDetails {
//
//    private final User user;
//
//    public CustomUserDetails(User user) {
//        this.user = user;
//    }
//
//    public final User getUser(){
//        return user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        System.out.println(user.getRole().toString()xz);
//        Collection<GrantedAuthority> authoritie = new ArrayList<>();
//        authoritie.add(new SimpleGrantedAuthority(user.getRole().toString()));
//        return authoritie;
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassWord();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getUserId();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
