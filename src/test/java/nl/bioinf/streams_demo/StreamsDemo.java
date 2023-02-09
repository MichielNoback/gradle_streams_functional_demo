package nl.bioinf.streams_demo;

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
        try (Stream<String> stream = Files.lines(Paths.get(demoFile))) {

            stream.//.parallel(). //just as simple!
                    skip(1).
                    map(Case::fromString).
                    limit(10).
                    forEach(c -> System.out.println(c.toCsv()));

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
                    map(Case::fromString).
                    mapToDouble(c -> c.bhr() * c.basebp()).
                    sorted().
                    //peek(System.out::println).
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

            Map<Integer, List<Case>> collect = stream.
                    skip(1).
                    map(Case::fromString).
                    collect(groupingBy(Case::dose));

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

    private GroupAverage collectAverage(int dose, List<Case> cases) {
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

        for (int number: numbers) {
            System.out.println("number = " + number);
        }
    }
}
