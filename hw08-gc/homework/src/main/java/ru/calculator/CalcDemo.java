package ru.calculator;


/*
-Xms256m
-Xmx256m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/heapdump.hprof
-XX:+UseG1GC
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m

Дефолтные запуски с разной кучей [размер Мб - статистика]:
128 - spend msec:10867, sec:10
256 - spend msec:11315, sec:11
384 - spend msec:10486, sec:10
512 - spend msec:10408, sec:10
640 - spend msec:10236, sec:10
768 - spend msec:13415, sec:13
896 - spend msec:10345, sec:10
1024 - spend msec:10948, sec:10
1152 - spend msec:10847, sec:10
1280 - spend msec:11067, sec:11
1408 - spend msec:10931, sec:10
1536 - spend msec:11410, sec:11
1664 - spend msec:10575, sec:10
1792 - spend msec:10344, sec:10
1920 - spend msec:10662, sec:10
2048 - spend msec:10834, sec:10

Поменян тип данных на простой тип long, и пара примеров запуска с разной кучей [размер Мб - статистика]:
64 -  spend msec:3248, sec:3
128 - spend msec:2955, sec:2
256 - spend msec:2971, sec:2
384 - spend msec:2925, sec:2
512 - spend msec:2968, sec:2
*/

import java.time.LocalDateTime;

public class CalcDemo {
    public static void main(String[] args) {
        long counter = 100_000_000;
        var summator = new Summator();
        long startTime = System.currentTimeMillis();

        for (var idx = 0; idx < counter; idx++) {
            var data = new Data(idx);
            summator.calc(data);

            if (idx % 10_000_000 == 0) {
                System.out.println(LocalDateTime.now() + " current idx:" + idx);
            }
        }

        long delta = System.currentTimeMillis() - startTime;
        System.out.println(summator.getPrevValue());
        System.out.println(summator.getPrevPrevValue());
        System.out.println(summator.getSumLastThreeValues());
        System.out.println(summator.getSomeValue());
        System.out.println(summator.getSum());
        System.out.println("spend msec:" + delta + ", sec:" + (delta / 1000));
    }
}
