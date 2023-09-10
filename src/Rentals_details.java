import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public interface Rentals_details {
    static String get_car_brand(int id)
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
     static String get_car_model(int id)
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

}
