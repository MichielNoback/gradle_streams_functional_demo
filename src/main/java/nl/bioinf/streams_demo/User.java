package nl.bioinf.streams_demo;

import java.util.Objects;

public class User {
    private String first;
    private String last;
    private String email;
    private Role role;

    public User(String first, String last, String email) {
        this(first, last, email, Role.GUEST);
    }

    public User(String first, String last, String email, Role role) {
        this.first = first;
        this.last = last;
        this.email = email;
        this.role = role;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "first='" + first + '\'' +
                ", last='" + last + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(first, user.first)) return false;
        if (!Objects.equals(last, user.last)) return false;
        if (!Objects.equals(email, user.email)) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (last != null ? last.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
