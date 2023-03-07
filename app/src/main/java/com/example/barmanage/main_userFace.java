package com.example.barmanage;

public class main_userFace {
    private int image_user;
    private String textUser;

    public int getImage_user() {
        return image_user;
    }

    public void setImage_user(int image_user) {
        this.image_user = image_user;
    }

    public String getTextUser() {
        return textUser;
    }

    public void setTextUser(String textUser) {
        this.textUser = textUser;
    }

    public main_userFace(int image_user, String textUser) {
        this.image_user = image_user;
        this.textUser = textUser;
    }
}
