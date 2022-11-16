public class Course {
    private String courseName;
    private String courseID;
    private String programID;
    private int hours;
    private double credit;
    public String getCourseName() {
        return courseName;
    }

    public Course() {
    }

    public Course(String courseName, String courseID, String programID, int hours, double credit) throws WrongInputException {
        if (courseID.length()>10) {
            throw new WrongInputException("course ID length is maximum 10 chars !");
        }
       // if (!programID.startsWith("FSD")) {
       //     throw new WrongInputException("Wrong ProgramID, Not Start With \"FSD\"  letter");
       // }
        if (credit>=10 ||credit < 2){
            throw new WrongInputException("Input course.credit data is wrong");
        }
        if (courseName.length()>50){
            throw  new WrongInputException("course name is too long and more than 50 chars!");
        }
        if (hours>300 ||hours<10){
            throw new WrongInputException("Input course.hour data is wrong");
        }
        this.courseName = courseName;
        this.courseID = courseID;
        this.programID = programID;
        this.hours = hours;
        this.credit = credit;
    }



    public void setCourseName(String courseName) throws WrongInputException {
        if (courseName.length()>50){
            throw  new WrongInputException("course name is too long and more than 50 chars!");
        }
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) throws WrongInputException {
        if (courseID.length()>10){
            throw  new WrongInputException("course ID length is maximum 10 chars !");
    }
        this.courseID = courseID;
    }

    public String getProgramID() {
        return programID;
    }

    /***
     * **********************************************************************************
     * * Explain : Now, we only have and limit to one program--FSD001 program
     * @param programID
     * @throws WrongInputException
     */
    public void setProgramID(String programID) throws WrongInputException {
    //    if (!programID.startsWith("FSD")) {
    //       throw new WrongInputException("Wrong ProgramID, Not Start With \"FSD\"  letter");
    //    }
        this.programID = programID;
    }



    public int getHours() {
        return hours;
    }

    public void setHours(int hours) throws WrongInputException {
        if (hours>300 ||hours<10){
            throw new WrongInputException("Input course.hour data is wrong");
        }
        this.hours = hours;
    }

    public double getCredit() {
        return credit;
    }
    public void setCredit(double credit) throws WrongInputException {
        if (credit>=10 ||credit < 2){
            throw new WrongInputException("Input course.credit data is wrong");
        }
        this.credit = credit;
    }

    @Override
    public String toString() {
            return getProgramID() + ";" + getCourseID() + ";" + getCourseName() + ";" + getCredit() + ";" + getHours();
        }
}
