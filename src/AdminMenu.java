import java.sql.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
public class AdminMenu{
    public static int admin_id;
    public static void menu(){

        AdminMenu admin = new AdminMenu();
        admin.adminmenu();
    }

        protected  void adminmenu()
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
                System.out.println("8. LOGOUT");
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

    protected static void printusers()
    {
        try {

            Connection cosn= DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","root");

            Statement stmt=cosn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss=stmt.executeQuery("select * from users WHERE type = 'user'");
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
    protected  static void deleteuser() {

            try {
                Scanner sc = new Scanner(System.in);

                System.out.print("DELETE USER, ID:  ");
                int x = sc.nextInt();
                if (x == admin_id) {
                    System.out.println("YOU CANT DELETE YOUR ACCOUNT");
                } else {
                    try {
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");
                        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        ResultSet rs = stmt.executeQuery("select * from users WHERE id='" + x + "'");
                        if (rs.next() && !rs.getString(6).equals("OWNER") && !rs.getString(6).equals("admin")) {
                            String query = "delete from users where id = ?";
                            PreparedStatement preparedStmt = con.prepareStatement(query);
                            preparedStmt.setInt(1, x);
                            preparedStmt.execute();
                            System.out.println("SUCCES");
                        } else {
                            System.out.println("YOU CANT DELETE THIS USER");
                        }


                        con.close();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
            catch (InputMismatchException exception) {
                System.out.println("ERROR, TRY AGAIN");
            }

    }
    protected  static void printcars()
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
    protected static void carlist_edit() {
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

                        if(rs.next()&&(rs.getString(4).equals("FREE"))) {
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


    protected static void print_rentals()
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
            cosn.close();
        }
        catch (Exception e) {
         System.out.println(e);
        }
    }
    private  static Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new Timestamp(today.getTime());

    }
    protected static  void end_rental()
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
                try {



                    query = "update cars set status = ? where id =?";
                    preparedStmt = con.prepareStatement(query);
                    preparedStmt.setString  (1, "FREE");
                    preparedStmt.setInt(2, rs.getInt(2));
                    preparedStmt.executeUpdate();
                    con.close();
                }
                catch (Exception e) {
                    System.out.println(e);
                }
                con.close();
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
    protected static void rentals_details()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("ID: ");
        int rental_id= sc.nextInt();
    try {
         Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");

         Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
         ResultSet rss = stmt.executeQuery("select * from rentals WHERE id='"+rental_id+"'");
         if(rss.next())
         {
             int vehicle_id= rss.getInt(2);

             int user_id=rss.getInt(3);
             String status= rss.getString(4);
             Timestamp start= rss.getTimestamp(5);
             Timestamp end= rss.getTimestamp(6);
             int duration = rss.getInt(7);

             for(int i=0;i<115;i++) System.out.print("-");System.out.print("\n");
             System.out.printf("|%10s|%6s|%9s|%12s|%9s|%9s|%21s|%21s|%8s|\n","RENTAL ID","STATUS","USER NAME" ,  "USER SURNAME" ,"CAR BRAND", "CAR MODEL","START DATE     ","END DATE     ","DURATION");
             for(int i=0;i<115;i++) System.out.print("-");System.out.print("\n");
             System.out.printf("|%10s|%6s|%9s|%12s|%9s|%9s|%21s|%21s|%8s|\n",rental_id,status,get_user_name(user_id) ,  get_user_surname(user_id) ,get_car_brand(vehicle_id), get_car_model(vehicle_id),start,end,duration);
             for(int i=0;i<115;i++) System.out.print("-");System.out.print("\n");
         }

         else {
             System.out.print("ERROR");

         }
        con.close();


        }
    catch (Exception e) {
        System.out.println(e);
    }
    }
    private  static String get_car_brand(int id)
    {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");

            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss = stmt.executeQuery("select * from cars WHERE id='"+id+"'");
            rss.next();
            String brand=rss.getString(2);
            con.close();
            return brand;
        }
        catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }
    }
    private static String get_car_model(int id)
    {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");

            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss = stmt.executeQuery("select * from cars WHERE id='"+id+"'");
            rss.next();
            String model=rss.getString(3);
            con.close();
            return model;
        }
        catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }
    }
    private static String get_user_name(int id)
    {
        try{
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");

        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rss = stmt.executeQuery("select * from users WHERE id='"+id+"'");
        rss.next();
        String name=rss.getString(2);
        con.close();
        return name;
        }
        catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }
    }
    private static String get_user_surname(int id)
    {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");

            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss = stmt.executeQuery("select * from users WHERE id='"+id+"'");
            rss.next();
            String surname=rss.getString(3);
            con.close();
            return surname;
        }
        catch (Exception e) {
            System.out.println(e);
            return "ERROR";
        }
    }


    }



