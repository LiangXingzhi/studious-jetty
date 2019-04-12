package studious.jetty.server;

public enum ServerType
{
    ECM, OPS, DIFF, VNFM;
    
    public static void main(String[] args) {
        System.out.println(ECM.name());
    }
}
