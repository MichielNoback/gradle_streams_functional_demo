package nl.bioinf.streams_demo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("demo")
public class FunctionalDemo {

    /**
     * This is called a *functional interface* because it has a single non-defauklt method defined.
     * Such interfaces can be used in functional programming settings.
     */
    interface Combiner{
        double combine(int x, int y);
    }

    /**
     * This is an old-school implementation of the interface.
     */
    class AdditionCombiner implements Combiner {
        @Override
        public double combine(int x, int y) {
            return x + y;
        }
    }

    /**
     * nl.bioinf.streams_demo.User of combiner.
     * It has no clue what the combiner is going to do;
     * It only knows the contract will by definition be implemented (otherwise the compiler would have rejected it).
     * @param combiner
     */
    void useAcombiner(Combiner combiner) {
        System.out.println("Class _"
                + combiner.getClass().getSimpleName()
                + "_ combines 2 and 4: "
                + combiner.combine(2, 4));
    }

    @Test
    void additionCombiner() {
        //old school
        useAcombiner(new AdditionCombiner());
    }

    @Test
    void multiplierCombiner() {
        //old school with anonymous inner class
        useAcombiner(new Combiner() {
            @Override
            public double combine(int x, int y) {
                return x * y;
            }
        });
    }

    @Test
    void usingLambdas() {
        //a Lambda implementer of the interface!
        useAcombiner((a, b) -> (double)a/b);
    }
}
