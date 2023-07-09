package ra.model.user;

import ra.model.cart.CartItem;
import ra.model.order.Order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Serializable {
    private int id;
    private String name = "";
    private String username = "";
    private String password = "";
    private String email = "";
    private String address = "";
    private String tel = "";
    private boolean status = true;
    private Set<RoleName> roles = new HashSet<>();
    private List<CartItem> cart = new ArrayList<>();
    public User() {
    }

    // constructor for admin
    public User(int id, String name, String username, String password, Set<RoleName> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    // constructor for user
    public User(int id, String name, String username, String password, String email, Set<RoleName> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
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


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<RoleName> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleName> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<CartItem> getCart() {
        return cart;
    }

    public void setCart(List<CartItem> cart) {
        this.cart = cart;
    }



    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Username: " + username + " | Role: " + roles + " | Status: " + (status ? "🔓" : "🔒");
    }


}
