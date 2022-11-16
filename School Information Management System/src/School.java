import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class School {
    static final String DATA_FILE_TEACHERS = "teacherData1.txt";

    private final String schoolName = "John Abbott College";
    private static ArrayList<Teacher> teacherList = new ArrayList<>();

    public School() {
    }


    @Override
    public String toString() {
        return "School{" +
                "schoolName='" + schoolName + '\'' +
                '}';
    }


    public String getSchoolName() {
        return schoolName;
    }

    public ArrayList<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(ArrayList<Teacher> teacherList) {
        School.teacherList = teacherList;
    }

    public static void employTeacher() throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);

        boolean userDone = true;
        try {
            while (userDone) {
                //determine teacher variables
                System.out.println("First name of the teacher: ");
                String teacherFName = scan.next();
                for (int i = 0; i < teacherFName.length(); i++) {
                    if (Character.isDigit(teacherFName.charAt(i)) || teacherFName.length() < 2) {
                        throw new WrongInputException("Over two characters and only letters.");
                    }
                }
                System.out.println("Last name of the teacher");
                String teacherLName = scan.next();
                for (int i = 0; i < teacherLName.length(); i++) {
                    if (Character.isDigit(teacherLName.charAt(i)) || teacherLName.length() < 2) {
                        throw new WrongInputException("Over two characters and only letters.");
                    }
                }
                System.out.println("Teacher ID (5 digits): ");
                String teacherID = scan.next();
                for (int i = 0; i < teacherID.length(); i++) {
                    if (Character.isLetter(teacherID.charAt(i)) || teacherID.length() != 5) {
                        throw new WrongInputException("Teacher ID must be 5 digits.");
                    }
                }
                System.out.println("Teacher's starting date (YYYY-MM-DD): ");
                String dateString = scan.next();
                LocalDate startingDate = LocalDate.parse(dateString);//parse date string into pattern for LocalDate
                System.out.println("How many courses will this teacher have: ");
                int amountOfClassesForTeacher = scan.nextInt();
                if (amountOfClassesForTeacher > 4 || amountOfClassesForTeacher < 0) {
                    throw new WrongInputException("4 class maximum.");
                }

                scan.nextLine();

                //array of courses being taught by individual teacher
                String[] courseNameArray = new String[amountOfClassesForTeacher];
                String[] courseIDArray = new String[amountOfClassesForTeacher];
                String[] programArray = new String[amountOfClassesForTeacher];
                int[] hoursArray = new int[amountOfClassesForTeacher];
                double[] classCredits = new double[amountOfClassesForTeacher];

                for (int i = 0; i < amountOfClassesForTeacher; i++) {
                    System.out.printf("Name of cClass %d taught by %s %s:%n", i + 1, teacherFName, teacherLName);
                    String userInputName = scan.nextLine();
                    System.out.printf("Class' %d ID number:%n", i + 1);
                    String userInputID = scan.nextLine();
                    System.out.printf("Program ID:%n");
                    String userInputProgramID = scan.nextLine();
                    System.out.printf("Hours in class %d%n", i + 1);
                    int userHoursInput = scan.nextInt();
                    scan.nextLine();
                    System.out.printf("Credits gained from class %d%n", i + 1);
                    double credits = scan.nextDouble();
                    scan.nextLine();
                    courseNameArray[i] = userInputName;
                    courseIDArray[i] = userInputID;
                    programArray[i] = userInputProgramID;
                    hoursArray[i] = userHoursInput;
                    classCredits[i] = credits;
                }


                Course[] teacherCourses = getCourses(amountOfClassesForTeacher, courseNameArray, courseIDArray, programArray, hoursArray, classCredits);

                Teacher teacher = new Teacher(teacherFName, teacherLName, teacherID, startingDate, teacherCourses);
                teacherList.add(teacher);
                saveTeacherData();
                listAllTeachers();
                userDone = false;
            }
        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    private static Course[] getCourses(int amountOfClassesForTeacher, String[] courseNameArray, String[] courseIDArray, String[] programArray, int[] hoursArray, double[] classCredits) throws WrongInputException {
        Course[] teacherCourses = new Course[amountOfClassesForTeacher];

        //create courses array
        for (int i = 0; i < amountOfClassesForTeacher; i++) {
            teacherCourses[i] = new Course(courseNameArray[i], courseIDArray[i], programArray[i], hoursArray[i], classCredits[i]);
        }
        return teacherCourses;
    }

    public static void readTeachers() throws FileNotFoundException, InputMismatchException {
        try (Scanner reader = new Scanner(new File(DATA_FILE_TEACHERS))) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String newLine = line.replaceAll("[\\[\\]]", "").replaceAll(",", ";");
                String[] arr = newLine.split(";");

                String teacherFirstName = arr[0];
                String teacherLastName = arr[1];
                String teacherID = arr[2];
                String dateString = arr[3];
                LocalDate startingDate = LocalDate.parse(dateString);

                int arrLen = arr.length - 4;
                if (arrLen % 5 != 0) {
                    throw new InputMismatchException("Invalid line length in text");
                }

                String[] courseNameArray = new String[arrLen / 5];
                for (int i = 0, counter = 4; i < arrLen / 5; i++, counter += 5) {
                    courseNameArray[i] = arr[counter];
                }


                String[] courseIDArray = new String[arrLen / 5];
                for (int i = 0, counter = 5; i < arrLen / 5; i++, counter += 5) {
                    courseIDArray[i] = arr[counter];
                }

                String[] programIDArray = new String[arrLen / 5];
                for (int i = 0, counter = 6; i < arrLen / 5; i++, counter += 5) {
                    programIDArray[i] = arr[counter];
                }

                int[] hoursArray = new int[arrLen / 5];
                for (int i = 0, counter = 8; i < arrLen / 5; i++, counter += 5) {
                    hoursArray[i] = Integer.parseInt(arr[counter]);
                }

                double[] creditsArray = new double[arrLen / 5];
                for (int i = 0, counter = 7; i < arrLen / 5; i++, counter += 5) {
                    creditsArray[i] = Double.parseDouble(arr[counter]);
                }

                Course[] teacherCourses = getCourses(courseNameArray.length, courseNameArray, courseIDArray, programIDArray, hoursArray, creditsArray);

                Teacher teacher = new Teacher(teacherFirstName, teacherLastName, teacherID, startingDate, teacherCourses);
                teacherList.add(teacher);

            }
        } catch (InputMismatchException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (WrongInputException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveTeacherData() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(DATA_FILE_TEACHERS)) {
            for (Teacher teacher : teacherList) {
                writer.println(teacher);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }


    }

    public static void listAllTeachers() {
        if (teacherList.isEmpty()) {
            throw new RuntimeException("There are no teachers. Please add some.");
        }
        for (Teacher teacher : teacherList) {
            System.out.println(teacher.toDataString());
        }
    }
}

