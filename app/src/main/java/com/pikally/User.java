package com.pikally;

import android.widget.EditText;

public class User {

    public String Name,surname,Email;

    public User(String email, EditText surname, EditText name){


    }

    public User(String name, String surname, String email) {
        this.Name = name;
        this.surname = surname;
        this.Email = email;
    }
}
