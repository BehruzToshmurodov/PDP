package uz.app.finalproject.entity;

import jakarta.persistence.*;

import uz.app.finalproject.entity.Enums.Gender;
import uz.app.finalproject.entity.Enums.Status;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber;
    @Enumerated
    private Gender gender;
    @Enumerated
    private Status status = Status.ACTIVE;
    private Boolean addedGroup = false;


    public Student(Long id, String firstname, String lastname, String password, String phoneNumber, Gender gender, Status status, Boolean addedGroup) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.status = status;
        this.addedGroup = addedGroup;
    }


    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getAddedGroup() {
        return addedGroup;
    }

    public void setAddedGroup(Boolean addedGroup) {
        this.addedGroup = addedGroup;
    }
}
