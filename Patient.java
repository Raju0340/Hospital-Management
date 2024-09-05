package HospitalManagementsystem;
import java.sql.*;
import java.util.Scanner;
import java.lang.*;
public class Patient {

    private Connection connection;
    private Scanner scanner;
   public Patient( Connection connection , Scanner scanner){
    this.connection = connection;
    this.scanner= scanner;
   }

   public void addPatient(){
    System.out.println("Enter the Patient Name : ");
    String name = scanner.next();
    System.out.println("Enter Patient's age : ");
    int age = scanner.nextInt();
    System.out.println("Enter Patient's Gender : ");
    String gender = scanner.next();
    try{
       String query = "INSERT INTO patients(name,age,gender) VALUES (?,?,?)";
       PreparedStatement preparedStatement = connection.prepareStatement(query);
       preparedStatement.setString(1,name);
       preparedStatement.setInt(2, age);
       preparedStatement.setString(3, gender);
       int affectedRows = preparedStatement.executeUpdate();
       if(affectedRows>0){
        System.out.println("Patient Added Successully !");
       }else{
        System.out.println("Failed to Add Patient ");
       }
    }catch(SQLException e){
       e.printStackTrace();
    }
   }
   public void viewPatient(){
    String query = "SELECT * FROM patients";
    try{
       PreparedStatement preparedStatement = connection.prepareStatement(query);
       ResultSet resultSet = preparedStatement.executeQuery();
       System.out.println("Patient's Data :");
       System.out.println("+-------------+------------------+-----------+----------------+");
       System.out.println("| Patient ID  |  Name            |    Age    | Gender         |");
       System.out.println("+-------------+------------------+-----------+----------------+");
       while(resultSet.next()){
        int id = resultSet.getInt("id");
         String name = resultSet.getString("name");
         String age = resultSet.getString("age");
         String gender = resultSet.getString("gender");
         System.out.printf("|%-13s| %-19s | %-11s| %-14s|\n",id,name,age,gender);
         System.out.println("+-------------+------------------+-----------+----------------+");
       }

    }
    catch(SQLException e ){
        e.printStackTrace();
    }
   }
   public boolean getPatientById(int id){
    String query = "SELECT * FROM patients WHERE id = ?";
    try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
      ResultSet resultSet =  preparedStatement.executeQuery();
      if(resultSet.next()){
            return true;}
        else {
            return false;
        }
    }
    catch(SQLException e ){
        e.printStackTrace();
    }
    return false;
   }
}
