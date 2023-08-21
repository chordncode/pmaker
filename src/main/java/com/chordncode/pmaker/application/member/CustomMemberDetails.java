package com.chordncode.pmaker.application.member;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.chordncode.pmaker.data.entity.MemberEntity;

public class CustomMemberDetails implements UserDetails {

    private MemberEntity memberEntity;

    public CustomMemberDetails(MemberEntity memberEntity){
        this.memberEntity = memberEntity;
    }

    public MemberEntity getMemberEntity(){
        return this.memberEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.memberEntity.getAuthEntityList()
                                .stream()
                                .map(authEntity -> new SimpleGrantedAuthority(authEntity.getMemAuth()))
                                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.memberEntity.getMemPw();
    }

    @Override
    public String getUsername() {
        return this.memberEntity.getMemId();
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
        return true;
    }
    
}
