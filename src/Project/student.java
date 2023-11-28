package Project;

public class student {

    private int id;
    private String name;
    private double cgpa;
    private String major;

    public student(int id, String name, double cgpa, String major) {
        this.id = id;
        this.name = name;
        this.cgpa = cgpa;
        this.major = major;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public String getmajor() {
        return major;
    }

    public void setmajor(String major) {
        this.major = major;
    }
}
