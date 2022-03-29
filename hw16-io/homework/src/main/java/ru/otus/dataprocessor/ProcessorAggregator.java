package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.*;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value

        Map<String, Double> measurementGroup = new HashMap<>();
        String name = null;
        double sumValue = 0D;
        List<Measurement> compareData = this.doCompareList(data);

        for (int i = 0; data.size() > i; i++) {
            if (compareData.get(i).getName().equals(name) || name == null) {
                sumValue+=compareData.get(i).getValue();
                if (data.size()-1 == i) {
                    measurementGroup.put(name, sumValue);
                }
            } else {
                measurementGroup.put(name, sumValue);
                sumValue = 0D;
                sumValue+=compareData.get(i).getValue();
            }
            name = compareData.get(i).getName();
        }
        return measurementGroup.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k,v) -> k, LinkedHashMap::new));
    }

    private List<Measurement> doCompareList(List<Measurement> measurements) {
        measurements.sort(new Comparator<Measurement>() {
            @Override
            public int compare(Measurement o1, Measurement o2) {
                if (Objects.equals(o1.getName(), o2.getName())) {
                    return 0;
                }
                return Objects.hash(o1.getName()) < Objects.hash(o2.getName()) ? -1 : 1;
            }
        });
        return measurements;
    }
}
