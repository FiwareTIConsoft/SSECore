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
package com.tilab.ca.sse.core.classify.threads;

import com.tilab.ca.sse.core.classify.Text;
import com.tilab.ca.sse.core.lucene.IndexesUtil;
import com.tilab.ca.sse.core.lucene.SimpleSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import com.tilab.ca.sse.core.lucene.LuceneManager;
import java.io.IOException;
import java.util.logging.Level;
import org.apache.lucene.queryParser.ParseException;
import org.apache.log4j.Logger;

public class ClassiThread extends Thread {

    static Logger LOG = Logger.getLogger(IndexesUtil.class.getName());

    private ScoreDoc[] hits;
    private final LuceneManager contextLuceneManager;
    private final SimpleSearcher simpleSearcher;
    private final String textPiece;

    public ClassiThread(
            LuceneManager contextLuceneManager,
            SimpleSearcher simpleSearcher,
            String textPiece) {
        LOG.debug("[constructor] - BEGIN");
        this.contextLuceneManager = contextLuceneManager;
        this.simpleSearcher = simpleSearcher;
        this.textPiece = textPiece;
        LOG.debug("[constructor] - END");
    }

    @Override
    public void run() {
        LOG.debug("[run] - BEGIN");
        LOG.debug("Thread " + this.getId() + " started.");
        Query query;
        try {
            query = contextLuceneManager.getQueryForContext(new Text(textPiece));
            hits = simpleSearcher.getHits(query);
        } catch (ParseException | IOException e) {
            LOG.error( "[run] - EXCEPTION: ", e);
            // XXX: is suppression exception here sensible?
        }
        LOG.debug("Thread " + this.getId() + " finished.");
        LOG.debug("[run] - END");
    }

    public ScoreDoc[] getHits() {
        return hits;
    }
}
