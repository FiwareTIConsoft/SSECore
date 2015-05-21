/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tila.ca.sse.core.ssecoreserver.test;

import com.tilab.ca.sse.core.classify.properties.SSEConfigMock;
import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import static java.util.Optional.ofNullable;
import static junit.framework.Assert.assertTrue;
import junit.framework.TestCase;
import org.aeonbits.owner.ConfigCache;
import org.aeonbits.owner.event.ReloadEvent;
import org.aeonbits.owner.event.ReloadListener;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author aantonazzo
 */
public class ConfigTest extends TestCase {

    private SSEConfigMock sseConfigFromCache = null;
    String corpusIndexIt = "/TMF/data/it/indexWithTitles-Images-SameAs";
    String kbIt = "/TMF/data/it/KB";
    String residualkbIt = "/TMF/data/it/ResidualKB";
    String stopWordsIt = "/TMF/data/it/italian-stopwords.list";
    String corpusIndexEn = "/TMF/data/en/indexWithTitles-Images";
    String kbEn = "/TMF/data/en/KB";
    String residualkbEn = "/TMF/data/en/ResidualKB";
    String stopWordsEn = "/TMF/data/en/stopwords.en.list";
    String updatedCorpusIndexIt = null;
    String updatedKbIt = null;
    String updatedResidualkbIt = null;
    String updatedStopWordsIt = null;
    String updatedCorpusIndexEn = null;
    String updatedKbEn = null;
    String updatedResidualkbEn = null;
    String updatedStopWordsEn = null;

    public ConfigTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {

        sseConfigFromCache = ConfigCache.getOrCreate(SSEConfigMock.class);
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of testHotReloadProperties method, of class SSEConfiguration.
     *
     * @throws java.lang.RuntimeException
     */
    @Before
    public void testHotReloadProperties() throws RuntimeException, InterruptedException {

        System.out.println("Enter testHotReloadProperties");

        Properties prop = new Properties();
        OutputStream output = null;
        File tempFile = null;
        URL url = null;

        try {

            //tempFile = testFolder.newFile("server.properties");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            url = ofNullable(classLoader.getResource("com/tilab/ca/sse/core/config/test/core.properties")).orElseThrow(RuntimeException::new);

            tempFile = new File(url.toURI().getPath());
            output = new FileOutputStream(tempFile);

            // set the properties value
            prop.setProperty("corpus.index.it", corpusIndexIt);
            prop.setProperty("kb.it", kbIt);
            prop.setProperty("residualkb.it", residualkbIt);
            prop.setProperty("stopWords.it", stopWordsIt);
            prop.setProperty("corpus.index.en", corpusIndexEn);
            prop.setProperty("kb.en", kbEn);
            prop.setProperty("residualkb.en", residualkbEn);
            prop.setProperty("stopWords.en", stopWordsEn);

            prop.store(output, "core.properties");

        } catch (URISyntaxException | IOException ex) {
            System.out.println("Error when reading server.properties file ");
            throw new RuntimeException("Error occurred when reading core.properties file " + ex.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    System.out.println("Error occurred when writing core.properties file");
                    throw new RuntimeException("Error occurred when writing core.properties file " + e.getMessage());
                }
            }
        }

        sseConfigFromCache.addReloadListener(new ReloadListener() {
            public void reloadPerformed(ReloadEvent event) {
                System.out.print("Reload intercepted at " + new Date());
                assertTrue(true);
            }
        });

        System.out.println("The program is running. ");

        Thread.sleep(16000);

        updatedCorpusIndexIt = sseConfigFromCache.corpusIndexIT();
        updatedKbIt = sseConfigFromCache.kbIT();
        updatedResidualkbIt = sseConfigFromCache.residualKBIT();
        updatedStopWordsIt = sseConfigFromCache.stopWordsIT();
        updatedCorpusIndexEn = sseConfigFromCache.corpusIndexEN();
        updatedKbEn = sseConfigFromCache.kbEN();
        updatedResidualkbEn = sseConfigFromCache.residualKBEN();
        updatedStopWordsEn = sseConfigFromCache.stopWordsEN();

        assertEquals(updatedCorpusIndexIt, corpusIndexIt);
        assertEquals(updatedKbIt, kbIt);
        assertEquals(updatedResidualkbIt, residualkbIt);
        assertEquals(updatedStopWordsIt, stopWordsIt);
        assertEquals(updatedCorpusIndexEn, corpusIndexEn);
        assertEquals(updatedKbEn, kbEn);
        assertEquals(updatedResidualkbEn, residualkbEn);
        assertEquals(updatedStopWordsEn, stopWordsEn);

    }

    /**
     * Test of CorpusIndexIt method, of class ConfigTest.
     */
    @Test
    public void testCorpusIndexIt() {

        System.out.println("Enter testCorpusIndexIt");

        // String expResult = "http://127.0.0.1:8811/sse/v2/";
        String result = sseConfigFromCache.corpusIndexIT();

        System.out.println("CorpusIndexIt result=" + result);
        assertEquals(corpusIndexIt, result);

    }

    /**
     * Test of CorpusIndexEn method, of class ConfigTest.
     */
    @Test
    public void testCorpusIndexEn() {

        System.out.println("Enter testCorpusIndexEn");

        // String expResult = "http://127.0.0.1:8811/sse/v2/";
        String result = sseConfigFromCache.corpusIndexEN();

        System.out.println("CorpusIndexEn result=" + result);
        assertEquals(corpusIndexEn, result);

    }

    /**
     * Test of KbIt method, of class ConfigTest.
     */
    @Test
    public void testKbIt() {

        System.out.println("Enter testKbIt");

        // String expResult = "http://127.0.0.1:8811/sse/v2/";
        String result = sseConfigFromCache.kbIT();

        System.out.println("KbIt result=" + result);
        assertEquals(kbIt, result);

    }

    /**
     * Test of KbEn method, of class ConfigTest.
     */
    @Test
    public void testKbEn() {

        System.out.println("Enter testKbIt");

        // String expResult = "http://127.0.0.1:8811/sse/v2/";
        String result = sseConfigFromCache.kbEN();

        System.out.println("KbEn result=" + result);
        assertEquals(kbEn, result);

    }

    /**
     * Test of ResidualKbIt method, of class ConfigTest.
     */
    @Test
    public void testResidualKbIt() {

        System.out.println("Enter testResidualKbIt");

        // String expResult = "http://127.0.0.1:8811/sse/v2/";
        String result = sseConfigFromCache.residualKBIT();

        System.out.println("ResidualKbIt result=" + result);
        assertEquals(residualkbIt, result);

    }

    /**
     * Test of ResidualKbEn method, of class ConfigTest.
     */
    @Test
    public void testResidualKbEn() {

        System.out.println("Enter testResidualKbEn");

        // String expResult = "http://127.0.0.1:8811/sse/v2/";
        String result = sseConfigFromCache.residualKBEN();

        System.out.println("ResidualKbEn result=" + result);
        assertEquals(residualkbEn, result);

    }

    /**
     * Test of stopWordsIt method, of class ConfigTest.
     */
    @Test
    public void testStopWordsIt() {

        System.out.println("Enter testStopWordsIt");

        // String expResult = "http://127.0.0.1:8811/sse/v2/";
        String result = sseConfigFromCache.stopWordsIT();

        System.out.println("StopWordsIt result=" + result);
        assertEquals(stopWordsIt, result);

    }

    /**
     * Test of stopWordsEn method, of class ConfigTest.
     */
    @Test
    public void testStopWordsEn() {

        System.out.println("Enter testStopWordsEn");

        // String expResult = "http://127.0.0.1:8811/sse/v2/";
        String result = sseConfigFromCache.stopWordsEN();

        System.out.println("StopWordsEn result=" + result);
        assertEquals(stopWordsEn, result);

    }

}
