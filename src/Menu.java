import java.util.*;
class Menu {
    public static void MainMenu()
    {
while (true){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("1. LOGIN");
            System.out.println("2.SIGN UP");
            System.out.println("3. ADMIN");
            try {
                Scanner sc = new Scanner(System.in);
                int x = sc.nextInt();

                switch (x) {
                    case 1:
                        System.out.println("1");
                        break;
                    case 2:
                        Menu.clear();
                        UserMenu.user_sign_up();
                        break;
                    case 3:

                        Menu.clear();
                        AdminMenu.admin_menu();

                        break;
                    default:
                        System.out.println("ERROR, TRY AGAIN ");
                        continue;
                }
               // sc.close();
            } catch (InputMismatchException exception) {
                System.out.println("ERROR, TRY AGAIN");
            }
        }}

    public static void clear()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


}

