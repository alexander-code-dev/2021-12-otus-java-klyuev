package homework;


import java.util.*;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"
    private Deque<Customer> list = new LinkedList<>();

    public void add(Customer customer) {
        list.add(customer);
    }

    public Customer take() {
        //return null; // это "заглушка, чтобы скомилировать"
        return list.pollLast();
    }
}
