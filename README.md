SSE (Social Semantic Enricher) core
===================================

This repository contains the core of SSE (Social Semantic Enricher)

SSE is a tool for classifying and enriching textual documents via Linked Open Data.
It uses [Lucene](http://lucene.apache.org/core/) indexes for its classification 
and enrichment system. 
To build such indexes use SSE Index Builder project.
The core of SSE is the lowest level component that directly interacts with Lucene.

API Initialization
------------------

First you need to initialize the settings and the index using
the following code:

    SSEConfig sseConfigFromCache = ConfigCache.getOrCreate(SSEConfig.class);
    IndexesUtil.init();

API Usage
---------

Once you have initialized SSE's core, as described above, you can
invoke `classify()` to classify text.

    //
    // Here `text` is a String, `numTopics` is a integer and `language`
    // is again a String (typically either "en" or "it").
    //
    Classifier classifier = new Classifier(language);
    List<String[]> res = classifier.classify(text, numTopics);

The `classify()` function follows the traditional SSE policy by which
large texts are divided in chunks classified separately, and the result
is generated merging the classification of each chunk of text.

You can bypass this policy by using the `classifyShortText()` function
that directly passes the text to Lucene. Note, however, that depending on
the Lucene configuration and on the text length, this call may raise an
exception if the resulting Lucene query is too large.

    Classifier classifier = new Classifier(language);
    List<String[]> res = classifier.classifyShortText(text, numTopics);
