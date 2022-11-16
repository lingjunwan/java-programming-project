import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Program {
    private String programID;
    private String programName;
    private String programDescription;
    private ArrayList<String> courseIDList;
    //   private ArrayList<String> openingCourseIDList;
    private ArrayList<Course> courseList;
    //   private ArrayList<OpeningCouse> openingCourseList;


    public Program() {

    }

    public Program(String programID, String programName, String programDescription, ArrayList<String> courseIDList) {
        try {
            this.setProgramID(programID);
            this.setProgramName(programName);
            this.setProgramDescription(programDescription);
            this.setCourseIDList(courseIDList);
            //      this.setOpeningCourseIDList(openingCourseIDList);   ///wrong
        } catch (WrongInputException e) {
            System.out.println("Wrong Data for New Program :" + e.getMessage());
        }

    }

    public String getProgramID() {
        return programID;
    }

    public void setProgramID(String programID) throws WrongInputException {
        if (!programID.startsWith("FSD")) {
            throw new WrongInputException("Wrong Program Name, Not Start With \"FSD\"  letter");
        }
        this.programID = programID;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) throws WrongInputException {
        if (programName.length() > 50)
            throw new WrongInputException("Program name is more than 50 chars.");
        this.programName = programName;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) throws WrongInputException {
        if (programDescription.length() > 100)
            throw new WrongInputException("Program description is more than 100 chars.");

        this.programDescription = programDescription;
    }

    public ArrayList<String> getCourseIDList() {

        return courseIDList;
    }

    public void setCourseIDList(ArrayList<String> courseIDList) throws WrongInputException {
        if (courseIDList.size() == 0) {
            throw new WrongInputException("Your course list is null, please check it!");
        }
        this.courseIDList = courseIDList;
    }

    public ArrayList<Course> getCourseList() {


        return this.courseList;
    }

    public void setCourseList(ArrayList<Course> courseList) {
        this.courseList = courseList;
    }

    public Course getCourseByCourseID(String courseID) throws NoFoundDataException {
        List<Course> collect = courseList.stream().filter(p -> p.getCourseID().equals(courseID) == true).collect(Collectors.toList());
        if (collect.size() == 0) {
            throw new NoFoundDataException("The CourseID doesn't exist in this system,  exit to Main Menu now!");
        } else {
            Course course = collect.get(0);
            return course;
        }
    }

    public ArrayList<Course> queryCourseListForAProgram() {
        return this.courseList;
    }

    public void modifyCourseForAProgram(String courseID, String courseName, int hour, double credit) throws WrongInputException {
        Course modCourse = null;
        try {
            modCourse = getCourseByCourseID(courseID);
        } catch (NoFoundDataException e) {
            System.out.println(e.getMessage());
        }
        Course newCourse = new Course(courseName, courseID, programID, hour, credit);
        if (courseList.contains(modCourse)) {
            courseList.remove(modCourse);
            courseList.add(newCourse);
        }

    }

    public void addCourseForAProgram(String ProgramID, String courseID, String courseName, int hour, double credit) {
        Course course = null;
        try {
            course = new Course(courseName, courseID, "FSD001", hour, credit);
        } catch (WrongInputException e) {
            System.out.println(e.getMessage());
        }
        this.courseList.add(course);
    }

    public void removeCourseForAProgram(Course course) {
        if (courseList.contains(course)) {
            courseList.remove(course);
        }

        // for (Course course : courseList) {
        //     List<Course> collect = courseList.stream().filter(p -> courseID.equals(p.getCourseID()) == false).collect(Collectors.toList());
        //     courseList = (ArrayList<Course>) collect;
        // }


    }


    @Override
    public String toString() {
        return "Program{" +
                "programID='" + programID + '\'' +
                ", programName='" + programName + '\'' +
                ", programDescription='" + programDescription + '\'' +
                ", courseIDList=" + courseIDList +
                '}';
    }


}