package common.services;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Hashes a user's plaintext password
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
