package homework;

import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private NavigableMap<Customer, String> map = new TreeMap<>(Comparator.comparing(Customer::getScores));

    private Map.Entry<Customer, String> entryEqualNull(Map.Entry<Customer, String> entry) {
        if (Objects.isNull(entry)) {
            return null;
        }
        Customer newCustomer = new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores());
        return new AbstractMap.SimpleImmutableEntry<>(newCustomer, entry.getValue());
    }

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        //return null; // это "заглушка, чтобы скомилировать"
        Map.Entry<Customer, String> entry = map.firstEntry();
        return entryEqualNull(entry);
/*        if (Objects.isNull(entry)) {
            return null;
        }
        Customer newCustomer = new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores());
        return new AbstractMap.SimpleImmutableEntry<>(newCustomer, entry.getValue());*/
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        //return null; // это "заглушка, чтобы скомилировать"
        Map.Entry<Customer, String> entry = map.higherEntry(customer);
        return entryEqualNull(entry);
/*        if (Objects.isNull(entry)) {
            return null;
        }
        Customer newCustomer = new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores());
        return new AbstractMap.SimpleImmutableEntry<>(newCustomer, entry.getValue());*/
    }

    public void add(Customer customer, String data) {
        this.map.put(customer, data);
    }
}
