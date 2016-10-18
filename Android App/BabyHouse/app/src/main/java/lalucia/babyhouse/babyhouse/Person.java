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
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    //**************************************************************************
    public String getPersonSurname() {
        return personSurname;
    }
    //**************************************************************************
    public void setPersonSurname(String personSurname) {
        this.personSurname = personSurname;
    }
    //**************************************************************************
    public String getPersonEmail() {
        return personEmail;
    }
    //**************************************************************************
    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }
    //**************************************************************************
    public String getPersonContactNumber() {
        return personContactNumber;
    }
    //**************************************************************************
    public void setPersonContactNumber(String personContactNumber) {
        this.personContactNumber = personContactNumber;
    }
    //**************************************************************************
    public String getPersonPassword() {
        return personPassword;
    }
    //**************************************************************************
    public void setPersonPassword(String personPassword) {
        this.personPassword = personPassword;
    }
}
