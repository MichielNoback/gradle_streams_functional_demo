package nl.bioinf.streams_demo;

public record Person2(String first, String second, String email, Role role) {
    String getFullName() {
        return first + " " + second;
    }
}
