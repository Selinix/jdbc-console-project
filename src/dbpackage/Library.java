package dbpackage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Library {

    private static String getUserInput () {
        System.out.print("Ввод: ");
        return new Scanner(System.in).nextLine();
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        String strSQL;
        ConnectionManager connManager = new ConnectionManager("config.xml");

        int input;
        try (Connection conn = connManager.getConnection()) {
            while (!conn.isClosed()) {
                menu.showMenu();
                try {
                    input = Integer.parseInt(getUserInput());
                }
                catch (NumberFormatException exc) {
                    input = -1;
                }

                switch (input) {
                    case 1: {
                        strSQL = new XMLParser().getQueryFromXML("insert_table.xml");
                        connManager.doSQL(conn, strSQL, false);
                        break;
                    }
                    case 2: {
                        System.out.println("Введите значение поля \"Имя книги\"");
                        String name_book = getUserInput();
                        System.out.println("Введите значение поля \"Имя автора\"");
                        String autor_book = getUserInput();
                        System.out.println("Введите значение поля \"Цена\"");
                        String price_book = getUserInput();
                        strSQL = "insert into BOOKS (NAME_BOOK, AUTOR_BOOK, PRICE_BOOK) values ('" + name_book +"', '" + autor_book + "', " + price_book + ")";
                        connManager.doSQL(conn, strSQL, false);
                        break;
                    }
                    case 3: {
                        strSQL = "SELECT * FROM BOOKS";
                        connManager.doSQL(conn, strSQL, true);
                        break;
                    }
                    case 4: {
                        strSQL = "SELECT * FROM BOOK_SHOPS";
                        connManager.doSQL(conn, strSQL, true);
                        break;
                    }
                    case 5: {
                        strSQL = "SELECT * FROM RECIPES";
                        connManager.doSQL(conn, strSQL, true);
                        break;
                    }
                    case 6: {
                        System.out.println("Введите номер магазина (ПУСТОЙ ВВОД - вывести информацию по всем магазинам)");
                        String book_id = getUserInput();
                        String condition = book_id.equals("") ? "" : " AND book_shops.id_shop = " + book_id;
                        strSQL = "SELECT book_shops.name_shop, book_shops.adres_shop, SUM(books.price_book * recipes.amount)" +
                                "FROM recipes, book_shops, books " +
                                "WHERE book_shops.id_shop = recipes.id_shop AND books.id_book = recipes.id_book" + condition + " " +
                                "GROUP BY book_shops.name_shop, book_shops.adres_shop";
                        connManager.doSQL(conn, strSQL, true);
                        break;
                    }
                    case 0: {
                        conn.close();
                        break;
                    }
                    case -1: {
                        System.out.println("Не корректный ввод");
                        break;
                    }
                    default: {
                        System.out.println("Такой опции нет");
                        break;
                    }
                }
            }
        }
        catch (SQLException exc) {
            exc.getErrorCode();
        }
    }
}