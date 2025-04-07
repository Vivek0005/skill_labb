package see;
import java.util.*;

public class OptimalHubLocation {

    // Point class
    static class Point {
        double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // Method to calculate Manhattan-optimal point (median)
    static Point manhattanOptimal(Point[] points) {
        int n = points.length;
        double[] xs = new double[n];
        double[] ys = new double[n];

        for (int i = 0; i < n; i++) {
            xs[i] = points[i].x;
            ys[i] = points[i].y;
        }

        Arrays.sort(xs);
        Arrays.sort(ys);

        double medianX = xs[n / 2];
        double medianY = ys[n / 2];

        return new Point(medianX, medianY);
    }

    // Method to calculate Euclidean-optimal point (Weiszfeld's algorithm)
    static Point euclideanOptimal(Point[] points, int maxIterations, double epsilon) {
        double x = 0, y = 0;
        for (Point p : points) {
            x += p.x;
            y += p.y;
        }
        x /= points.length;
        y /= points.length;

        for (int iter = 0; iter < maxIterations; iter++) {
            double numX = 0, numY = 0, denom = 0;
            for (Point p : points) {
                double dist = Math.hypot(x - p.x, y - p.y);
                if (dist < epsilon) continue; // Avoid division by zero
                double weight = 1.0 / dist;
                numX += weight * p.x;
                numY += weight * p.y;
                denom += weight;
            }
            double newX = numX / denom;
            double newY = numY / denom;

            if (Math.abs(newX - x) < epsilon && Math.abs(newY - y) < epsilon) break;

            x = newX;
            y = newY;
        }

        return new Point(x, y);
    }

    public static void main(String[] args) {
        // Sample input
        Point[] customers = {
            new Point(2, 3),
            new Point(6, 7),
            new Point(3, 4),
            new Point(5, 1),
            new Point(4, 2)
        };

        Point manhattan = manhattanOptimal(customers);
        Point euclidean = euclideanOptimal(customers, 1000, 1e-6);

        System.out.printf("📍 Manhattan Optimal Location: (%.2f, %.2f)%n", manhattan.x, manhattan.y);
        System.out.printf("📍 Euclidean Optimal Location: (%.2f, %.2f)%n", euclidean.x, euclidean.y);
    }
}
