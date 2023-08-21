import java.util.Scanner;
public class AdminMenu{

        public static void  admin_menu(){
            Scanner scan = new Scanner(System.in);
            while(DataBase.AdminLog()!=1)
            { Menu.clear();
            System.out.println("TRY AGAIN");
           }
            System.out.println("SUCCES");
            Menu.clear();
            menu();
            scan.nextLine();

        }
        public static void menu(){
            System.out.println("MENU");
        }
}
