package demo;

import org.eclipse.jetty.util.security.Credential;
import org.eclipse.jetty.util.security.Password;

/**
 * java -cp ./system/org/eclipse/jetty/jetty-util/9.3.7.v20160115/jetty-util-9.3.7.v20160115.jar org.eclipse.jetty.util.security.Password "password"
 *
 */
public class Test {
    public static void main(String[] args) {
        String p = "changeit";
        Password pw = new Password(p);
        System.err.println(pw.toString());
        System.err.println(Password.obfuscate(pw.toString()));
        System.err.println(Credential.MD5.digest(p));
        System.err.println(Password.deobfuscate("OBF:1v2j1vu11ym71ym71vv91v1v"));
    }
}

//passwd
//        OBF:1v2j1vu11ym71ym71vv91v1v
//        MD5:76a2173be6393254e72ffa4d6df1030a