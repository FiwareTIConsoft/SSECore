Tell Me First's core
====================

This repository contains the core of
[TellMeFirst](https://github.com/TellMeFirst/TellMeFirst).

TellMeFirst is a tool for classifying and enriching
textual documents via Linked Open Data.
It uses [Lucene](http://lucene.apache.org/core/) indexes
for its classification and enrichment system. To build such
indexes use our [fork of the DBpedia Spotlight
project](https://github.com/TellMeFirst/dbpedia-spotlight/tree/social_semantic_enricher).

The core of TellMeFirst is the lowest level component that
directly interacts with Lucene.

Use the API exported by this module as follows. See also how the API is
used by [ssecore_build_cli](https://github.com/bassosimone/ssecore_build_cli/blob/master/ssecore_cli/src/main/java/it/polito/social_semantic_enricher/cli/SSECoreCli.java) and by
[ssecore_build_war](https://github.com/bassosimone/ssecore_build_war/blob/master/ssecore_jaxrs/src/main/java/it/polito/social_semantic_enricher/jaxrs/ClassifyResource.java).

API Initialization
------------------

First you need to initialize the settings and the index using
the following code:

    SSEVariables variables = new SSEVariables("/path/to/config/file");
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
