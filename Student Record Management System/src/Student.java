import java.io.*;
import java.util.*;
class Student{
    private String name;
    private int rollNumber;
    private String grade;
    Student(String name,int rollNumber,String grade){
        this.name=name;
        this.rollNumber=rollNumber;
        this.grade=grade;
    }
    public String getName(){
        return name;
    }
    public int getRollNumber(){
        return rollNumber;
    }
    public String getGrade(){
        return grade;
    }
    void setName(String name){
        this.name=name;
    }
    void setRollNumber(int number){
        this.rollNumber=rollNumber;
    }
    void setGrade(String grade){
        this.grade=grade;
    }
    public String toString(){
        return "Student( name= "+name+","+" rollNumber= "+rollNumber+" grade= "+grade+" )";
    }

}