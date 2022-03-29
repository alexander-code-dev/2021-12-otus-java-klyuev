package ru.solid.runner;

//import ru.solid.Impl.AtmImpl;

import ru.solid.Atm;
import ru.solid.Cash;
import ru.solid.Impl.AtmImpl;
import ru.solid.Impl.CashImpl;

public class AtmRunner {
    public static void main(String... arg) {
        // запускаем АТМ
        Atm atm = new AtmImpl();
        // достаем разные купюры, собираем в пачку
        Cash cash = new CashImpl.Banknotes()
                .cellCash(2000, 4)
                .cellCash(50, 4)
                .cellCash(200, 5)
                .build();
        // сумма пачки
        System.out.println("сумма пачки: "+cash.getSum());
        // кладем пачку в АТМ
        atm.putCash(cash);
        // деньги в АТМ, проверяем баланс
        System.out.println("деньги в АТМ, проверяем баланс: "+atm.getBalance());
        // заберем деньги из АТМ
        System.out.println("Запрашиваем: "+1100);
        Cash cashOut = atm.takeCash(1100);
        // смотрим что запрошенная сумма соотносится с выданной
        System.out.println("АТМ выдал: "+cashOut.getSum());
        // Проверяем баланс, он уменьшился на выданную сумму 1100
        System.out.println("Проверяем баланс, он уменьшился на выданную сумму 1100: "+atm.getBalance());
    }
}
