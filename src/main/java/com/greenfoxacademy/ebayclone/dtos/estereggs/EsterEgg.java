package com.greenfoxacademy.ebayclone.dtos.estereggs;

public class EsterEgg {
    private String hi = "Hi!";
    private String welcome = "Welcome to this secret page!";
    private String[] credits = {
            "Krajnyák Márk",
            "ASDASD",
    };

    public EsterEgg(String hi, String welcome, String[] credits) {
        this.hi = hi;
        this.welcome = welcome;
        this.credits = credits;
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
}
