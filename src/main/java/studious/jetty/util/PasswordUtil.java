package studious.jetty.util;

import org.eclipse.jetty.util.security.Credential;
import org.eclipse.jetty.util.security.Password;

public class PasswordUtil {

    public static String obfuscate(String plainPassword) {
        return Password.obfuscate(plainPassword);
    }

    public static String deobfuscate(String obfPassword) {
        return Password.deobfuscate(obfPassword);
    }

    public static void main(String[] args) {
        System.out.println(obfuscate("changeit"));
        System.out.println(deobfuscate("OBF:1v2j1vu11ym71ym71vv91v1v"));
    }
}
