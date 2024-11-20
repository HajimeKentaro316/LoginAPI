package com.example.login.domain.model.user;

import java.util.Objects;

import jakarta.persistence.Embeddable;
@Embeddable
public class UserName {
    private String firstName;
    private String lastName;

    public UserName() {
    }

    public UserName(String firstName, String lastName) {
        // 仕様を満たしていないと、インスタンスを生成できないようにする。
        if (firstName.length() > 20 || lastName.length() > 20) {
            throw new IllegalArgumentException("");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserName fullName = (UserName) o;
        return Objects.equals(fullName, fullName.firstName) && Objects.equals(fullName, fullName.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
