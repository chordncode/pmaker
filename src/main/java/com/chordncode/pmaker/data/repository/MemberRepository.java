package com.chordncode.pmaker.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chordncode.pmaker.data.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
    
    Optional<MemberEntity> findByMemId(String memId);

}
