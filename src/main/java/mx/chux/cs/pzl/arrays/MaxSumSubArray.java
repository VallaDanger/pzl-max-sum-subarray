package mx.chux.cs.pzl.arrays;

import mx.chux.cs.pzl.Puzzle;

public class MaxSumSubArray extends Puzzle<Integer> {

    public static MaxSumSubArray inArray(final int[] array, int size) {
        return new MaxSumSubArray(array, size);
    }

    final int[] array;
    final int subArraySize;
    final int size;

    private MaxSumSubArray(final int[] array, int subArraySize) {

        this.size = array.length;

        if (this.size < subArraySize) {
            throw new IllegalArgumentException("sub-array size is greater than size of array");
        }

        this.array = array;
        this.subArraySize = subArraySize;
    }

    private int get(int index) {
        return this.array[index];
    }

    @Override
    public Integer bruteForceSolution() {
        // time complexity: O(n^2)

        int maxSumSubArray = 0;

        for (int i = 0; i < (1 + this.size - this.subArraySize); ++i) {

            int subArraySum = 0;

            for (int j = 0; j < this.subArraySize; ++j) {
                tick();
                subArraySum += get(i + j);
            }

            maxSumSubArray = Math.max(maxSumSubArray, subArraySum);

        }

        return Integer.valueOf(maxSumSubArray);
    }

    @Override
    public Integer optimalSolution() {
        // time complexity: O(n)

        // since the sub-array of size k is a fixed size window the
        // solution can also be implemented using a sliding window

        // this solution doesn't need to build a prefix-sum array

        int windowSum = 0;

        // calculate the sum of first sub-array of size = window
        for (int index = 0; index < this.subArraySize; index++) {
            windowSum += get(index);
        }

        int maxSumSubArray = windowSum;

        // skip ahead to the next element and continue 'till the end of array
        for (int end = this.subArraySize; end < this.size; end++) {
            tick();
            // when the window slides towards the right a new element is
            // discovered and the window left-most element is left outside:
            // the next sub-array-sum is equal to the current sub-array-sum
            // minus the element left outside plus the new found element
            windowSum += get(end) - get(end - this.subArraySize);
            maxSumSubArray = Math.max(maxSumSubArray, windowSum);
        }

        return Integer.valueOf(maxSumSubArray);
    }

    public Integer anotherOptimalSolution() {
        // time complexity: O(n)

        // the goal is to find the sub-array on given size
        // which sum is the maximum among all combinations

        // a prefix-sum array is one such that for any array:
        // a = { 1, 2, 3, ... , n } , then its prefix-sum array (PSA) is equal to:
        // { a[1], a[1] + a[2], a[1]+a[2]+a[3], ... , sum(i from 1 to n) of a[i] }

        // using a new array to preserve the original one
        final int[] prefixSumArray = buildPrefixSumArray();

        // given a progression of numbers, a range-sum [x,y] is equal to:
        // difference of PS(x-1) and PS(y)
        // PS(y) - PS(x-1) for x > 0 OR PS(y) if x = 0
        // Example: RangeSum(2,4) = PS(4) - PS(2) = a[3] + a[4]
        // PS(4) - PS(2) = a[1] + a[2] + a[3] + a[4] - ( a[1] + a[2] )
        // PS(4) - PS(2) = a[1] - a[1] + a[2] - a[2] + a[3] + a[4]
        // PS(4) - PS(2) = 0 + 0 + a[3] + a[4] = a[3] + a[4]

        // max-sum-sub-array of k elements is an application of
        // RangeSum in which the range is of fixed size ( window )

        // max-sum-sub-array is the maximum possible RangeSum
        int maxSumSubArray = 0;

        for (int i = 0; i < (1 + this.size - this.subArraySize); ++i) {
            tick();
            maxSumSubArray = Math.max(maxSumSubArray, rangeSum(prefixSumArray, i, i + this.subArraySize - 1));
        }

        return Integer.valueOf(maxSumSubArray);
    }

    private int rangeSum(final int[] prefixSumArray, final int start, final int end) {
        if (start == 0) {
            return prefixSumArray[end];
        }
        return prefixSumArray[end] - prefixSumArray[start - 1];
    }

    private int[] buildPrefixSumArray() {

        final int[] prefixSumArray = new int[this.size];

        prefixSumArray[0] = get(0);

        for (int i = 1; i < this.size; ++i) {
            prefixSumArray[i] = prefixSumArray[i - 1] + get(i);
        }

        return prefixSumArray;
    }

}
