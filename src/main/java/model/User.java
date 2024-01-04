package model;

import java.math.BigDecimal;

public class User{
    Long id;
    String username;
    String password;
    String email;
    BigDecimal money;
    int point;

    public User(Long id, String username, String email, BigDecimal money, int point) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.money = money;
        this.point = point;
    }

    public User(){}

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public int getPoint() {
        return point;
    }
}
