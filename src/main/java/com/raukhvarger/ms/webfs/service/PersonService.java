package com.raukhvarger.ms.webfs.service;

import com.raukhvarger.ms.webfs.entity.Person;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PersonService {

    private int h = 1;

    private List<Person> persons = Arrays.asList(
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build(),
            Person.builder()
                    .firstName("Иван")
                    .lastName("Петров")
                    .phoneNumber("+7(960)123-34-54")
                    .hash(h++)
                    .build()
    );

    public Person[] fetch(int from, int count) {
        Person[] result = new Person[count];
        for (int i = from, n = 0; i < from + count; i++, n++) {
            result[n] = persons.get(i);
        }
        return result;
    }
}
