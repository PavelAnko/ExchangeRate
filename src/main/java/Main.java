import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Utils.loadStatistic();
        CurrencyUnitDao cud = Utils.getCurrencyUnitDao();
        int choice;
        do {
            System.out.println("Що ви хочете зробити зі статистикою :\n" +
                    "1. Отримати середній курс за період.\n" +
                    "2. Отримати курс з дати. Вона має бути вказана між початковою та кінцевою датою.\n" +
                    "3. Отримати максимальний курс за період.\n" +
                    "0. Щоб закінчити виконання.");
            choice = Integer.parseInt((scanner.nextLine()));
            switch (choice){
                case 1:
                    Utils.printQueryResult(cud.getAverageCurrency(), "Середній кірс: ");
                    break;
                case 2:
                    System.out.println("Введіть конкретну дату (yyyyMMdd)");
                    String targetDate = scanner.nextLine();
                    Utils.printQueryResult(cud.getCurrencyFromDate(targetDate), "Курс з дати: ");
                    break;
                case 3:
                    Utils.printQueryResult(cud.getMaxCurrency(), "Максимальна курс: ");
                    break;
                case 0:
                    choice = 0;
                    break;
                default:
                    System.out.println("Введіть інше значення!");
                    break;
            }
        }
        while (choice != 0);
    }
}
