package com.gracefull.webRTCService.models;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Data
@Entity
public class Credentials {

    @Id
    @GeneratedValue
    private Long id;
    private String login;
    private String secret;
    @Column (name = "is_busy")
    private Boolean isBusy;

    public Credentials() {
    }



    public Credentials(String login, String secret, Boolean isBusy) {
        this.login = login;
        this.secret = secret;
        this.isBusy = isBusy;
    }


}

