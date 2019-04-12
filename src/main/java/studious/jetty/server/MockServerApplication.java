package studious.jetty.server;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class MockServerApplication
{
    static Logger logger = LoggerFactory.getLogger(MockServerApplication.class);
    
    public static void main(String[] args)
    {
        MockServerApplication.start(ServerType.ECM);
        MockServerApplication.start(ServerType.OPS);
        MockServerApplication.start(ServerType.DIFF);
        MockServerApplication.start(ServerType.VNFM);
    }
    
    public static Server start(ServerType serverType)
    {
        Server server = new Server();
        ServerConfig config = ServerConfig.ofType(serverType);
        if(config.getPort() > 0) {
            server.addConnector(buildHttpServerConnector(server, config.getPort()));
        }
        if(config.getSecurePort() > 0) {
            server.addConnector(buildHttpsServerConnector(server, config.getSecurePort()));
        }

        HandlerCollection handlers = new HandlerCollection();
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath(config.getContextPath());
        context.addServlet(config.getServletClass(), "/");
        handlers.addHandler(context);
        if(!StringUtils.isEmpty(config.getOptionalContextPath())) {
            ServletContextHandler optionalContext = new ServletContextHandler();
            optionalContext.setContextPath(config.getOptionalContextPath());
            optionalContext.addServlet(config.getServletClass(), "/");
            handlers.addHandler(optionalContext);
        }
//        handlers.setHandlers(new Handler[] { context, new DefaultHandler() });
        server.setHandler(handlers);

        try
        {
            server.start();
            logger.info("Server [" + serverType +"] is started successfully.");
        }
        catch (Exception e)
        {
            logger.error("server start error,", e);
        }
        return server;
    }

    public static ServerConnector buildHttpServerConnector(Server server, int port) {
        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setOutputBufferSize(32768);

        // HTTP connector
        // The first server connector we create is the one for http, passing in
        // the http configuration we configured above so it can get things like
        // the output buffer size, etc. We also set the port (8080) and
        // configure an idle timeout.
        ServerConnector http = new ServerConnector(server,
                new HttpConnectionFactory(http_config));
        http.setPort(port);
        http.setIdleTimeout(30000);
        return http;
    }

    public static ServerConnector buildHttpsServerConnector(Server server, int port) {
        String jettyDistKeystore = "C:\\Users\\ezliagu\\a\\usr\\https\\san-domain\\san_domain_com.p12";
//        String keystorePath = System.getProperty(
//                "example.keystore", jettyDistKeystore);
        File keystoreFile = new File(jettyDistKeystore);
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(keystoreFile.getAbsolutePath());
        //changeit
        sslContextFactory.setKeyStorePassword("OBF:1vn21ugu1saj1v9i1v941sar1ugw1vo0");
        sslContextFactory.setKeyManagerPassword("OBF:1vn21ugu1saj1v9i1v941sar1ugw1vo0");

        // HTTPS Configuration
        // A new HttpConfiguration object is needed for the next connector and
        // you can pass the old one as an argument to effectively clone the
        // contents. On this HttpConfiguration object we add a
        // SecureRequestCustomizer which is how a new connector is able to
        // resolve the https connection before handing control over to the Jetty
        // Server.
        HttpConfiguration http_config = new HttpConfiguration();
        http_config.setSecureScheme("https");
        http_config.setSecurePort(port);
        http_config.setOutputBufferSize(32768);

        HttpConfiguration https_config = new HttpConfiguration(http_config);
        SecureRequestCustomizer src = new SecureRequestCustomizer();
        src.setStsMaxAge(2000);
        src.setStsIncludeSubDomains(true);
        https_config.addCustomizer(src);

        // HTTPS connector
        // We create a second ServerConnector, passing in the http configuration
        // we just made along with the previously created ssl context factory.
        // Next we set the port and a longer idle timeout.
        ServerConnector https = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                new HttpConnectionFactory(https_config));
        https.setPort(port);
        https.setIdleTimeout(500000);
        return https;
    }
}
