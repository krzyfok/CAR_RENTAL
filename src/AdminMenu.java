import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
public class AdminMenu{
    public static int id;
    public static void menu()
    {

        AdminMenu.adminmenu();
    }

        private static void adminmenu()
        {
            boolean exit=false;
            AdminMenu newadminsesion = new AdminMenu();
          while(!exit) {
                System.out.println("ADMIN MENU");
                System.out.println("1. PRINT ALL USERS");
                System.out.println("2. DELETE USER");
                System.out.println("3. PRINT VEHICLE LIST");
                System.out.println("4. EDIT VEHICLE LIST");
                System.out.println("5. PRINT ACTIVE RENTALS");
                System.out.println("6. ADD ADMIN");
                System.out.println("7. LOGOUT");
                try {
                    Scanner sc = new Scanner(System.in);
                    int x = sc.nextInt();

                    switch (x) {
                        case 1:
                            newadminsesion.printusers();
                            break;

                        case 2:
                            newadminsesion.deleteuser();
                            break;
                        case 3:
                            newadminsesion.printcars();
                            break;
                        case 4:

                            break;
                        case 5:

                            break;
                        case 6:
                            break;

                        case 7:
                            exit=true;
                            break;


                        default:
                            System.out.println("ERROR, TRY AGAIN ");
                           // continue;
                    }

                } catch (InputMismatchException exception) {
                    System.out.println("ERROR, TRY AGAIN");
                }
            }
        }

    private void printusers()
    {
        try {

            Connection cosn= DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","root");

            Statement stmt=cosn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss=stmt.executeQuery("select * from users");
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
    private void deleteuser() {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("DELETE USER, ID:  ");
            int x = sc.nextInt();
            if(x==id)
            {
                System.out.println("ERROR");
            }
            else{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");


            String query = "delete from users where id = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, x);
            preparedStmt.execute();

            con.close();
        }}
        catch (Exception e){ System.out.println(e);}
    }
    private void printcars()
    {
        try {

            Connection cosn= DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","root");

            Statement stmt=cosn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss=stmt.executeQuery("select * from cars");
            System.out.println("ID BRAND   MODEL   STATUS  ");


            for(int i=0;i<49;i++) System.out.print("-");System.out.print("\n");
            System.out.printf("|%4s|%10s|%20s|%10s|\n","ID ","BRAND   " ,  "MODEL        " , "STATUS  ");
            for(int i=0;i<49;i++) System.out.print("-");System.out.print("\n");
            while (rss.next())
            { System.out.printf("|%-4s|%-10s|%-20s|%-10s|\n",rss.getInt(1),rss.getString(2) ,  rss.getString(3) , rss.getString(4));
                for(int i=0;i<49;i++) System.out.print("-");System.out.print("\n");
            }
        }
        catch (Exception e){ System.out.println(e);}
    }

}
