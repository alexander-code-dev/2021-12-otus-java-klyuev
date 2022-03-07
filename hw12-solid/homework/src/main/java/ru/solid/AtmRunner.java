package ru.solid;

import ru.solid.Impl.AtmImpl;
import ru.solid.Impl.Cash;

public class AtmRunner {
    public static void main(String... arg) {

        // достаем разные купюры, собираем в пачку
        Cash moneyIn = new Cash.СhooseBanknotes().fiftyRurCount(100).set();
        Atm atm = new AtmImpl();
        // кладем пачку в АТМ
        atm.giveCash(moneyIn);

        // баланс
        System.out.println("Остаток в ATM: " + atm.getBalance());
        System.out.println("Остаток в ATM (подробно): " + atm);

        // запрашиваем купюры
        Cash moneyOut = atm.takeCash(150);
        System.out.println("Выданные деньги сумма: " + moneyOut.getSum());
        System.out.println("Выданные деньги (подробно): " + moneyOut);
        // баланс
        System.out.println("Остаток в ATM: " + atm.getBalance());
        System.out.println("Остаток в ATM (подробно): " + atm);
    }
}
