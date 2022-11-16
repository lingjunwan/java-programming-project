import java.time.LocalDate;
import java.util.Arrays;


public class Teacher {
    private String firstName;
    private String lastName;
    private String teacherID;
    private LocalDate hiredDate;
    private Course[] teacherCourses;

    public Teacher(String firstName, String lastName, String teacherID, LocalDate hiredDate, Course[] teacherCourses) throws WrongInputException {
        setFirstName(firstName);
        setLastName(lastName);
        setTeacherID(teacherID);
        setHiredDate(hiredDate);
        setTeacherCourses(teacherCourses);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws WrongInputException {
        for (int i = 0; i < firstName.length(); i++) {
            if (!Character.isLetter(firstName.charAt(i)) && firstName.length() <= 2) {
                throw new WrongInputException("Wrong input - only letters and more than two characters");
            } else {
                this.firstName = firstName;
            }
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws WrongInputException {
        for (int i = 0; i < lastName.length(); i++) {
            if (!Character.isLetter(lastName.charAt(i)) && lastName.length() <= 2) {
                throw new WrongInputException("Wrong input - only letters and more than two characters");
            } else {
                this.lastName = lastName;
            }
        }
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) throws WrongInputException {
        for (int i = 0; i < teacherID.length(); i++) {
            if (teacherID.length() != 5 && !Character.isDigit(teacherID.charAt(i))) {
                throw new WrongInputException("Wrong input - Five Digits only.");
            } else {
                this.teacherID = teacherID;
            }
        }

    }

    public LocalDate getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(LocalDate hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Course[] getTeacherCourses() {
        return teacherCourses;
    }

    public void setTeacherCourses(Course[] teacherCourses) {
        this.teacherCourses = teacherCourses;
    }

    @Override
    public String toString() {
        return firstName + ";" +
                lastName + ";" +
                teacherID + ";" +
                hiredDate + ";" +
                Arrays.toString(teacherCourses);
    }

    public String toDataString() {
        return "| Full Name: " + firstName + " " +
                lastName + " " + "|  Teacher ID: " +
                teacherID + " |  Date Hired: " +
                hiredDate + "   |   " + "Courses taught: " +
                Arrays.toString(teacherCourses);
    }


}
