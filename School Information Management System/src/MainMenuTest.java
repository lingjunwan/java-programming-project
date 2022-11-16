import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MainMenuTest {
    // public static final String programID = "FSD001";  //Design for man program!
    public Scanner scannerInput;
    ProgramFactory programFactory;
    public String DATA_FILE1 = "programFile.txt";
    public String DATA_FILE2 ="courseFile.txt";



    public static void main(String[] args) throws ProgramFileNotExistException, IOException, WrongInputException, ParseException {
        Scanner scannerInput = new Scanner(System.in);
        MainMenuTest mainMenuTest = new MainMenuTest();
        mainMenuTest.programFactory = new ProgramFactory();
        mainMenuTest.programFactory.fsdProgram = mainMenuTest.initLoadFromProgramFile();
        mainMenuTest.programFactory.fsdProgram.setCourseList(mainMenuTest.initLoadFromCourseFile());
        StudentMethods studentMethods = new StudentMethods();
        studentMethods.loadAllStudentDatatoArraylist();
        School.readTeachers();
        int selection = 0;
        do {
            selection = mainMenuTest.displayMainPage();
            if (selection == 0) {
                mainMenuTest.saveDataToProgramFile();

            }
            if (selection == 1) {
                mainMenuTest.listAllCourseForAProgramDisplayPage();
            }
            if (selection == 2) {
                try {
                    mainMenuTest.addCourseForAProgramDisplayPage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (selection == 3) {
                mainMenuTest.modifyCourseDisplayPage();
            }
            if (selection == 4) {
                mainMenuTest.removeCourseDisplayPage();
            }
            if (selection == 5) {
                studentMethods.addcourse();
            }
            if (selection == 6) {
                studentMethods.dropcourse();
            }
            if (selection == 7) {
                studentMethods.viewCoursesTaking();
            }
            if (selection == 8) {
                School.employTeacher();
            }
        } while (selection != -1);
        System.out.println("Good Bye");
    }

    public int displayMainPage() {
        int selection = 9999;
        System.out.println();
        System.out.println();
        System.out.println("**********************This is a School Information Management System*********************");
        System.out.println("*  0 : save data to a file............................................................  *");
        System.out.println("*  1 : list all FSD program courses...................................................  *");
        System.out.println("*  2 : add a new course to FSD program................................................  *");
        System.out.println("*  3 : Modify a course to FSD program.................................................  *");
        System.out.println("*  4 : remove a course from FSD program...............................................  *");
        System.out.println("*  5 : Student - add a course.........................................................  *");
        System.out.println("*  6 : Student - drop a course........................................................  *");
        System.out.println("*  7 : Student - view current course taken............................................  *");
        System.out.println("*  8 : Teacher - Employ a teacher.....................................................  *");
        System.out.println("* -1 : Exit...........................................................................  *");
        System.out.println("*****************************************************************************************");

        System.out.println("Please input your choice:");
        scannerInput = new Scanner(System.in);
        try {
            selection = scannerInput.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Input error, Please input number from 0 tp 8 , -1 means exit!");
        }
        if (selection > 8 || selection < -1) {
            System.out.println("Please input number from 0 tp 8 , -1 means exit!");
        }
        return selection;
    }

    public void listAllCourseForAProgramDisplayPage() {
        if (programFactory.fsdProgram.getCourseList().size() == 0) {
            System.out.println("Currently, there is no any car information in this system");
        } else {
            System.out.println("********This is all course information for FSD program************");
            System.out.println("** ProgramID :" + programFactory.fsdProgram.getProgramID());
            System.out.println("** ProgramName :" + programFactory.fsdProgram.getProgramName() + "    ");
            System.out.println("** ProgramDescription :" + programFactory.fsdProgram.getProgramDescription() + "    ");
            System.out.println("** Program Course Lists:");
            ArrayList<Course> courseList = programFactory.fsdProgram.getCourseList();
            System.out.println("------------------------------------------------------------------------------------------------------");

            System.out.printf("%-40s", "Course Name\t\t");
            System.out.printf("  Course ID \t\t\t");
            System.out.printf("Course Credit \t\t\t ");
            System.out.printf("Course Hours\n");


            for (Course course : courseList) {
                System.out.printf("%-40s", course.getCourseName());
                System.out.printf("%15s", course.getCourseID());
                System.out.printf("\t\t\t%.2f \t\t\t\t", course.getCredit());
                System.out.printf("\t\t%d\t\t\n", course.getHours());

            }
            System.out.println("-----------------------------------------------------------------------------------------------------");
            System.out.printf("Summary =>>>>> ");
            System.out.printf("The Numbers of Course:  %d\t", courseList.size());
            int sumHours = 0;
            double sumCredit = 0;
            for (Course c : courseList) {
                sumCredit += c.getCredit();
                sumHours += c.getHours();
            }


            System.out.printf("\tTotal Credit :\t%.2f \t\t", sumCredit);
            System.out.printf("Total Hours:  %d\t\t\n", sumHours);
            System.out.println();
            System.out.println("Please enter Any Letter to go to main menu!");
            String next = scannerInput.next();
            System.out.println();
        }
    }

    public void removeCourseDisplayPage() {
        if (programFactory.fsdProgram.getCourseList().size() == 0) {
            System.out.println("Currently, there is no any course information in this system!");
        } else {
            System.out.println("Please input course ID that you want to remove!");
            String inputCourseID = scannerInput.next();
            Course courseByCourseID=null;
            try {
                courseByCourseID = programFactory.fsdProgram.getCourseByCourseID(inputCourseID);
            } catch(NoFoundDataException e){
                System.out.println(e.getMessage());
                return;
            }
            programFactory.fsdProgram.removeCourseForAProgram(courseByCourseID);
            System.out.println("\ncourse has been removed.\n");
        }
    }

    public void modifyCourseDisplayPage() throws WrongInputException {
        if (programFactory.fsdProgram.getCourseList().size() == 0) {
            System.out.println("Currently, there is no course information in this system!");
        } else {
            System.out.println("Please input course ID to modify:");
            String removecourseID = scannerInput.next();
            Course removecourseByCourseID = null;
            try {
                removecourseByCourseID = programFactory.fsdProgram.getCourseByCourseID(removecourseID);
            } catch (NoFoundDataException e) {
                System.out.println("No CourseID exist! Please check it!");
                return;
            }
            System.out.println("You want to modify Course: " + removecourseByCourseID);
            System.out.println("Please input your new Course ID ( Length<=10 ) :");
            String courseID = scannerInput.next();
            System.out.println("Please input your new Course Name ( Length<=50 ):");
            String courseName = scannerInput.next();
            System.out.println("Please input your new course hours ( 30<Hour<300 ):");
            int hours = scannerInput.nextInt();
            System.out.println("Please input your new course credit ( 2<=Credit<=10:");
            double credit = scannerInput.nextDouble();

            System.out.printf("\nYour course information: CourseID=%s CourseName=%s course hours=%d course credit=%.2f\n"
                    , courseID, courseName, hours, credit);
            System.out.println("You want to replace the old one? (y/n)");
            String confirmYOrNot = scannerInput.next();
            if ("y".equals(confirmYOrNot)) {

                programFactory.fsdProgram.removeCourseForAProgram(removecourseByCourseID);
                programFactory.fsdProgram.addCourseForAProgram("FSD001", courseID, courseName, hours, credit);
                System.out.println("Your data has been saved to list");
            } else {
                System.out.println("You give up your input!");
            }

        }
        System.out.println();
        System.out.println("Please enter Any Letter to go to main menu!");
        String next = scannerInput.next();
        System.out.println();


    }

    public void addCourseForAProgramDisplayPage() {

        System.out.println("Please input your Course ID ( ep:420-JA4-AB,length<=10 ):");
        String courseID = scannerInput.next();
        System.out.println("Please input your Course Name ( length<=50 ):");
        String courseName = scannerInput.next();
        System.out.println("Please input your course hours ( 10<Hour<300 ):");
        int hours = scannerInput.nextInt();
        System.out.println("Please input your course credit ( 2<=Credit<=10:");
        double credit = scannerInput.nextDouble();


        System.out.printf("\nYour course information: CourseID=%s CourseName=%s course hours=%d course credit=%.2f\n"
                , courseID, courseName, hours, credit);
        System.out.println("If above information is right? (y/n)");
        String confirmYOrNot = scannerInput.next();
        if ("y".equals(confirmYOrNot)) {
            programFactory.fsdProgram.addCourseForAProgram("FSD001", courseID, courseName, hours, credit);
            System.out.println("Your data has been saved to list");
        } else {
            System.out.println("You give up your input!");
        }
    }

    public void saveDataToProgramFile() throws IOException {
        if (this.programFactory.fsdProgram.equals(null)) {
            System.out.println("Nothing to save");
        } else {
            File file = new File("programFile.txt");
            if (file.exists() == false) {
                file.createNewFile();
            }
            File file2 = new File("courseFile.txt");
            if (file2.exists() == false) {
                file2.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(file);

            PrintWriter printWriter2 = new PrintWriter(file2);
            //System.out.println(file.getAbsolutePath());
            //System.out.print("FSD001;");
            printWriter.print("FSD001;");
            printWriter.print(this.programFactory.fsdProgram.getProgramName() + ";" + this.programFactory.fsdProgram.getProgramDescription() + ";{");
            ArrayList<Course> courseList = this.programFactory.fsdProgram.getCourseList();
            int size = courseList.size();

            for (int i = 0; i < size; i++) {
                if (i == size - 1) {
                    printWriter.print(courseList.get(i).getCourseID());
                } else {
                    printWriter.print(courseList.get(i).getCourseID() + ",");
                }
            }

            printWriter.print("}");


            for (int i = 0; i < size; i++) {
                printWriter2.print("FSD001;" + courseList.get(i).getCourseID() + ";"
                        + courseList.get(i).getCourseName() + ";"
                        + courseList.get(i).getCredit() + ";"
                        + courseList.get(i).getHours() + "\n");
            }


            printWriter.close();
            printWriter2.close();
            System.out.println("Already Save data to courseFile.txt and programFile.txt,Please check it");
        }
    }

    public Program initLoadFromProgramFile() throws
            ProgramFileNotExistException, FileNotFoundException, WrongInputException {

        File file = new File(DATA_FILE1);
        Program program = new Program(); //Later, if we change mind to many programs, use this
        if (!file.exists()) {
            throw new ProgramFileNotExistException("Program File Not Existed!");
        } else {
            Scanner scannerFile = new Scanner(file);
            String[] splitProgram = scannerFile.nextLine().split(";");
            if (splitProgram.length!=4){
                System.out.println("Error in reading file");
                return null;
            }
            String programID = splitProgram[0];
            String programName = splitProgram[1];
            String programDescription = splitProgram[2];


            String courseIDList = splitProgram[3];
            String[] splitCourseIDListText = courseIDList.split(",");
            ArrayList<String> splitCourseIDList = new ArrayList<>(0);
            for (int i = 0; i < splitCourseIDListText.length; i++) {
                splitCourseIDList.add(splitCourseIDListText[i]);
            }
            program.setCourseIDList(splitCourseIDList);
            program.setProgramID(programID);
            program.setProgramName(programName);
            program.setProgramDescription(programDescription);
            //          System.out.println("Program add " + program);

        }

        return program;
    }


    public ArrayList<Course> initLoadFromCourseFile() throws
            ProgramFileNotExistException, FileNotFoundException, WrongInputException {
        File file = new File(DATA_FILE2);
        ArrayList<Course> courseList1 = new ArrayList<>();
        if (!file.exists()) {
            throw new ProgramFileNotExistException("course File Not Existed!");
        } else {
            Scanner scannerFile = new Scanner(file);
            while (scannerFile.hasNext()) {
                Course course = new Course();
                String[] splitCourse = scannerFile.nextLine().split(";");
                if (splitCourse.length!=5){
                    System.out.println("Error in reading file");
                    continue;
                }
                String programID = splitCourse[0];
                String courseID = splitCourse[1];
                String courseName = splitCourse[2];
                String courseCredit = splitCourse[3];
                String courseHour = splitCourse[4];
                course.setProgramID(programID);
                course.setCourseID(courseID);
                course.setCourseName(courseName);
                course.setCredit(Double.parseDouble(courseCredit));
                course.setHours(Integer.parseInt(courseHour));
                courseList1.add(course);

            }
        }
        return courseList1;
    }


}


