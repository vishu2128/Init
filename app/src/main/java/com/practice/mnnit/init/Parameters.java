package com.practice.mnnit.init;

/**
 * Created by Shivani gupta on 9/28/2017.
 */
public class Parameters {
    public final String ip = "192.168.43.25";
    public static Integer loggedIn = 0;
    public static String loginReg;
    public static Integer loginPage;

    public String getIp() {
        return ip;
    }

    public Integer getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Integer loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getLoginReg() {
        return loginReg;
    }

    public void setLoginReg(String loginReg) {
        this.loginReg = loginReg;
    }

    public Integer getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(Integer loginPage) {
        this.loginPage = loginPage;
    }
}
