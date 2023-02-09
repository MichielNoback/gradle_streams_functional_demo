package nl.bioinf.streams_demo;

public record Coordinate(int x, int y) {
    double euclideanDistance(Coordinate other) {
        return Math.sqrt(Math.pow((x - other.x()), 2) + Math.pow((y - other.y()), 2));
    }
}