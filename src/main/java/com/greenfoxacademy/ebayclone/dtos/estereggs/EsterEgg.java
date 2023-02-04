package com.greenfoxacademy.ebayclone.dtos.estereggs;

public class EsterEgg {
    private String hi = "Hi!";
    private String welcome = "Welcome to this secret page!";
    private String[] credits = {
            "Krajnyák Márk",
            "ASDASD",
    };
    private String[] advice = {
            "Also if u are using chrome and NOT using this, please do it NOW!!!",
            "https://chrome.google.com/webstore/detail/json-viewer/gbmdgpbipfallnflgajpaliibnhdgobh"
    };

    public EsterEgg(String hi, String welcome, String[] credits, String[] advice) {
        this.hi = hi;
        this.welcome = welcome;
        this.credits = credits;
        this.advice = advice;
    }

    public EsterEgg() {
    }

    public String getHi() {
        return hi;
    }

    public void setHi(String hi) {
        this.hi = hi;
    }

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    public String[] getCredits() {
        return credits;
    }

    public void setCredits(String[] credits) {
        this.credits = credits;
    }

    public String[] getAdvice() {
        return advice;
    }

    public void setAdvice(String[] advice) {
        this.advice = advice;
    }
}
