package mx.chux.cs.pzl.arrays;

import java.util.logging.Logger;

import mx.chux.cs.pzl.Puzzle;
import mx.chux.cs.pzl.PuzzleExecutor;
import mx.chux.cs.pzl.TimedPuzzleSolution;

import java.util.logging.Level;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {

        final int[] array = new int[] { 1, 2, 3, 4, 5, 6 };

        final Puzzle<Integer> puzzle = MaxSumSubArray.inArray(array, 3);
        final PuzzleExecutor<Integer> executor = PuzzleExecutor.from(puzzle);
        
        TimedPuzzleSolution<Integer> solution = executor.executeTimed(false);
        
        LOGGER.log(Level.INFO, "MaxSumSubArray (bute-force) [time: {0}],[ticks: {1}]: {2}", 
                new Object[] { solution.timeElapsed(), solution.ticks(), solution.get() });
        
        solution = executor.executeTimed(true);
        
        LOGGER.log(Level.INFO, "MaxSumSubArray (optimal) [time: {0}],[ticks: {1}]: {2}", 
                new Object[] { solution.timeElapsed(), solution.ticks(), solution.get() });

    }

}
