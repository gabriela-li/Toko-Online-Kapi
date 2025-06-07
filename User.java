import java.util.ArrayList;
import java.util.List;

public class User {
    List<List<String>> user = new ArrayList<>();
    private String username;
    private String password;
    private String fullName;
    private String userType;

    /*
     * username = andi23
     * passw = passw123
     * fullName = Andi Pratama
     * type = customer
     */

    public User(String username, String password, String fullName, String userType) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.userType = userType;
        user.add(List.of("Titi", "20242025", "Titi Sendi", "Customer"));
        user.add(List.of("Didi", "123456", "Didi Dodo", "Admin"));
        user.add(List.of(username, password, fullName, userType));
    }

    public String getUsername() {
        return username;
    }

    public String getUserType (){
        return this.userType;
    }

    public String getFullName(){
        return this.fullName;
    }
}
