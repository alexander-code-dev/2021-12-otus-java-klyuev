package homework;


import java.util.*;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    private LinkedHashSet<Customer> set = new LinkedHashSet<>();
    LinkedList<Customer> list = new LinkedList<>();

    public void add(Customer customer) {
        this.set.add(customer);
        list.add(customer);
    }

    public Customer take() {
        //return null; // это "заглушка, чтобы скомилировать"
        return list.pollLast();
    }
}
