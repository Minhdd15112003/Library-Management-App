package ph25260.fpoly.asm.model;

public class User {
    private int id;
    private String email;
    private String username;
    private String password;
    private String numberphone;



    public User(int id, String email, String username, String password, String numberphone) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.numberphone = numberphone;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", numberphone='" + numberphone + '\'' +
                '}';
    }
}