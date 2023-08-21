package com.chordncode.pmaker.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.chordncode.pmaker.data.idclass.AuthId;

import lombok.Data;

@Data
@Entity
@IdClass(AuthId.class)
@Table(name = "AUTH")
public class AuthEntity {
    
    @Id
    @Column(name = "MEM_ID")
    private String memId;

    @Id
    @Column(name = "MEM_AUTH")
    private String memAuth;

    @ManyToOne
    @JoinColumn(name="MEM_ID", insertable=false, updatable=false)
    private MemberEntity memberEntity;

}
