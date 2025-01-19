package uz.app.finalproject.dto;


import uz.app.finalproject.entity.Enums.Gender;
public class StudentDTO {

    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber;
    private Gender gender;


    public StudentDTO(String firstname, String lastname, String password, String phoneNumber, Gender gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public StudentDTO() {
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
}
