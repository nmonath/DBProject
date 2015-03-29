package edu.umass.cs.data_fusion.util;


public class Functions {

    public static double dot(double[] x, double[] y) {
        assert x.length == y.length;
        double res = 0.0;
        for (int i = 0; i < x.length; i++)
            res += x[i] * y[i];
        return res;
    }

    public static double L1Norm(double[] x) {
        double res = 0.0;
        for (int i = 0; i < x.length; i++)
            res += Math.abs(x[i]);
        return res;
    }

    public static double L2Norm(double[] x) {
        double res = 0.0;
        for (int i = 0; i < x.length; i++)
            res += Math.pow(x[i], 2);
        return res;
    }

    public static double cosine(double[] x, double[] y) {
        assert x.length == y.length;
        return dot(x, y) / (L2Norm(x) * L2Norm(y));
    }

    /**
     * Based on Algorithm presented in: http://people.cs.clemson.edu/~bcdean/dp_practice/dp_8.swf
     * Computes the editDistance between the two strings. The minimum number of characters to delete, insert or swap
     * to convert one string into the other.
     *
     * @param one
     * @param two
     * @return
     */
    public static int editDistance(String one, String two) {

        int[][] dist = new int[2][two.length() + 1];
        for (int j = 0; j <= two.length(); j++) {
            dist[0][j] = j;
        }

        int replacementCost = 0;
        for (int i = 1; i <= one.length(); i++) {
            dist[1][0] = i;
            for (int j = 1; j <= two.length(); j++) {
                replacementCost = 0;
                if (one.charAt(i - 1) != two.charAt(j - 1)) {
                    replacementCost = 1;
                }
                //                             Deletion Cost    Insertion Cost      Replacement/Use Symbol Cost
                dist[1][j] = Math.min(Math.min(dist[0][j] + 1, dist[1][j - 1] + 1), dist[0][j - 1] + replacementCost);
            }
            System.arraycopy(dist[1], 0, dist[0], 0, dist[0].length);
        }
        return dist[1][two.length()];
    }

}
