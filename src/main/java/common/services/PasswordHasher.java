package common.services;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 */
public class PasswordHasher {

    /**
     * Hash the user's password
     *
     * @param password Password
     * @return String
     */
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
