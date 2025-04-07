public class GeometricMedian {

    // Simple class to represent a 2D point.
    static class Point {
        double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        // Compute the Euclidean distance between this point and another.
        double distance(Point other) {
            return Math.hypot(this.x - other.x, this.y - other.y);
        }
        
        @Override
        public String toString() {
            return String.format("(%.6f, %.6f)", x, y);
        }
    }

    /**
     * Computes the geometric median of an array of points using the Weiszfeld algorithm.
     * @param points an array of Point objects.
     * @param tolerance the convergence tolerance.
     * @param maxIterations the maximum number of iterations.
     * @return the geometric median as a Point.
     */
    public static Point computeGeometricMedian(Point[] points, double tolerance, int maxIterations) {
        // Start with the centroid as the initial guess.
        double x = 0, y = 0;
        for (Point p : points) {
            x += p.x;
            y += p.y;
        }
        x /= points.length;
        y /= points.length;
        Point current = new Point(x, y);

        for (int iter = 0; iter < maxIterations; iter++) {
            double numeratorX = 0, numeratorY = 0;
            double denominator = 0;
            boolean reachedExactPoint = false;

            for (Point p : points) {
                double dist = current.distance(p);
                // If current equals one of the points, that point is the median.
                if (dist < 1e-10) {
                    current = p;
                    reachedExactPoint = true;
                    break;
                }
                double weight = 1.0 / dist;
                numeratorX += p.x * weight;
                numeratorY += p.y * weight;
                denominator += weight;
            }
            if (reachedExactPoint) {
                break;
            }
            Point next = new Point(numeratorX / denominator, numeratorY / denominator);
            // Check for convergence.
            if (current.distance(next) < tolerance) {
                current = next;
                break;
            }
            current = next;
        }
        return current;
    }

    public static void main(String[] args) {
        // Example usage: define a set of points.
        Point[] points = {
            new Point(0, 0),
            new Point(1, 0),
            new Point(0, 1),
            new Point(1, 1),
            new Point(0.5, 2)
        };

        // Set convergence tolerance and maximum iterations.
        double tolerance = 1e-6;
        int maxIterations = 1000;

        Point median = computeGeometricMedian(points, tolerance, maxIterations);
        System.out.println("The geometric median is: " + median);
    }
}
