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

import com.tilab.ca.sse.core.util.Ret;
import com.tilab.ca.sse.core.util.SSEUtils;
import static com.tilab.ca.sse.core.util.SSEUtils.unchecked;
import com.tilab.ca.sse.core.util.SSEVariables;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import java.io.File;
import java.io.UncheckedIOException;
import java.util.Optional;
import static java.util.Optional.ofNullable;

public class IndexesUtil {

	static Log LOG = LogFactory.getLog(IndexesUtil.class);
	public static SimpleSearcher ITALIAN_CORPUS_INDEX_SEARCHER;
	public static SimpleSearcher ENGLISH_CORPUS_INDEX_SEARCHER;

	/**
	 * Initialize the classifiers. This static method initializes the italian and the english
	 * classifiers under the hood. You must call this function after you have constructed an
	 * instance of the SSEVariables class as described in SSEVariables docs.
	 *
	 * If you don't call this method, when you use the classifier you will get a
	 * NullPointerException in Classifier().
	 *
	 * @since 2.0.0.0.
	 */
	public static void init() {
		LOG.debug("[initializator] - BEGIN");

		ITALIAN_CORPUS_INDEX_SEARCHER = indexLoading(() -> {
			// build italian searcher
			Directory contextIndexDirIT = LuceneManager.pickDirectory(new File(SSEVariables.CORPUS_INDEX_IT));
			LOG.info("Corpus index used for italian: " + contextIndexDirIT);
			LuceneManager contextLuceneManagerIT = new LuceneManager(contextIndexDirIT);
			contextLuceneManagerIT.setLuceneDefaultAnalyzer(new ItalianAnalyzer(Version.LUCENE_36, SSEVariables.STOPWORDS_IT));
			return new SimpleSearcher(contextLuceneManagerIT);
		}).orElse(null); //FIXME not a good use of Optional -> use a default SimpleSearcher

		ENGLISH_CORPUS_INDEX_SEARCHER = indexLoading(() -> {
			// build english searcher
			Directory contextIndexDirEN = LuceneManager.pickDirectory(new File(SSEVariables.CORPUS_INDEX_EN));
			LOG.info("Corpus index used for english: " + contextIndexDirEN);
			LuceneManager contextLuceneManagerEN = new LuceneManager(contextIndexDirEN);
			contextLuceneManagerEN.setLuceneDefaultAnalyzer(new EnglishAnalyzer(Version.LUCENE_36, SSEVariables.STOPWORDS_EN));
			return new SimpleSearcher(contextLuceneManagerEN);
		}).orElse( null ); //FIXME not a good use of Optional -> use a default SimpleSearcher
		
		if( ITALIAN_CORPUS_INDEX_SEARCHER == null && ENGLISH_CORPUS_INDEX_SEARCHER ==null )
			throw new RuntimeException("Indexes not available");
		
		LOG.debug("[initializator] - END");
	}

	private static <T> Optional<T> indexLoading(Ret<T> iLoadFunction) {
		T result = null;
		try {
			result = iLoadFunction.ret();
			LOG.info("Index correctly available");
		} catch (Exception e) {
			LOG.warn("WARNING: Indexes not available");
		}
		return ofNullable(result);
	}

}
