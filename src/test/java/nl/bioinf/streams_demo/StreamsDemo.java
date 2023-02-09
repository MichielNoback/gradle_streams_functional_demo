package nl.bioinf.streams_demo;

import com.google.gson.Gson;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

@Tag("demo")
public class StreamsDemo {
    private String demoFile = "data/cardiac.csv";

    @Test
    void readFilterWrite() {
        //read file into stream, try-with-resources
        Gson gson = new Gson();

        try (Stream<String> stream = Files.lines(Paths.get(demoFile))) {

            stream.//.parallel(). //just as simple!
                    skip(1).
                    map(CardiacCase::fromString).
//                    filter(cc -> cc.age() > 50).
                    limit(10).
                    peek(c -> System.out.println(c.toCsv())).
                    map(c -> gson.toJson(c)).
                    forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void mapReduce() {
        try (Stream<String> stream = Files.lines(Paths.get(demoFile))) {
            //get average of bhr*basebp double product
            OptionalDouble average = stream.
                    skip(1).
                    map(CardiacCase::fromString).
                    mapToDouble(c -> c.bhr() * c.basebp()).
                    sorted().
                    average();

            System.out.println("average = " + average.orElse(-1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void mapGroupReduce() {
        /**
         * A tale of three streams.
         * The first collects all cases in a map where the keys are the dose of dobutamine given.
         */
        try (Stream<String> stream = Files.lines(Paths.get(demoFile))) {

            Map<Integer, List<CardiacCase>> collect = stream.
                    skip(1).
                    map(CardiacCase::fromString).
                    collect(groupingBy(CardiacCase::dose));

            System.out.println("doses given: = " + collect.keySet());
            /* The second streams the keys of this map and applies a custom method.
             * which agian applies a stream
             */
            collect.keySet().
                    stream().
                    map(k -> collectAverage(k, collect.get(k))).  //collect average of the group of cases
                    sorted(Comparator.comparing(GroupAverage::dose)). //sort on dose
                    forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GroupAverage collectAverage(int dose, List<CardiacCase> cases) {
        double avg = cases.
                stream().
                mapToDouble(c -> c.bhr() * c.basebp()). //returns the basal double product
                average(). //gives an optional
                orElse(-1);
        return new GroupAverage(dose, avg);
    }

    /**
     * An inner record!
     * All we need is a name and properties
     * @param dose
     * @param average
     */
    record GroupAverage(int dose, double average) { }



    @Test
    void testIntelliJsuggestions() {
        List<Integer> numbers = List.of(2,3,4,5,6,7);

        numbers.
                stream().
                mapToInt(number -> number).
                mapToObj(number -> "number = " + number).
                forEach(System.out::println);
    }
}
