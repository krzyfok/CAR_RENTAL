import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OwnerMenu extends AdminMenu{
    public static void menu(){

        OwnerMenu owner = new OwnerMenu();
        owner.adminmenu();
    }
@Override
    protected void adminmenu()
    {
        boolean exit=false;

        while(!exit) {
            System.out.println("ADMIN MENU");
            System.out.println("1. PRINT ALL USERS");
            System.out.println("2. DELETE USER");
            System.out.println("3. PRINT VEHICLE LIST");
            System.out.println("4. EDIT VEHICLE LIST");
            System.out.println("5. PRINT RENTALS HISTORY");
            System.out.println("6. RENTAL DETAILS");
            System.out.println("7. END RENTAL");
            System.out.println("8. ADD ADMIN");
            System.out.println("9. LOGOUT");
            try {
                Scanner sc = new Scanner(System.in);
                int x = sc.nextInt();

                switch (x) {
                    case 1:
                        printusers();
                        break;

                    case 2:
                        deleteuser();
                        break;
                    case 3:
                        printcars();
                        break;
                    case 4:
                        carlist_edit();
                        break;
                    case 5:
                        print_rentals();
                        break;
                    case 6://sprawdzic
                        rentals_details();
                        break;
                    case 7:
                        end_rental();
                        break;
                    case 8:
                        add_admin();
                        break;
                    case 9:
                        exit=true;
                        break;


                    default:
                        System.out.println("ERROR, TRY AGAIN ");
                }

            } catch (InputMismatchException exception) {
                System.out.println("ERROR, TRY AGAIN");
            }
        }

    }
    private static void add_admin()
    {
        try{
            Scanner sc= new Scanner(System.in);

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","root");
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
            preparedStm.setString(5,"admin");
            preparedStm.execute();

            con.close();


        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
    protected static void printusers()
    {
        try {

            Connection cosn= DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","root");

            Statement stmt=cosn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss=stmt.executeQuery("select * from users ");
            for(int i=0;i<61;i++) System.out.print("-");System.out.print("\n");
            System.out.printf("|%4s|%10s|%10s|%10s|%10s|%10s|\n","ID ","NAME   " ,  "SURNAME " , "LOGIN   ",   "PASSWORD " ,  "TYPE   ");
            for(int i=0;i<61;i++) System.out.print("-");System.out.print("\n");
            while (rss.next())
            { System.out.printf("|%-4s|%-10s|%-10s|%-10s|%-10s|%-10s|\n",rss.getInt(1),rss.getString(2) ,  rss.getString(3) , rss.getString(4),   rss.getString(5) ,  rss.getString(6));
                for(int i=0;i<61;i++) System.out.print("-");System.out.print("\n");
            }

        }
        catch (Exception e){ System.out.println(e);}
    }

}
