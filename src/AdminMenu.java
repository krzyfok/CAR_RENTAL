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
                            newadminsesion.printusers();
                            break;

                        case 2:
                            newadminsesion.deleteuser();
                            break;
                        case 3:
                            newadminsesion.printcars();
                            break;
                        case 4:
                            newadminsesion.carlist_edit();
                            break;
                        case 5:
                            newadminsesion.print_rentals();
                            break;
                        case 6:

                            break;

                        case 7:
                            newadminsesion.end_rental();
                            break;
                        case 8:

                        break;
                        case 9:
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
            Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=stmt.executeQuery("select * from users WHERE id='" + x + "'");
                if(rs.next()){
                    String query = "delete from users where id = ?";
                    PreparedStatement preparedStmt = con.prepareStatement(query);
                    preparedStmt.setInt(1, x);
                    preparedStmt.execute();
                    System.out.println("SUCCES");
                }
                else {
                    System.out.println("ERROR");
                }


            con.close();
            }
        }
        catch (Exception e){ System.out.println(e);}
    }
    private void printcars()
    {
        try {

            Connection cosn= DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","root");

            Statement stmt=cosn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss=stmt.executeQuery("select * from cars");


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
    private void carlist_edit() {
        try {

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");
            Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            Scanner sc = new Scanner(System.in);

            System.out.println("1. ADD NEW WEHICLE");
            System.out.println("2. DELETE WEHICLE");
            try {

                int x = sc.nextInt();
                switch (x) {
                    case 1:

                            System.out.print("BRAND: ");
                            String brand = sc.next();
                            System.out.print("MODEL: ");
                            String model = sc.next();


                            String sql = "insert into cars(brand,model,status)" + "values(?,?,?)";
                            PreparedStatement preparedStm = con.prepareStatement(sql);

                            preparedStm.setString(1, brand);
                            preparedStm.setString(2, model);
                            preparedStm.setString(3, "FREE");

                            preparedStm.execute();
                            con.close();

                        break;

                    case 2:

                            System.out.print("DELETE VEHICLE, ID:  ");
                            int y = sc.nextInt();

                            String query = "delete from cars where id = ?";
                            ResultSet rs=stmt.executeQuery("select * from cars WHERE id='" + y + "'");
                        if(rs.next()) {
                            PreparedStatement preparedStmt = con.prepareStatement(query);
                            preparedStmt.setInt(1, y);
                            preparedStmt.execute();
                            System.out.println("SUCCES");}

                        else {
                            System.out.println("ERROR");
                        }
                        con.close();
                        break;
                    default:
                        System.out.println("ERROR, TRY AGAIN ");
                }
            } catch (InputMismatchException exception) {
                System.out.println("ERROR, TRY AGAIN");
            }

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }


    private void print_rentals()
    {
        try{ Connection cosn= DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","root");

         Statement stmt=cosn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
         ResultSet rss=stmt.executeQuery("select * from rentals");

         for(int i=0;i<85;i++) System.out.print("-");System.out.print("\n");
         System.out.printf("|%4s|%10s|%7s|%6s|%21s|%21s|%8s|\n","ID ","VEHICLE ID" ,  "USER ID" ,"STATUS", "START DATE     ","END DATE     ","DURATION");
         for(int i=0;i<85;i++) System.out.print("-");System.out.print("\n");
         while (rss.next())
         { System.out.printf("|%-4s|%-10s|%-7s|%-6s|%-21s|%-21s|%8s|\n",rss.getInt(1),rss.getInt(2) ,  rss.getInt(3) , rss.getString(4),rss.getTimestamp(5),rss.getTimestamp(6),rss.getInt(7));
             for(int i=0;i<85;i++) System.out.print("-");System.out.print("\n");
         }

        }
        catch (Exception e) {
         System.out.println(e);
        }
    }
    private static Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new Timestamp(today.getTime());

    }
    private void end_rental()
    {
        try {
            Scanner sc = new Scanner(System.in);
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");

            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            System.out.print("RENTAL ID= ");
            int x =sc.nextInt();
            ResultSet rs = stmt.executeQuery("select * from rentals WHERE id='"+x+"'");
            if(rs.next()&& rs.getString(4).equals("ACTIVE") )
            {

                String query = "update rentals set end_date = ? where id = ?";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setTimestamp  (1, getCurrentTimeStamp());
                preparedStmt.setInt(2, x);
                preparedStmt.executeUpdate();

                query = "update rentals set status = ? where id = ?";
                preparedStmt = con.prepareStatement(query);
                preparedStmt.setString  (1, "ENDED");
                preparedStmt.setInt(2, x);
                preparedStmt.executeUpdate();

                query = "update rentals set duration = ? where id = ?";
                preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1, rs.getTimestamp(6).getDate()-rs.getTimestamp(5).getDate()+1);
                preparedStmt.setInt(2, x);
                preparedStmt.executeUpdate();
            }
            else
            {
                System.out.println("ERROR");
            }

        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

}
