package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {

        try {
            Collections.sort(inputNumbers);
        } catch (NullPointerException e) {
            throw new CannotBuildPyramidException();
        }
        catch (OutOfMemoryError e){
            throw new CannotBuildPyramidException();
        }

        double rowDouble;
        int row;
        int column;
        int indexOfArrList;
        //calc of nums of required rows
        rowDouble = (-1 + Math.sqrt(1 + 8 * inputNumbers.size())) / 2;
        row = (int) rowDouble;
        //if rowDouble is  no integer - throw exception
        if (Math.abs(rowDouble - row) > 0.0000001) throw new CannotBuildPyramidException();

        //calc of nums of column
        column = 2 * row - 1;

        //get last index of List
        indexOfArrList = inputNumbers.size() - 1;

        int[][] pyramidArr = new int[row][column];

        for (int i = row - 1; i >= 0; i--) {
            for (int j = column - 1; j >= 0; j--) {
                //start from last row
                if (i == row - 1) {
                    if (j % 2 == 0) {
                        pyramidArr[i][j] = (int) inputNumbers.get(indexOfArrList);
                        indexOfArrList--;
                    } else {
                        pyramidArr[i][j] = 0;
                    }
                } else {
                    //left or right side of pyramid is 0
                    if (j == column - 1 || j == 0) {
                        pyramidArr[i][j] = 0;
                    } else {
                        //right side
                        if (j > column / 2) {
                            if (pyramidArr[i + 1][j + 1] != 0) {
                                pyramidArr[i][j] = (int) inputNumbers.get(indexOfArrList);
                                indexOfArrList--;
                            } else {
                                pyramidArr[i][j] = 0;
                            }
                        }
                        //left side and center
                        else {
                            if (pyramidArr[i + 1][j - 1] != 0) {
                                pyramidArr[i][j] = (int) inputNumbers.get(indexOfArrList);
                                indexOfArrList--;
                            } else {
                                pyramidArr[i][j] = 0;
                            }
                        }
                    }
                }
            }
        }
        return pyramidArr;


    }
}

