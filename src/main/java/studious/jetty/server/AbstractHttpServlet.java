package studious.jetty.server;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import studious.jetty.util.*;

public abstract class AbstractHttpServlet extends HttpServlet
{

    /**
     * @author ezliagu
     * @description
     */
    private static final long serialVersionUID = 564475071246190872L;

    static Logger logger = LoggerFactory.getLogger(AbstractHttpServlet.class);

    ServerConfig config;

    public AbstractHttpServlet()
    {
        this.config = getServerConfig();
    }

    protected abstract ServerConfig getServerConfig();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
    {
        String method = req.getMethod();
        logger.info(method);
        logger.info(req.getRequestURI());
        logger.info(req.getContextPath());
        String url = req.getRequestURI();
        String context = req.getContextPath();
        String filePath = FileUtils.getFileByUrl(
                config.getServerType().name().toLowerCase(),
                method.toLowerCase() + url.substring(context.length()));
        resp.setHeader("Content-Type", "application/json");
        
        
        try
        {
            resp.getWriter().println(IOUtils.toString(
                    new FileInputStream(filePath), Charset.forName("UTF-8")));
        }
        catch (IOException e)
        {
            logger.error("service error,", e);
        }
    }

    public static class OpsHttpServlet extends AbstractHttpServlet
    {

        /**
         * @author ezliagu
         * @description
         */
        private static final long serialVersionUID = -8289517780311366643L;

        @Override
        protected ServerConfig getServerConfig()
        {
            return ServerConfig.ofType(ServerType.OPS);
        }

    }

    public static class EcmHttpServlet extends AbstractHttpServlet
    {

        /**
         * @author ezliagu
         * @description
         */
        private static final long serialVersionUID = -5006948321767192372L;

        @Override
        protected ServerConfig getServerConfig()
        {
            return ServerConfig.ofType(ServerType.ECM);
        }

    }

    public static class VnfmHttpServlet extends AbstractHttpServlet
    {

        /**
         * @author ezliagu
         * @description
         */
        private static final long serialVersionUID = -3931216743258335078L;

        @Override
        protected ServerConfig getServerConfig()
        {
            return ServerConfig.ofType(ServerType.VNFM);
        }

    }

    public static class DiffHttpServlet extends AbstractHttpServlet
    {

        /**
         * @author ezliagu
         * @description
         */
        private static final long serialVersionUID = -2472309034665292713L;

        @Override
        protected ServerConfig getServerConfig()
        {
            return ServerConfig.ofType(ServerType.DIFF);
        }

    }

}