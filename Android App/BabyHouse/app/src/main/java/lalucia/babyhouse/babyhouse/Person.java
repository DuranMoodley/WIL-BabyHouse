/*
Person.java
Contains person data that will be called in other classes
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

public class Person {

    private String personName;
    private String personSurname;
    private String personEmail;
    private String personContactNumber;
    private String personPassword;
    //**************************************************************************
    public Person (String emailAddress, String password)
    {
        personEmail = emailAddress;
        personPassword = password;
    }
    //**************************************************************************
    public Person(String name,String surname, String email, String contactNumber, String password)
    {
        personName = name;
        personSurname = surname;
        personEmail = email;
        personContactNumber = contactNumber;
        personPassword = password;
    }
    //**************************************************************************
    public String getPersonName() {
        return personName;
    }
    //**************************************************************************
    public String getPersonSurname() {
        return personSurname;
    }
    //**************************************************************************
    public String getPersonEmail() {
        return personEmail;
    }
    //**************************************************************************
    public String getPersonContactNumber() {
        return personContactNumber;
    }
    //**************************************************************************
    public String getPersonPassword() {
        return personPassword;
    }
    //**************************************************************************
}
