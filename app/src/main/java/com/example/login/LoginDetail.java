package com.example.login;

/**
 * Created by LENOVO on 22-12-2015.
 */
public class LoginDetail {
    private long id;
    private String email;
    private String password;
    private String selectedItem;
    private String address;
    private String name;
    private int number;

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email= email;
    }
    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(String selectedItem) {
        this.selectedItem = selectedItem;
    }

    public void setName(String name) {
        this.name = name;
    }
}
