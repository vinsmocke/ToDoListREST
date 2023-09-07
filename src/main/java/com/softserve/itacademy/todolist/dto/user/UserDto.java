package com.softserve.itacademy.todolist.dto.user;

import com.softserve.itacademy.todolist.model.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserDto {

    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private Role role;

    public UserDto() {
    }

    public UserDto(Long id, String firstName, String lastName, String email, Role role, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto userDto)) return false;

        if (!id.equals(userDto.id)) return false;
        if (!firstName.equals(userDto.firstName)) return false;
        if (!lastName.equals(userDto.lastName)) return false;
        if (!email.equals(userDto.email)) return false;
        if (!password.equals(userDto.password)) return false;
        return role.equals(userDto.role);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}