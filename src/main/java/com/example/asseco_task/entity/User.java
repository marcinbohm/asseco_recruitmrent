package com.example.asseco_task.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
@Cacheable(value = false)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name="user_generator", sequenceName = "user_user_id_seq", allocationSize=1)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "pesel", unique = true)
    private String pesel;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<CommunicationMethod> communicationMethods = new LinkedHashSet<>();

    public Set<CommunicationMethod> getCommunicationMethods() {
        return communicationMethods;
    }

    public void setCommunicationMethods(Set<CommunicationMethod> communicationMethods) {
        this.communicationMethods = communicationMethods;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(pesel, user.pesel) && Objects.equals(communicationMethods, user.communicationMethods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, pesel, communicationMethods);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                ", communicationMethods=" + communicationMethods +
                '}';
    }
}
