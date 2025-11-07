package Engine.Math;

//sometimes the Math library isn't enough
//This class has more complex operations
public class ComplexCalculator {

    //triangle[x,y,z][index]
    public static float[] calculateAveragePositionOfTriangle(float[][] triangle){
        float averageX = ComplexCalculator.calculateAverage(triangle[0]);
        float averageY = ComplexCalculator.calculateAverage(triangle[1]);
        float averageZ = ComplexCalculator.calculateAverage(triangle[2]);
        return new float[]{averageX, averageY, averageZ};
    }


    public static float calculateDistance3D(float[] pos1, float[] pos2){
        //a² + b² + c² = d²
        return (float) Math.sqrt((pos1[0] - pos2[0]) * (pos1[0] - pos2[0])   +    (pos1[1] - pos2[1]) * (pos1[1] - pos2[1])    +   (pos1[2] - pos2[2]) * (pos1[2] - pos2[2]));
    }

    public static float calculateAverage(float[] array){
        float sum = 0f;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum/array.length;
    }


    public static boolean contains(int[] array, int value){
        for (int element : array)
            if(element == value)
                return true;

        return false;
    }


    public static double roundDouble(double value, int places){
        value *= Math.pow(10, places);
        return Math.round(value) / Math.pow(10, places);
    }

    /**multiply each element of a vector with the other vector
     * v.x = v1.x * v2.x
     * v.y = v1.y * v2.y
     * v.x = v1.x * v2.x*/
    public static float[] hadamardProductVector(float[] v1, float[] v2){
        if(v2.length != v1.length)
            throw new RuntimeException("Vector has the wrong size");

        float[] newVector = new float[v1.length];
        for (int i = 0; i < newVector.length; i++) {
            newVector[i] = v1[i] * v2[i];
        }
        return newVector;
    }

    public static float[][] matrixMultiplikation(float[][] m1, float[][] m2){
        if(m1[0].length != m2.length)
            throw new RuntimeException("Matrix has the wrong size");

        float[][] newMatrix = new float[m1.length][m2[0].length];

        //loping through every value of matrix
        for (int i = 0; i < newMatrix.length; i++) {
            for (int j = 0; j < newMatrix[0].length; j++) {

                //calculating the value
                for (int k = 0; k < m1[0].length; k++) {
                    newMatrix[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return newMatrix;
    }

    public static float[] scalarMultiplication(float[] vector, float scalar){
        float[] newVector = new float[vector.length];
        for (int i = 0; i < vector.length; i++) {
            newVector[i] = vector[i] * scalar;
        }
        return newVector;
    }

    public static float clampToRange(float value, float min, float max){
        return Math.max(min, Math.min(value, max));
    }

    public static float calculateVectorNorm(float[] vector, int p){
        //p = infinity => p = max value
        if(p == Integer.MAX_VALUE)
            return getMax(vector);

        float sum = 0f;
        for (int i = 0; i < vector.length; i++) {
            sum += (float) Math.pow(vector[i], p);
        }
        return (float) Math.pow(sum, (double) 1 / p);
    }

    public static float[] crossProduct(float[] v1, float[] v2){
        float[] newVector = {v1[1]*v2[2] - v1[2]*v2[1], v1[2]*v2[0] - v1[0]*v2[2], v1[0]*v2[1] - v1[1]*v2[0]};
        return newVector;
    }

    public static float dotProduct(float[] v1, float[] v2){
        float sum = 0f;
        for (int i = 0; i < v1.length; i++) {
            sum += v1[i] * v2[i];
        }
        return sum;
    }


    public static float[] matrixVectorMultiplikation(float[][] matrix, float[] vektor){
        if(matrix[0].length != vektor.length)
            throw new RuntimeException("Matrix has the wrong size");

        float[] newVector = new float[matrix.length];
        for (int i = 0; i < newVector.length; i++) {
            for (int j = 0; j < vektor.length; j++) {
                newVector[i] += matrix[i][j] * vektor[j];
            }
        }
        return  newVector;
    }

    public static float[] vectorAddition(float[] v1, float[] v2){
        if(v2.length != v1.length)
            throw new RuntimeException("Vector has the wrong size");

        float[] newVector = new float[v1.length];
        for (int i = 0; i < v1.length; i++) {
            newVector[i] = v1[i] + v2[i];
        }
        return newVector;
    }

    public static int[] vectorAddition(int[] v1, int[] v2){
        if(v2.length != v1.length)
            throw new RuntimeException("Vector has the wrong size");

        int[] newVector = new int[v1.length];
        for (int i = 0; i < v1.length; i++) {
            newVector[i] = v1[i] + v2[i];
        }
        return newVector;
    }

    public static float[] vectorSubtraction(float[] v1, float[] v2){
        if(v2.length != v1.length)
            throw new RuntimeException("Vector has the wrong size");

        float[] newVector = new float[v1.length];
        for (int i = 0; i < v1.length; i++) {
            newVector[i] = v1[i] - v2[i];
        }
        return newVector;
    }

    public static void printMatrix(float[][] m){
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printMatrix(int[][] m){
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printVector(float[] v){
        System.out.print("Vektor: ");
        for (int i = 0; i < v.length; i++) {
            System.out.print(v[i] + " ");
        }
        System.out.println();
    }

    public static void printVector(int[] v){
        System.out.print("Vektor: ");
        for (int i = 0; i < v.length; i++) {
            System.out.print(v[i] + " ");
        }
        System.out.println();
    }

    public static float getMax(float[] v){
        float max = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < v.length; i++) {
            if(v[i] > max)
                max = v[i];
        }
        return max;
    }

    public static float getMin(float[] v){
        float max = Float.POSITIVE_INFINITY;
        for (int i = 0; i < v.length; i++) {
            if(v[i] < max)
                max = v[i];
        }
        return max;
    }

    public static int getMaxIndex(float[] v){
        int max = 0;
        for (int i = 1; i < v.length; i++) {
            if(v[i] > v[max])
                max = i;
        }
        return max;
    }

    public static int getMinIndex(float[] v){
        int max = 0;
        for (int i = 1; i < v.length; i++) {
            if(v[i] < v[max])
                max = i;
        }
        return max;
    }

    public static float[] rotatePointX(float[] pos, float angle){
        float[][] matrix = getXRotationMatrix(angle);
        return ComplexCalculator.matrixVectorMultiplikation(matrix, pos);
    }

    public static float[] rotatePointY(float[] pos, float angle){
        float[][] matrix = getYRotationMatrix(angle);
        return ComplexCalculator.matrixVectorMultiplikation(matrix, pos);
    }

    public static float[] rotatePointZ(float[] pos, float angle){
        float[][] matrix = getZRotationMatrix(angle);
        return ComplexCalculator.matrixVectorMultiplikation(matrix, pos);
    }

    public static float[][] getXRotationMatrix(float angle){
        angle = (float) Math.toRadians(angle);
        float[][] matrix = {
                {1f, 0f, 0f},
                {0f, (float) Math.cos(angle), (float) -Math.sin(angle)},
                {0f, (float) Math.sin(angle), (float) Math.cos(angle)}
        };
        return matrix;
    }

    public static float[][] getYRotationMatrix(float angle){
        angle = (float) Math.toRadians(angle);
        float[][] matrix = {
                {(float) Math.cos(angle), 0f, (float) Math.sin(angle)},
                {0f, 1f, 0f},
                {-(float) Math.sin(angle), 0f, (float) Math.cos(angle)}
        };
        return matrix;
    }

    public static float[][] getZRotationMatrix(float angle){
        angle = (float) Math.toRadians(angle);
        float[][] matrix = {
                {(float) Math.cos(angle), (float) -Math.sin(angle), 0f},
                {(float) Math.sin(angle), (float) Math.cos(angle), 0f},
                {0f, 0f, 1f}
        };
        return matrix;
    }

    public static int[] appendArray(int[] a, int[] b){
        int[] newArray = new int[a.length + b.length];
        for (int i = 0; i < a.length; i++) {
            newArray[i] = a[i];
        }
        for (int i = a.length; i < newArray.length; i++) {
            newArray[i] = b[i-a.length];
        }
        return newArray;
    }

    public static float[] appendArray(float[] a, float[] b){
        float[] newArray = new float[a.length + b.length];
        for (int i = 0; i < a.length; i++) {
            newArray[i] = a[i];
        }
        for (int i = a.length; i < newArray.length; i++) {
            newArray[i] = b[i-a.length];
        }
        return newArray;
    }
}
