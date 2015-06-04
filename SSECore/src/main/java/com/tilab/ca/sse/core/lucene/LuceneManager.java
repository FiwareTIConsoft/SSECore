/*-
 * Copyright (C) 2012 Federico Cairo, Giuseppe Futia, Federico Benedetto.
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

import com.tilab.ca.sse.core.classify.Text;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;
import java.io.File;
import java.io.IOException;
import org.apache.lucene.search.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LuceneManager {

    static Logger LOG = Logger.getLogger(LuceneManager.class.getName());

    private Analyzer luceneDefaultAnalyzer = new StandardAnalyzer(
            Version.LUCENE_36);
    private final Directory luceneCorpusIndexDirectory;

    // This attribute is taylored for SSE GUI:
    private final int limitForQueryResult = 7;

    public LuceneManager(Directory directory) throws IOException {
        LOG.log(Level.FINE, "[constructor] - BEGIN");
        luceneCorpusIndexDirectory = directory;
        LOG.log(Level.FINE, "[constructor] - BEGIN");
    }

    // This method is customized from DBpedia Spotlight:
    public static Directory pickDirectory(File indexDir) throws IOException {
        LOG.log(Level.FINE, "[pickDirectory] - BEGIN");
        Directory directory;
        if (System.getProperty("os.name").equals("Linux")
                && System.getProperty("os.arch").contains("64")) {
            directory = new MMapDirectory(indexDir);
        } else if (System.getProperty("os.name").equals("Linux")) {
            directory = new NIOFSDirectory(indexDir);
        } else {
            directory = FSDirectory.open(indexDir);
        }
        LOG.log(Level.FINE, "[pickDirectory] - END");
        return directory;
    }

    public Analyzer getLuceneDefaultAnalyzer() {
        return luceneDefaultAnalyzer;
    }

    public void setLuceneDefaultAnalyzer(Analyzer analyzer) {
        luceneDefaultAnalyzer = analyzer;
    }

    public int getLimitForQueryResult() {
        return limitForQueryResult;
    }

    public Directory getLuceneCorpusIndexDirectory() {
        return luceneCorpusIndexDirectory;
    }

    public Query getQueryForContext(Text context) throws ParseException {
        LOG.log(Level.FINE, "[getQueryForContext] - BEGIN");
        Query result;
        QueryParser parser = new QueryParser(Version.LUCENE_36,
                "CONTEXT", this.getLuceneDefaultAnalyzer());
        LOG.log(Level.FINE, "Analyzer used here: " + getLuceneDefaultAnalyzer());
        // Escape special characters:
        String queryText = context.getText();
        queryText = QueryParser.escape(queryText);
        result = parser.parse(queryText);
        LOG.log(Level.FINE, "Main query from Classify: " + result);
        LOG.log(Level.FINE, "[getQueryForContext] - END");
        return result;
    }
}
