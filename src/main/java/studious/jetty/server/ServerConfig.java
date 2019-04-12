package studious.jetty.server;

import javax.servlet.Servlet;

import studious.jetty.server.AbstractHttpServlet.DiffHttpServlet;
import studious.jetty.server.AbstractHttpServlet.EcmHttpServlet;
import studious.jetty.server.AbstractHttpServlet.OpsHttpServlet;
import studious.jetty.server.AbstractHttpServlet.VnfmHttpServlet;

public class ServerConfig
{

    public static ServerConfig ofType(ServerType type)
    {
        switch (type)
        {
            case ECM:
                return new ServerConfig(ServerType.ECM, EcmHttpServlet.class,
                        "/ecm_service", 8083, -1);
            case OPS:
                return new ServerConfig(ServerType.OPS, OpsHttpServlet.class,
                        "/v1", 8981, 8900);
            case VNFM:
                return new ServerConfig(ServerType.VNFM, VnfmHttpServlet.class,
                        "/v1", "/", 8099, 8095);
            case DIFF:
                return new ServerConfig(ServerType.DIFF, DiffHttpServlet.class,
                        "/diff", 8188, -1);
        }
        return null;
    }

    ServerType serverType;

    public String getOptionalContextPath() {
        return optionalContextPath;
    }

    public void setOptionalContextPath(String optionalContextPath) {
        this.optionalContextPath = optionalContextPath;
    }

    String contextPath;

    String optionalContextPath;

    int port;

    public int getSecurePort() {
        return securePort;
    }

    public void setSecurePort(int securePort) {
        this.securePort = securePort;
    }

    int securePort;

    Class<? extends Servlet> servletClass;

    public ServerType getServerType()
    {
        return serverType;
    }

    public void setServerType(ServerType serverType)
    {
        this.serverType = serverType;
    }

    public String getContextPath()
    {
        return contextPath;
    }

    public void setContextPath(String contextPath)
    {
        this.contextPath = contextPath;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    private ServerConfig(ServerType serverType,
            Class<? extends Servlet> servletClass, String contextPath, int port, int securePort)
    {
        this(serverType, servletClass, contextPath, null, port, securePort);
    }

    private ServerConfig(ServerType serverType,
                         Class<? extends Servlet> servletClass, String contextPath, String optionalContextPath, int port, int securePort)
    {
        this.serverType = serverType;
        this.servletClass = servletClass;
        this.contextPath = contextPath;
        this.port = port;
        this.securePort = securePort;
        this.optionalContextPath = optionalContextPath;
    }

    public Class<? extends Servlet> getServletClass()
    {
        return servletClass;
    }

    public void setServletClass(Class<? extends Servlet> servletClass)
    {
        this.servletClass = servletClass;
    }

}