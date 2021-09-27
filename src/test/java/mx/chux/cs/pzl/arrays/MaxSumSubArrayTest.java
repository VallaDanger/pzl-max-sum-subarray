package mx.chux.cs.pzl.arrays;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import mx.chux.cs.pzl.PuzzleExecutor;
import mx.chux.cs.pzl.TimedPuzzleSolution;

public class MaxSumSubArrayTest {
    
    private static int[] array;
    
    @BeforeClass
    public static void init() {
        array = new int[] { 1, 2, 3, 4, 5, 6 };
    }

    private void assertSolution(final int windowSize, final int expectedSolution, final int ticks) {
        final MaxSumSubArray puzzle = MaxSumSubArray.inArray(array, 3);
        final PuzzleExecutor<Integer> executor = PuzzleExecutor.from(puzzle);
        
        final TimedPuzzleSolution<Integer> optimalSolution = executor.executeTimed(true);
        
        assertThat(optimalSolution.get()).isEqualTo(Integer.valueOf(expectedSolution));
        
        assertThat(optimalSolution.get()).isEqualTo(puzzle.bruteForceSolution());
        assertThat(optimalSolution.get()).isEqualTo(puzzle.anotherOptimalSolution());
    }

    @Test
    public void basicTest() {
        assertSolution(3, 15, 3);
    }

}
