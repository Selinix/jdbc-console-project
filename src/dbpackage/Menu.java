package dbpackage;

import java.util.ArrayList;

public class Menu {
    private ArrayList<String> menu = new ArrayList<>();

    Menu () {
        menu.add("1. Добавить данные в таблицу \"Книги\" из XML файла");
        menu.add("2. Занести данные  в таблицу \"Книги\" вручную");
        menu.add("3. Показать таблицу книги");
        menu.add("4. Показать таблицу магазины");
        menu.add("5. Показать таблицу чеки");
        menu.add("6. Вывод сводной информации вида \"Название магазина\", \"Адрес\", \"Общая сумма заказа\"");
        menu.add("0. Выход");
    }

    void showMenu () {
        System.out.println();
        for (String item : menu) {
            System.out.println(item);
        }
    }
}
