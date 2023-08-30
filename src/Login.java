import java.util.Scanner;
import java.sql.*;
public class Login {

    private int userlogin()
    {
        Scanner sc = new Scanner(System.in);

        String user_type="user";

        System.out.print("LOGIN: ");
        String login = sc.next();
        System.out.print("PASSWORD: ");
        String password = sc.next();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","root");

            Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs=stmt.executeQuery("select * from users WHERE login='" + login + "'");
           while (rs.next()) {
                String _password = rs.getString(5);


               if(password.equals(_password)){

               user_type=rs.getString(6);
               switch (user_type) {
                   case "user":
                      return 1;

                   case "admin":
                       AdminMenu.id=rs.getInt(1);

                       return 2;
                   case "OWNER":
                       AdminMenu.id=rs.getInt(1);
                       return 3;

                   default:
                       System.out.println("ERROR");
                       continue;
               }}
           }
           con.close();
        }
        catch(Exception e){ System.out.println(e);}
       return 0;
    }
    private int usersignup()
    {
        Scanner sc = new Scanner(System.in);

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
            String sql= "insert into users(name,surname,login,password,type)"+ "values(?,?,?,?,?)";
            PreparedStatement preparedStm =con.prepareStatement(sql);

            preparedStm.setString(1,name);
            preparedStm.setString(2,surname);
            preparedStm.setString(3,login);
            preparedStm.setString(4,password1);
            preparedStm.setString(5,"user");
            preparedStm.execute();

            con.close();
        }
        catch(Exception e){ System.out.println(e);}
        return 1;
    }
    public static void login()
    {

        Login newlogin = new Login();
        int x= newlogin.userlogin();
        while(x==0)
        {
            System.out.println("TRY AGAIN");
            Menu.clear();
            x= newlogin.userlogin();
        }
        System.out.println("SUCCES");
        Menu.clear();

        switch (x) {
            case 1:
                UserMenu.menu();
                break;
            case 2:
                AdminMenu.menu();
                break;
            case 3:
                OwnerMenu.menu();
                break;

            default:
                System.out.println("ERROR");

        }



    }

    public static void user_sign_up()
    {
        Login newsignup = new Login();
        if(newsignup.usersignup()==1);
        {
            Menu.clear();
            UserMenu usermenu =new UserMenu();
            login();
        }

    }
}
