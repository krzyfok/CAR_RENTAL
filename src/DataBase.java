import java.util.Scanner;
import java.sql.*;
public class DataBase {

    public static int AdminLog()
    {
        Scanner sc = new Scanner(System.in);



        System.out.print("LOGIN: ");
        String login = sc.next();
        System.out.print("PASSWORD: ");
        String password = sc.next();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","root");

            Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs=stmt.executeQuery("select * from adminlogs WHERE login='" + login + "'");
           while (rs.next()) {
                String _password = rs.getString(2);
               if(password.equals(_password)){
                   return 1;}

           }
           con.close();
        }
        catch(Exception e){ System.out.println(e);}
       return 0;
    }
    public static int UserSignUp()
    {
        Scanner sc = new Scanner(System.in);
        int user_id=
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","root");
            Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            System.out.print("NAME: ");
            String name = sc.next();
            System.out.print("SURNAME: ");
            String surname = sc.next();
            System.out.print("LOGIN: ");
            String login = sc.next();


            ResultSet rs=stmt.executeQuery("select * from users WHERE login='" + login + "'");
            while (rs.next()==true) {
                System.out.println("ENTER DIFERENT LOGIN");
                rs.beforeFirst();
                login= sc.next();
                rs=stmt.executeQuery("select * from users WHERE login='" + login + "'");
            }

            System.out.print("PASSWORD: ");
            String password1 = sc.next();
            System.out.print("RETYPE PASSWORD: ");
            String password2 = sc.next();
            while(password1.equals(password2)==false)
            {
                System.out.print("RETYPE PASSWORD: ");
                password2=sc.next();
            }
            String sql= "insert into users(id,name,surname,login,password)"+ "values(?,?,?,?,?)";
            PreparedStatement preparedStm =con.prepareStatement(sql);
            preparedStm.setInt(1,user_id);
            preparedStm.setString(2,name);
            preparedStm.setString(3,surname);
            preparedStm.setString(4,login);
            preparedStm.setString(5,password1);
            preparedStm.execute();
            user_id++;
            con.close();
        }
        catch(Exception e){ System.out.println(e);}
        return 0;
    }

}
