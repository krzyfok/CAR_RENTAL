import java.util.*;
class Menu {
    public static void MainMenu()
    {
while (true){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("1. LOGIN");
            System.out.println("2.SIGN UP");

            try {
                Scanner sc = new Scanner(System.in);
                int x = sc.nextInt();

                switch (x) {
                    case 1:
                        Menu.clear();
                        Login.login();
                        break;
                    case 2:
                        Menu.clear();
                        Login.user_sign_up();
                        break;

                    default:
                        System.out.println("ERROR, TRY AGAIN ");
                        continue;
                }

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

