package com.tilab.ca.sse.core.ssecoreserver;

import com.tilab.ca.sse.core.lucene.IndexesUtil;
import com.tilab.ca.sse.core.util.SSEVariables;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Hello world!
 *
 */
public class App {
    // Base URI the Grizzly HTTP server will listen on

    public static final String BASE_URI = "http://0.0.0.0:8079/sse/"; //XXX Hardcoded?

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this
     * application.
     *
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example package
        final ResourceConfig rc = new ResourceConfig().packages("com.tilab.ca.sse.core.rest");

        // to enable JSON support
        rc.register(JacksonFeature.class);

        // to enable Multipart support
        rc.register(MultiPartFeature.class);
		
		//XXX FIXME Codice vergognoso che serve al solo scopo di inizializzare le config globali del core.
		//XXX FIXME da sostituire con Owner.
		SSEVariables variables = new SSEVariables("/var/local/ssecore/conf/server.properties");
		IndexesUtil.init();
        
//        sseConfigFromCache = ConfigCache.getOrCreate(SSEConfig.class);
//        sseConfigFromCache.addReloadListener((ReloadEvent event) -> {
//            LOG.log(Level.INFO, "Reload intercepted at {0}", new Date());
//        });
        
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at configured serviceUrl 
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\n", BASE_URI));
        Thread.currentThread().suspend(); //XXX verify the best option to suspend the current thread

        server.stop();
    }
}
