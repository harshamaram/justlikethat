package com.justlikethat.hackerrank;

/*
https://www.hackerrank.com/challenges/minimum-swaps-2?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays
 */

/*
Idea:
find both min and max starting from a specific index
start from left, skip till its already sorted.
find min and max indices,
- if they are sorted, find next max index
- if they are not, swap min and max index locations, and keep a count

 */

public class MinimumSwaps {

    public static void main(String s[]) {
        int[] arr = {7,1,3,2,4,5,6};
        minSwaps(arr);
    }

    private static int minSwaps(int[] arr) {

        return 0;
    }

    private static int[] findMinMaxIndices(int[] arr, int startIndex) {
        int[] result = new int[2];
        return result;
    }

}
