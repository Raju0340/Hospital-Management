package HospitalManagementsystem;
import java.sql.*;
public class Doctor {

    private Connection connection;
   public Doctor( Connection connection){
    this.connection = connection;
   }

   public void viewDoctors(){
    String query = "SELECT * FROM doctors";
    try{
       PreparedStatement preparedStatement = connection.prepareStatement(query);
       ResultSet resultSet = preparedStatement.executeQuery();
       System.out.println("Doctors's Data :");
       System.out.println("+-------------+------------------+----------------------+");
       System.out.println("| Doctor ID   |  Name            |  specialization      |");
       System.out.println("+-------------+------------------+----------------------+");
       while(resultSet.next()){
        int id = resultSet.getInt("id");
         String name = resultSet.getString("name");
         String specialization = resultSet.getString("specialization");
         System.out.printf("|%-13s|%-18s|%-22s|\n",id,name,specialization);
         System.out.println("+-------------+------------------+----------------------+");
       }

    }
    catch(SQLException e ){
        e.printStackTrace();
    }
   }
   public boolean getDoctorById(int id){
    String query = "SELECT * FROM doctors WHERE id = ?";
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
