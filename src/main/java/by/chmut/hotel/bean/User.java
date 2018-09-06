package by.chmut.hotel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class User {
    private int id;
    private String login;
    private String password;
    private String name;
    private String lastName;
    private String role;
    private String email;
    private String telephone;
    private String country;
    private String city;
    private String address;
    private String zip;

    public User(String login, String password, String name, String lastName,
                String role, String email, String telephone, String country, String city, String address, String zip) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.telephone = telephone;
        this.country = country;
        this.city = city;
        this.address = address;
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (id != user.id) {
            return false;
        }
        if (login == null) {
            if (user.login != null) {
                return false;
            }
        } else if (!login.equals(user.login)) {
            return false;
        }
        if (password == null) {
            if (user.password != null) {
                return false;
            }
        } else if (!password.equals(user.password)) {
            return false;
        }
        if (name == null) {
            if (user.name != null) {
                return false;
            }
        } else if (!name.equals(user.name)) {
            return false;
        }
        if (lastName == null) {
            if (user.lastName != null) {
                return false;
            }
        } else if (!lastName.equals(user.lastName)) {
            return false;
        }
        if (role == null) {
            if (user.role != null) {
                return false;
            }
        } else if (!role.equals(user.role)) {
            return false;
        }
        if (email == null) {
            if (user.email != null) {
                return false;
            }
        } else if (!email.equals(user.email)) {
            return false;
        }
        if (telephone == null) {
            if (user.telephone != null) {
                return false;
            }
        } else if (!telephone.equals(user.telephone)) {
            return false;
        }
        if (country == null) {
            if (user.country != null) {
                return false;
            }
        } else if (!country.equals(user.country)) {
            return false;
        }
        if (city == null) {
            if (user.city != null) {
                return false;
            }
        } else if (!city.equals(user.city)) {
            return false;
        }
        if (address == null) {
            if (user.address != null) {
                return false;
            }
        } else if (!address.equals(user.address)) {
            return false;
        }
        if (zip == null) {
            if (user.zip != null) {
                return false;
            }
        } else if (!zip.equals(user.zip)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result*37 + id;
        result = result*37 + (login == null ? 0 : login.hashCode()) * 37;
        result = result*37 + (password == null ? 0 : password.hashCode()) * 37;
        result = result*37 + (name == null ? 0 : name.hashCode()) * 37;
        result = result*37 + (lastName == null ? 0 : lastName.hashCode()) * 37;
        result = result*37 + (role == null ? 0 : role.hashCode()) * 37;
        result = result*37 + (email == null ? 0 : email.hashCode()) * 37;
        result = result*37 + (telephone == null ? 0 : telephone.hashCode()) * 37;
        result = result*37 + (country == null ? 0 : country.hashCode()) * 37;
        result = result*37 + (city == null ? 0 : city.hashCode()) * 37;
        result = result*37 + (address == null ? 0 : address.hashCode()) * 37;
        result = result*37 + (zip == null ? 0 : zip.hashCode()) * 37;
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}

