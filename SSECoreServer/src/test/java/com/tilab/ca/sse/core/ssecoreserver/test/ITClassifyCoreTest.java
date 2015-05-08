package com.tilab.ca.sse.core.ssecoreserver.test;

import com.tilab.ca.sse.core.lucene.IndexesUtil;
import com.tilab.ca.sse.core.lucene.LuceneManager;
import com.tilab.ca.sse.core.lucene.SimpleSearcher;
import com.tilab.ca.sse.core.rest.ClassifyOutput;
import com.tilab.ca.sse.core.ssecoreserver.App;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.util.Version;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.index.IndexWriterConfig;

public class ITClassifyCoreTest {

    private HttpServer server;
    private WebTarget target;
    RAMDirectory idx = null;

    @Before
    public void setUp() throws Exception {

        // Construct a RAMDirectory to hold the in-memory representation
        // of the index.
        idx = new RAMDirectory();

        // Make an writer to create the index
        ItalianAnalyzer italianAnalyzer = new ItalianAnalyzer(Version.LUCENE_36,
                getStopWordsMock(ClassLoader.class.getResourceAsStream("/com/tilab/ca/sse/core/config/test/stopwords.it.list")));
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, italianAnalyzer);
        IndexWriter writer = new IndexWriter(idx, config);

        // Add some Document 
        writer.addDocument(createDocument("HD 88693", "HD_88693", " HD 88693 è una stella gigante arancione di magnitudine 6,16 "
                + "situata nella costellazione delle Vele . Dista 335 anni luce dal sistema solare . Osservazione Si tratta di una "
                + "stella situata nell' emisfero celeste australe. La sua posizione è fortemente australe e ciò comporta che la stella "
                + "sia osservabile prevalentemente dall'emisfero sud, dove si presenta circumpolare anche da gran parte delle regioni "
                + "temperate; dall'emisfero nord la sua visibilità è invece limitata alle regioni temperate inferiori e alla fascia "
                + "tropicale. Essendo di magnitudine pari a 6,2, non è osservabile ad occhio nudo ; per poterla scorgere è sufficiente "
                + "comunque anche un binocolo di piccole dimensioni, a patto di avere a disposizione un cielo buio. Il periodo migliore "
                + "per la sua osservazione nel cielo serale ricade nei mesi compresi fra febbraio e giugno; nell'emisfero sud è visibile"
                + " anche per buona parte dell'inverno, grazie alla declinazione australe della stella, mentre nell'emisfero nord può essere"
                + " osservata limitatamente durante i mesi primaverili boreali. Voci correlate Stelle principali della costellazione delle "
                + "Vele Collegamenti esterni Categoria:Stelle di classe spettrale K Categoria:"
                + "Giganti rosse", "http://commons.wikimedia.org/wiki/Special:FilePath/Vela_constellation_map.svg", "DBpedia:CelestialBody", "1"));
        // Add some Document 
        writer.addDocument(createDocument("HD 98025", "HD_98025", "  HD 98025 è una stella bianca nella sequenza principale di magnitudine "
                + "6,43 situata nella costellazione della Carena . Dista 583 anni luce dal sistema solare . Osservazione Si tratta di una "
                + "stella situata nell' emisfero celeste australe. La sua posizione è fortemente australe e ciò comporta che la stella "
                + "sia osservabile prevalentemente dall'emisfero sud, dove si presenta circumpolare anche da gran parte delle regioni "
                + "temperate; dall'emisfero nord la sua visibilità è invece limitata alle regioni temperate inferiori e alla fascia tropicale. "
                + "Essendo di magnitudine pari a 6,4, non è osservabile ad occhio nudo ; per poterla scorgere è sufficiente comunque anche "
                + "un binocolo di piccole dimensioni, a patto di avere a disposizione un cielo buio. Il periodo migliore per la sua osservazione"
                + " nel cielo serale ricade nei mesi compresi fra febbraio e giugno; nell'emisfero sud è visibile anche per buona parte "
                + "dell'inverno, grazie alla declinazione australe della stella, mentre nell'emisfero nord può essere osservata limitatamente "
                + "durante i mesi primaverili boreali. Voci correlate Stelle principali della costellazione della Carena Collegamenti "
                + "esterni Categoria:Stelle di classe spettrale A Categoria:Stelle bianche di sequenza principale",
                "http://commons.wikimedia.org/wiki/Special:FilePath/Carina_constellation_map.svg", "DBpedia:CelestialBody", "1"));
        // Add some Document 
        writer.addDocument(createDocument("HD 62082", "HD_62082", "  HD 62082 è una stella gigante rossa di magnitudine 6,2 situata nella "
                + "costellazione della Poppa . Dista 861 anni luce dal sistema solare . Osservazione Si tratta di una stella situata nell' "
                + "emisfero celeste australe; grazie alla sua posizione non fortemente australe, può essere osservata dalla gran parte "
                + "delle regioni della Terra , sebbene gli osservatori dell'emisfero sud siano più avvantaggiati. Nei pressi dell' "
                + "Antartide appare circumpolare , mentre resta sempre invisibile solo in prossimità del circolo polare artico . "
                + "Essendo di magnitudine pari a 6,2, non è osservabile ad occhio nudo ; per poterla scorgere è sufficiente comunque "
                + "anche un binocolo di piccole dimensioni, a patto di avere a disposizione un cielo buio. Il periodo migliore per la "
                + "sua osservazione nel cielo serale ricade nei mesi compresi fra dicembre e maggio; da entrambi gli emisferi il periodo"
                + " di visibilità rimane indicativamente lo stesso, grazie alla posizione della stella non lontana dall'equatore celeste."
                + " Voci correlate Stelle principali della costellazione della Poppa Collegamenti esterni Categoria:Stelle di classe "
                + "spettrale M Categoria:Giganti rosse",
                "http://commons.wikimedia.org/wiki/Special:FilePath/Puppis_constellation_map.svg", "DBpedia:CelestialBody", "1"));

        // Optimize and close the writer to finish building the index
        writer.optimize();
        writer.close();

        // build italian searcher
        LuceneManager contextLuceneManagerIT = new LuceneManager(idx);
        contextLuceneManagerIT.setLuceneDefaultAnalyzer(new ItalianAnalyzer(Version.LUCENE_36,
                getStopWordsMock(ClassLoader.class.getResourceAsStream("/com/tilab/ca/sse/core/config/test/stopwords.it.list"))));
        IndexesUtil.ITALIAN_CORPUS_INDEX_SEARCHER = new SimpleSearcher(contextLuceneManagerIT);

        // start the server
        server = App.startServerMock();
        // create the client
        Client c = ClientBuilder.newClient();
        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to add
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
//        c.configuration().enable(
//				new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target("http://127.0.0.1:8855/ssecore/classify");
    }

    @After
    public void tearDown() throws Exception {
        //server.stop();
    }

    /**
     * Test to see the text extracted from the URL
     */
    @Test
    public void testClassify() throws UnsupportedEncodingException, IOException {

        String payload = "{\"lang\":\"it\",\"numTopics\":3,\"text\":\"Si tratta di una stella situata nel "
                + "emisfero celeste australe \"}";

        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(payload));

        System.out.println("status = " + response.getStatus());
        System.out.println("status info = " + response.getStatusInfo());
        //System.out.println("raw response = "+response.readEntity(String.class));

        assertEquals(response.getStatus(), 200);
        
        List<ClassifyOutput> resources
                = response.readEntity(new GenericType<List<ClassifyOutput>>() {
                });
        
        assertFalse(resources.isEmpty());
        assertTrue(resources.size() == 3);
        
        
        
        StringBuilder builder = new StringBuilder("=== Classify Output ===\n");
        resources.forEach(resource
                -> builder.append("\n\nURI: ").append(resource.getUri()).append("\n")
                .append("LABEL: ").append(resource.getLabel()).append("\n")
                .append("TITLE: ").append(resource.getTitle()).append("\n")
                .append("SCORE: ").append(resource.getScore()).append("\n")            
                .append("TYPES: ").append(resource.getMergedTypes()).append("\n")
                .append("IMAGE: ").append(resource.getImage()).append("\n")
                .append("WIKILINK: ").append(resource.getWikilink()).append("\n")
        );

        builder.append("==================");
        System.out.println(builder.toString());

    }

    /**
     * Make a Document object with an un-indexed title field and an indexed
     * content field.
     */
    private Document createDocument(String title, String uri,
            String context, String image, String type, String uri_count) {

        Document doc = new Document();

        // ...and the content as an indexed field. Note that indexed
        // Text fields are constructed using a Reader. Lucene can read
        // and index very large chunks of text, without storing the
        // entire content verbatim in the index. In this example we
        // can just wrap the content string in a StringReader.
        doc.add(new Field("CONTEXT", context, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("IMAGE", image, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("TITLE", title, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("TYPE", type, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("URI", uri, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("URI", uri_count, Field.Store.YES, Field.Index.ANALYZED));

        return doc;
    }

    private static Set<String> getStopWordsMock(InputStream i) {
        ArrayList<String> stopWordsList = new ArrayList<String>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(i));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stopWordsList.add(line.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Could not read stopwords file in classpath");
        }
        Set<String> stopwordsSet = new HashSet<String>(stopWordsList);
        return stopwordsSet;
    }

}
