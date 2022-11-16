import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Scanner;

/**
 * @author Shuwen Ju
 */
public class Student {


    private String firstName;
    private String lastName;
    private int id;
    private ArrayList<Course> CurrentCoursesList;
    //private ArrayList<Course> PassedCourseList;

    public Student() {
    }

    public Student(String firstName, String lastName, int id, ArrayList<Course> currentCoursesList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        CurrentCoursesList = currentCoursesList;
    }

    public Student(String firstName, String lastName, ArrayList<Course> currentCoursesList) {
        this.firstName = firstName;
        this.lastName = lastName;
        CurrentCoursesList = currentCoursesList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws InvalidInputException  {
        char[] firstname = firstName.toCharArray();
        for (int i = 0; i < firstname.length; i++) {
            if(Character.isDigit(firstname[i]) || firstname.length < 2) {
                throw new InvalidInputException("Name cannot contain digits / has to be at least 2 characters.");
            }
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws InvalidInputException{
        char[] lastname = lastName.toCharArray();
        for (int i = 0; i < lastname.length; i++) {
            if(Character.isDigit(lastname[i]) || lastname.length < 2) {
                throw new InvalidInputException("Name cannot contain digits / has to be at least 2 characters.");
            }
        }
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(("" + id).length() != 5){
            throw new InvalidInputException("Student ID has to be 5 digits");
        }
        this.id = id;
    }




    public ArrayList<Course> getCurrentCoursesList() {
        return CurrentCoursesList;
    }

    public void setCurrentCoursesList(ArrayList<Course> currentCoursesList) {
        CurrentCoursesList = currentCoursesList;
    }



    @Override
    public String toString() {
        return "Student " + firstName + " " + lastName + " ID " + id + ", courses enrolled: " + CurrentCoursesList;
    }


    public String toData(){
        return firstName + "&" + lastName + "&" + id + "&" + CurrentCoursesList + "\n";
    }

}




