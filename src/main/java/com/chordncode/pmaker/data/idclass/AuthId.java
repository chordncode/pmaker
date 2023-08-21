package com.chordncode.pmaker.data.idclass;

import java.io.Serializable;

import lombok.Data;

@Data
public class AuthId implements Serializable{
    
    private String memId;
    private String memAuth;

}
