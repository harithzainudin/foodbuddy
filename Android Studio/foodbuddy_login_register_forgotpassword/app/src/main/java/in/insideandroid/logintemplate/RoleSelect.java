package in.insideandroid.logintemplate;

public class RoleSelect {

    public String username;
    public String password;
    public int role;

    /* role numbering
    0 = admin
    1 = customer
    2 = seller
     */

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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
