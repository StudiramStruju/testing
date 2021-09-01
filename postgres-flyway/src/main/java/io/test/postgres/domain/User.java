package io.test.postgres.domain;

import org.springframework.lang.Nullable;

import java.util.Objects;

public class User {

    @Nullable
    private Integer id;
    private String fullName;
    private String email;
    private String password;

    public User(final String fullName, final String email, final String password) {
        this.id = null;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public User(@Nullable final Integer id, final String fullName, final String email, final String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    @Nullable
    public Integer getId() {
        return this.id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public User setId(@Nullable final Integer id) {
        this.id = id;
        return this;
    }

    public User setFullName(final String fullName) {
        this.fullName = fullName;
        return this;
    }

    public User setEmail(final String email) {
        this.email = email;
        return this;
    }

    public User setPassword(final String password) {
        this.password = password;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        final User user = (User) o;
        return Objects.equals(this.getId(), user.getId()) &&
                this.getFullName().equals(user.getFullName()) &&
                this.getEmail().equals(user.getEmail()) &&
                this.getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getFullName(), this.getEmail(), this.getPassword());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(this.id);
        sb.append(", fullName='").append(this.fullName).append('\'');
        sb.append(", email='").append(this.email).append('\'');
        sb.append(", password='").append(this.password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
