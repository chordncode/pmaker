package com.chordncode.pmaker.data.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "MEMBER")
public class MemberEntity {
    
    @Id
    @Column(name = "MEM_ID")
    private String memId;

    @Column(name = "MEM_PW", nullable = false)
    private String memPw;

    @Column(name = "MEM_NM", nullable = false)
    private String memNm;

    @OneToMany(mappedBy="memberEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthEntity> authEntityList;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "WITHDRAWAL_AT")
    private LocalDateTime withdrawalAt;

}
