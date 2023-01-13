package com.example.desginstofinal;

import java.io.Serializable;

public class Student  implements Serializable {
    private String name, Phone,email, uid,id;
    public Student(){}
    public Student(String Name, String Phone,String email, String uid,String id ){
        this.name=Name;
        this.Phone=Phone;
        this.uid=uid;
        this.email=email;
        this.id=id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getid() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
