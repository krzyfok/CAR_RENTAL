import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserMenu implements Rentals_details{
    public static int user_id;

    public static void menu()
    {
        UserMenu Usermennu = new UserMenu();
        Usermennu.usermenu();
    }
    private static void usermenu()
    {
        boolean exit=false;

        while(!exit) {
            System.out.println("USER MENU");
            System.out.println("1. AVAILABLE VEHICLES");
            System.out.println("2. RENT VEHICLE");
            System.out.println("3. YOUR ACTIVE RENTALS");
            System.out.println("4. END RENTAL");
            System.out.println("5. PRINT RENTALS HISTORY");
            System.out.println("6. LOGOUT");
            try {
                Scanner sc = new Scanner(System.in);
                int x = sc.nextInt();

                switch (x) {
                    case 1:
                    print_available_vehicles();
                        break;

                    case 2:
                    rent_vehicle_select();
                        break;
                    case 3:
                        print_active_renatls();
                        break;
                    case 4:
                        end_rental();
                        break;
                    case 5:
                        print_rentals_history();
                        break;
                    case 6:
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
    protected  static void print_available_vehicles()
    {
        try {

            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","root");

            Statement stmt=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss=stmt.executeQuery("select * from cars WHERE STATUS= 'FREE'");


            for(int i=0;i<38;i++) System.out.print("-");System.out.print("\n");
            System.out.printf("|%4s|%10s|%20s|\n","ID ","BRAND   " ,  "MODEL        " );
            for(int i=0;i<38;i++) System.out.print("-");System.out.print("\n");
            while (rss.next())
            { System.out.printf("|%-4s|%-10s|%-20s|\n",rss.getInt(1),rss.getString(2) ,  rss.getString(3) );
                for(int i=0;i<38;i++) System.out.print("-");System.out.print("\n");
            }
            con.close();
        }
        catch (Exception e){ System.out.println(e);}
    }

    protected static void rent_vehicle_select()
    {
        try {
            Scanner sc = new Scanner(System.in);

            print_available_vehicles();
            System.out.print("SELECT VEHICLE, ID: ");
            int id = sc.nextInt();
            rent_vehicle(id);

        }
        catch (InputMismatchException exception) {
            System.out.println("ERROR, TRY AGAIN");
        }


    }
    protected static void rent_vehicle(int id)
    {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select * from cars WHERE id='" + id + "' AND status='FREE'");
            if(rs.next())
            {
                String query = "update cars set status = ? where id = ?";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString  (1, "RENTED");
                preparedStmt.setInt(2, id);
                preparedStmt.executeUpdate();
                adding_new_rental(id);
                System.out.println("SUCCES");
            }
            else {
                System.out.print("ERROR, WRONG VEHICLE");
            }
            con.close();
        }
        catch(Exception e){ System.out.println(e);}
    }
    private  static Timestamp getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        return new Timestamp(today.getTime());

    }
    protected  static void adding_new_rental(int vehicle_id)
    {
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");

            String sql= "insert into rentals(vehicle_id,user_id,status,start_date)"+ "values(?,?,?,?)";
            PreparedStatement preparedStm =con.prepareStatement(sql);

            preparedStm.setInt(1,vehicle_id);
            preparedStm.setInt(2,user_id);
            preparedStm.setString(3,"ACTIVE");
            preparedStm.setTimestamp(4,getCurrentTimeStamp());
            preparedStm.execute();

            con.close();
        }
        catch (Exception e){System.out.println(e);}
    }

    protected static  void end_rental()
    {
        try {
            Scanner sc = new Scanner(System.in);
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");

            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            System.out.print("RENTAL ID= ");
            int x =sc.nextInt();
            ResultSet rs = stmt.executeQuery("select * from rentals WHERE id='"+x+"' AND user_id='"+user_id+"'");
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
                preparedStmt.setInt(1, getCurrentTimeStamp().getDate()-rs.getTimestamp(5).getDate()+1);
                preparedStmt.setInt(2, x);
                preparedStmt.executeUpdate();




                    query = "update cars set status = ? where id =?";
                    preparedStmt = con.prepareStatement(query);
                    preparedStmt.setString  (1, "FREE");
                    preparedStmt.setInt(2, rs.getInt(2));
                    preparedStmt.executeUpdate();



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
    private static void print_active_renatls()
    {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");

            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss = stmt.executeQuery("select * from rentals WHERE user_id='"+user_id+"' AND status='ACTIVE'");
            while (rss.next())
            {
                int vehicle_id= rss.getInt(2);
                String status= rss.getString(4);
                Timestamp start= rss.getTimestamp(5);



                for(int i=0;i<64;i++) System.out.print("-");System.out.print("\n");
                System.out.printf("|%4s|%6s|%9s|%9s|%21s|%8s|\n","ID ","STATUS","CAR BRAND", "CAR MODEL","START DATE     ","DURATION");
                for(int i=0;i<64;i++) System.out.print("-");System.out.print("\n");
                System.out.printf("|%4s|%6s|%9s|%9s|%21s|%8s|\n",rss.getInt(1),status ,Rentals_details.get_car_brand(vehicle_id),Rentals_details.get_car_model(vehicle_id),start,getCurrentTimeStamp().getDate()-start.getDate()+1);
                for(int i=0;i<64;i++) System.out.print("-");System.out.print("\n");
            }


            con.close();


        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    protected static void print_rentals_history()
    {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental", "root", "root");

            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss = stmt.executeQuery("select * from rentals WHERE user_id='"+user_id+"'");



                for(int i=0;i<92;i++) System.out.print("-");System.out.print("\n");
                System.out.printf("|%10s|%6s|%9s|%9s|%21s|%21s|%8s|\n","RENTAL ID","STATUS" ,"CAR BRAND", "CAR MODEL","START DATE     ","END DATE     ","DURATION");
                for(int i=0;i<92;i++) System.out.print("-");System.out.print("\n");
            while(rss.next())
            {
                int vehicle_id= rss.getInt(2);


                String status= rss.getString(4);
                Timestamp start= rss.getTimestamp(5);
                Timestamp end= rss.getTimestamp(6);
                int duration = rss.getInt(7);
                System.out.printf("|%10s|%6s|%9s|%9s|%21s|%21s|%8s|\n",rss.getInt(1),status,Rentals_details.get_car_brand(vehicle_id), Rentals_details.get_car_model(vehicle_id),start,end,duration);
                for(int i=0;i<92;i++) System.out.print("-");System.out.print("\n");
            }


            con.close();


        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
