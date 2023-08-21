package com.chordncode.pmaker.application.member;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.chordncode.pmaker.data.entity.MemberEntity;
import com.chordncode.pmaker.data.repository.MemberRepository;

@Component
public class CustomMemberDetailsService implements UserDetailsService{

    private MemberRepository memberRepo;
    public CustomMemberDetailsService(MemberRepository memberRepo){
        this.memberRepo = memberRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepo.findByMemId(username)
                                              .orElseThrow(() -> new UsernameNotFoundException("계정이 존재하지 않습니다."));

        return new CustomMemberDetails(memberEntity);
    }

}
