package com.vhbarros.belvo.FinaceAPIIntegration.backend.model;

public class Token {

    private String access;
    private String refresh;

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

}
