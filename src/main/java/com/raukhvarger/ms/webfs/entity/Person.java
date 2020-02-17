package com.raukhvarger.ms.webfs.entity;

import lombok.*;

import java.util.Objects;
import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {

    public String firstName;

    public String lastName;

    public String phoneNumber;

    public int hash;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(hash, person.hash);
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
