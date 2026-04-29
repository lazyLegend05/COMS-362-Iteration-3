public class Patient {
    private String name;
    private int age;
    private String contactNum;

    // For Pharmacy Dept compatibility
    public Patient(String name) {
        this.name = name;
        this.age = 0;
        this.contactNum = "";
    }

    // For Emergency Care Dept
    public Patient(String name, int age, String contactNum) {
        this.name = name;
        this.age = age;
        this.contactNum = contactNum;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getContactNum() {
        return contactNum;
    }

    public String getPhoneNumber() {
        return contactNum;
    }
}