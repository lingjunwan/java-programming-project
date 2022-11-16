import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Shuwen Ju
 */
class StudentMethods {
    static final String DATA_FILE = "AllStudent";
    static File studentFile = new File(DATA_FILE);
    static Scanner input = new Scanner(System.in);

    static ArrayList<Student> allStudent = null;

    public ArrayList<Student> loadAllStudentDatatoArraylist() {
        allStudent = new ArrayList<Student>();

        try (Scanner reader = new Scanner(studentFile)) {
            while (reader.hasNext()) {
                String line = reader.nextLine();
                String[] dataLine = line.split("&");

                if (dataLine.length != 4) {
                    System.out.println("Error reading the file");
                    continue;
                }

                String firstname = dataLine[0];
                String lastname = dataLine[1];
                int studentid = Integer.parseInt(dataLine[2]);
                ArrayList<Course> list = new ArrayList();
                String programid = "";
                String coursecode = "";
                String coursename = "";
                double credit = 0;
                int hour = 0;
                String courses = dataLine[3].substring(1, dataLine[3].length() - 1);
                String[] coursesplit = {""};
                for (int i = 0; i < courses.length(); i++) {
                    coursesplit = courses.split(",");// split to each course arrayinfo
                }
                String[] eachcourse = {""};

                for (int j = 0; j < coursesplit.length; j++) {
                    eachcourse = coursesplit[j].trim().split(";");
                    programid = eachcourse[0];
                    coursecode = eachcourse[1];
                    coursename = eachcourse[2];
                    credit = Double.parseDouble(eachcourse[3]);
                    String hourstemp = eachcourse[4].substring(0, eachcourse[4].length()); // remove }
                    hour = Integer.parseInt(hourstemp);
                    list.add(new Course(coursename, coursecode, programid, hour, credit));
                }
                allStudent.add(new Student(firstname, lastname, studentid, list));
            }
        } catch (IOException e) {
            System.out.println("there is an exception in reading/writing the data " + e.getMessage());
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        } catch (WrongInputException e) {
            throw new RuntimeException(e);
        }
        return allStudent;
    }

    {
        loadAllStudentDatatoArraylist();
    }

    public void viewCoursesTaking(){
        int studentidInput = 0;
        boolean answer = true;
        while (answer) {
            System.out.println("Please enter student ID");
            studentidInput = input.nextInt();
            if(studentidInput >= 10000 && studentidInput <= 10000 + allStudent.size() - 1){
                answer= false;
            }else{
                System.out.println("Please recheck your Student ID");
                continue;
            }
        }
        Student currentStudent = null;
        for (int i = 0; i < allStudent.size(); i++) {
            if (studentidInput == allStudent.get(i).getId()) {
                currentStudent = allStudent.get(i);
            }
        }
        for (int i = 0; i < currentStudent.getCurrentCoursesList().size(); i++) {
            System.out.println(currentStudent.getCurrentCoursesList().get(i));
        }
    }



    public void addcourse() {
        try {
            int studentidInput = 0;
            boolean answer = true;
            while (answer) {
                System.out.println("Please enter student ID");
                studentidInput = input.nextInt();
                if(studentidInput >= 10000 && studentidInput <= 10000 + allStudent.size() - 1){
                    answer= false;
                }else{
                    System.out.println("Please recheck your Student ID");
                    continue;
                }
            }

            Student currentStudent = null;
            for (int i = 0; i < allStudent.size(); i++) {
                if (studentidInput == allStudent.get(i).getId()) {
                    currentStudent = allStudent.get(i);
                }
            }

            ArrayList<Course> courses = new MainMenuTest().initLoadFromCourseFile();
            for (int i = 0; i < courses.size(); i++) {
                System.out.println(courses.get(i));
            }
            boolean match = true;
            while(match) {
                System.out.println("Please choose a course you'd like to add, please enter course code");
                String coursecodeinput = input.next();

                for (int i = 0; i < courses.size(); i++) {
                    if (coursecodeinput.equals(courses.get(i).getCourseID())) {
                        ArrayList<Course> list = currentStudent.getCurrentCoursesList();
                        list.add(new Course(courses.get(i).getCourseName(), courses.get(i).getCourseID(), courses.get(i).getProgramID(),
                                courses.get(i).getHours(), courses.get(i).getCredit()));
                        match = false;
                    }
                }
                if(match){
                    System.out.println("Not able to add this course, please recheck");
                    continue;
                }
            }
            try (PrintWriter writer = new PrintWriter(studentFile)) {
                for (Student student : allStudent) {
                    writer.print(student.toData());
                }
                writer.close();
            } catch (IOException e) {
                System.out.println("there is an exception in reading/writing the data " + e.getMessage());
            }

        } catch (ProgramFileNotExistException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (WrongInputException e) {
            throw new RuntimeException(e);
        }
    }





    public void dropcourse() {
        int studentidInput = 0;
        boolean answer = true;
        while (answer) {
            System.out.println("Please enter student ID");
            studentidInput = input.nextInt();
            if(studentidInput >= 10000 && studentidInput <= 10000 + allStudent.size() - 1){
                answer= false;
            }else{
                System.out.println("Please recheck your Student ID");
                continue;
            }
        }
        Student currentStudent = null;
        for (int i = 0; i < allStudent.size(); i++) {
            if (studentidInput == allStudent.get(i).getId()) {
                currentStudent = allStudent.get(i);}
        }


        //displaying what this student takes right now:
        ArrayList<Course> list = currentStudent.getCurrentCoursesList();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(currentStudent.getCurrentCoursesList().get(i));
        }
        //ask which course they want to drop:
        boolean match = true;
        while(match) {
            System.out.println("Please enter course that you'd like to drop by course ID");
            String courseidInput = input.next();
            //remove the selected course from the list
            for (int i = 0; i < list.size(); i++) {
                if (courseidInput.equals(list.get(i).getCourseID())) {
                    list.remove(i);
                    match = false;
                }
            }
            if(match){
                System.out.println("Unable to drop this course, please recheck.");
                continue;
            }

            try (PrintWriter writer = new PrintWriter(studentFile)) {
                for (Student student : allStudent) {
                    writer.print(student.toData());
                }
                writer.close();
            } catch (IOException e) {
                System.out.println("there is an exception in reading/writing the data " + e.getMessage());
            }
    }


//    public void saveData() {
//        if (!allStudent.isEmpty()) {
//            try (PrintWriter writer = new PrintWriter(studentFile)) {
//                for (Student student : allStudent) {
//                    writer.print(student.toData());
//                    writer.close();
//                }
//            } catch (IOException e) {
//                System.out.println("there is an exception in reading/writing the data " + e.getMessage());
//
//            }
//        }
//    }
}}

