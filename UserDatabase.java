import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private static final List<User> users = new ArrayList<>();

    static {
        users.add(new User("andi23", "passw123", "Andi Pratama", "Customer"));
        users.add(new User("dodo", "dodo123", "Dodo Ando", "Admin"));
    }

    public static boolean registerUser(String username, String password, String fullName, String type) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        users.add(new User(username, password, fullName, type));
        return true;
    }

    public static User loginUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.checkPassword(password)) {
                return u;
            }
        }
        return null;
    }
}