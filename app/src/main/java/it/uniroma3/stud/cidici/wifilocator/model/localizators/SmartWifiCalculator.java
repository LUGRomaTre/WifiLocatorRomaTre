package it.uniroma3.stud.cidici.wifilocator.model.localizators;

import it.uniroma3.stud.cidici.wifilocator.model.Ap;
import it.uniroma3.stud.cidici.wifilocator.model.Position;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Tutti i diritti riservati a Christian Micocci
 * Created by christian on 02/08/16.
 */
public class SmartWifiCalculator {

    public static final double AP_GAIN = 14;
    public static final double A = 1;
    public static final double B = 0;
    public static final double C = 128;
    private static int iterations = 0;
    public int WIDTH = 26;
    public int HEIGHT = 40;

    public static void main(String[] args) {
        SmartWifiCalculator a = new SmartWifiCalculator();
        Ap ap1 = new Ap("", new Position(1, 1));
        Ap ap2 = new Ap("", new Position(200, 150));
        Ap ap3 = new Ap("", new Position(300, 300));
        ap1.setLevel(-71);
        ap2.setLevel(-70);
        ap3.setLevel(-75);

        HashSet<Ap> aps = new HashSet<>(3);
        aps.add(ap1);
        aps.add(ap2);
        aps.add(ap3);

        a.findPosition(128, aps);
        System.out.println(iterations);
    }

    public Position findPosition(int blockSize, Set<Ap> aps) {
        return findPosition(blockSize, aps, 0, WIDTH, 0, HEIGHT);
    }

    public Position findPosition(int blockSize, Set<Ap> aps, int startX, int endX, int startY, int endY) {
        System.out.println("blockSize = " + blockSize);
        if (blockSize == 0) blockSize = 1;
        int w = (endX - startX) / blockSize + 1;
        int h = (endY - startY) / blockSize;

        double[][] mappa = new double[w][h];

        for (double[] colonne : mappa) {
            Arrays.fill(colonne, 0);
        }

        for (Ap ap : aps) {
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    double distance = calculateDistance(ap.getLevel() + 38, 2450);
                    Position apPos = ap.getPosition();
                    mappa[x][y] += calculateProbability(apPos.getX(), apPos.getY(), (x + 0.5) * blockSize + startX, (y + 0.5) * blockSize + startY, distance);
                    printMatrix(mappa);
                    iterations++;
                }
            }
            break;
        }

        int xMax = 0;
        int yMax = 0;
        double valMax = 0;

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (mappa[x][y] > valMax) {
                    valMax = mappa[x][y];
                    xMax = x;
                    yMax = y;
                    iterations++;
                }
            }
        }

        int xfound = xMax * blockSize + startX;
        int yfound = yMax * blockSize + startY;

        System.out.println("xfound = " + xfound);
        System.out.println("yfound = " + yfound);
        System.out.println("valMax = " + valMax);

        if (blockSize == 1) return new Position(xfound, yfound);
        return findPosition(blockSize / 2, aps, xfound, (xMax + 1) * blockSize + startX, yfound, (yMax + 1) * blockSize + startY);
    }

    private double calculateProbability(double x1, double y1, double x2, double y2, double distanceEstimated) {
        double dd = Math.abs(distanceEstimated - Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)));
        return A * Math.exp(-Math.pow((dd - B), 2) / Math.pow(C, 2));
    }

    public double calculateDistance(int level, int frequency) {
//        d=10^((27.55-(20*log10(2447))+58+14-19)/20)
//        distanza = 10^((27.55-(20*log10(freqMhz))-powerDbm+AP_GAIN-WallFactor*wall)/20)
        return Math.pow(10, ((27.55 - (20 * Math.log10(frequency)) - level + AP_GAIN) / 20));
    }

    private double convertXYToDistance(int x, int y, int blockSize) {
        return Math.sqrt(Math.pow(x + 0.5, 2) + Math.pow(y + 0.5, 2)) * blockSize;
    }

    private void printMatrix(double[][] mat) {
        System.out.println("___________________");
        int length1 = mat.length;
        for (int x = 0; x < length1; x++) {
            int length = mat[x].length;
            for (int y = 0; y < length; y++) {
                System.out.print(((int) mat[x][y] * 100) + " ");
            }
            System.out.println();
        }
        System.out.println("--------------------");
        System.out.println();
    }

}
