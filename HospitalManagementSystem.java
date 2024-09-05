package HospitalManagementsystem;
import java.sql.*;
import java.util.*;
public class HospitalManagementSystem {

    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username ="root";
    private static final String password = "6305570042";
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");


        }catch(ClassNotFoundException e){
            e.printStackTrace();

        }
        Scanner sc = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient =new Patient(connection, sc);
            Doctor doctor = new Doctor(connection);
            while(true){
                System.out.println("HOSPITAL MANAGEMNET SYSTEM ");
              
                System.out.println("1 . Add patients");
                System.out.println("2. View Patients ");
                System.out.println("3. View Doctors ");
                System.out.println("4. Book Appointment ");
                System.out.println("5 . Exit  ");
                System.out.println("SELECT Your Choice : ");
                int choice = sc.nextInt();
                switch(choice){
                    case 1:  patient.addPatient(); break;
                    case 2:  patient.viewPatient(); break;
                    case 3:doctor.viewDoctors(); 
                    System.out.println(); break;
                    case 4: bookAppointment(patient,doctor,connection,sc); break;
                    case 5:
                    default: System.out.println("SELECT Valid Option..!!");
                }

            }

        }catch(SQLException e){
            e.printStackTrace();

        }
    }
    public static void bookAppointment(Patient patient , Doctor doctor,Connection connection, Scanner sc){
        System.out.println("Enter Patient ID : ");
        int patientId = sc.nextInt();
        System.out.println("Enter Doctor ID : ");
        int doctorId = sc.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD) : ");
        String appointmentDate = sc.nextLine();
        if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)){
            if(checkDoctorAvailability(doctorId,appointmentDate,connection)){
               String query = "INSERT INTO appointments(patientId , doctorId , appointment_date) VALUES(?,?,?)";
               try{
                 PreparedStatement preparedStatement = connection.prepareStatement(query);
                 preparedStatement.setInt(1, patientId);
                 preparedStatement.setInt(2, doctorId);
                 preparedStatement.setString(3, appointmentDate);
                int rowsAffected =  preparedStatement.executeUpdate();
                if(rowsAffected>0){
                    System.out.println("APPOINTMENT BOOKED  SUCCESSFULLY..!");
                }else{
                    System.out.println("FAILED TO BOOK APPOINTMENT..!");
                }

               }catch(SQLException E){
                E.printStackTrace();

               }
            }else{
                System.out.println("Doctor Not Available on the selected Date..!");
            }
        }else{
            System.out.println(" Either Patient Or doctor does not exist");
        }


    }
    public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
        String query = " SELECT COUNT(*) FROM appointments WHERE doctorId = ? AND appointment_date = ?";
        try{
       PreparedStatement preparedStatement = connection.prepareStatement(query);
       preparedStatement.setInt(1, doctorId);
       preparedStatement.setString(2, appointmentDate);
       ResultSet resultSet = preparedStatement.executeQuery();
       if(resultSet.next()){
        int count = resultSet.getInt(1);
        if(count == 0){
            return true;
        }else return false ;
       }
        }catch(SQLException e ){
            e.printStackTrace();;
        }
        return false ;
    }

}
