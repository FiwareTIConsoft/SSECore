/*-
 * Copyright (C) 2012 Federico Cairo, Giuseppe Futia, Federico Benedetto
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.tilab.ca.sse.core.lucene;

import com.tilab.ca.sse.core.classify.properties.SSEConfig;
import com.tilab.ca.sse.core.util.Ret;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import static java.util.Optional.ofNullable;
import java.util.Set;
import org.aeonbits.owner.ConfigCache;

public class IndexesUtil {

    static Logger LOG = Logger.getLogger(IndexesUtil.class.getName());

    public static SimpleSearcher ITALIAN_CORPUS_INDEX_SEARCHER;
    public static SimpleSearcher ENGLISH_CORPUS_INDEX_SEARCHER;
    static SSEConfig sseConfigFromCache;

    /**
     * Initialize the classifiers. This static method initializes the italian
     * and the english classifiers under the hood. You must call this function
     * after you have constructed an instance of the SSEVariables class as
     * described in SSEVariables docs.
     *
     * If you don't call this method, when you use the classifier you will get a
     * NullPointerException in Classifier().
     *
     * @since 2.0.0.0.
     */
    public static void init() {
        LOG.log(Level.FINE, "[initializator] - BEGIN");

        sseConfigFromCache = ConfigCache.getOrCreate(SSEConfig.class);

        ITALIAN_CORPUS_INDEX_SEARCHER = indexLoading(() -> {
            // build italian searcher
            Directory contextIndexDirIT = LuceneManager.pickDirectory(new File(sseConfigFromCache.corpusIndexIT()));
            LOG.info("Corpus index used for italian: " + contextIndexDirIT);
            LuceneManager contextLuceneManagerIT = new LuceneManager(contextIndexDirIT);
            contextLuceneManagerIT.setLuceneDefaultAnalyzer(new ItalianAnalyzer(Version.LUCENE_36, getStopWords(sseConfigFromCache.stopWordsIT())));
            return new SimpleSearcher(contextLuceneManagerIT);
        }).orElse(null); //FIXME not a good use of Optional -> use a default SimpleSearcher

        ENGLISH_CORPUS_INDEX_SEARCHER = indexLoading(() -> {
            // build english searcher
            Directory contextIndexDirEN = LuceneManager.pickDirectory(new File(sseConfigFromCache.corpusIndexEN()));
            LOG.info("Corpus index used for english: " + contextIndexDirEN);
            LuceneManager contextLuceneManagerEN = new LuceneManager(contextIndexDirEN);
            contextLuceneManagerEN.setLuceneDefaultAnalyzer(new EnglishAnalyzer(Version.LUCENE_36, getStopWords(sseConfigFromCache.stopWordsEN())));
            return new SimpleSearcher(contextLuceneManagerEN);
        }).orElse(null); //FIXME not a good use of Optional -> use a default SimpleSearcher

        if (ITALIAN_CORPUS_INDEX_SEARCHER == null && ENGLISH_CORPUS_INDEX_SEARCHER == null) {
            throw new RuntimeException("Indexes not available");
        }

        LOG.log(Level.FINE, "[initializator] - END");
    }

    private static <T> Optional<T> indexLoading(Ret<T> iLoadFunction) {
        T result = null;
        try {
            result = iLoadFunction.ret();
            LOG.info("Index correctly available");
        } catch (Exception e) {
            LOG.log(Level.WARNING, "WARNING: Indexes not available");
        }
        return ofNullable(result);
    }

    public static Set<String> getStopWords(String stopwordsFilePath) {
        LOG.log(Level.FINE, "[getStopWords] - BEGIN");
        ArrayList<String> stopWordsList = new ArrayList<String>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(stopwordsFilePath));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stopWordsList.add(line.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Could not read stopwords file at location: " + stopwordsFilePath);
        }
        Set<String> stopwordsSet = new HashSet<String>(stopWordsList);
        LOG.log(Level.FINE, "[getStopWords] - END");
        return stopwordsSet;
    }

}
