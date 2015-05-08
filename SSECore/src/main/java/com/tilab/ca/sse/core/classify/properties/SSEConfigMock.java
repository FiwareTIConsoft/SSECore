/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tilab.ca.sse.core.classify.properties;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.DefaultValue;
import org.aeonbits.owner.Config.HotReload;
import org.aeonbits.owner.Config.HotReloadType;
import org.aeonbits.owner.Config.Key;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Reloadable;

/**
 *
 * @author andrea
 */
// Will use ASYNC reload type and will check every 15 seconds.
@HotReload(value = 15, type = HotReloadType.ASYNC)
@LoadPolicy(LoadType.MERGE)
@Sources({"classpath:com/tilab/ca/sse/core/config/test/core.properties"})
public interface SSEConfigMock extends Config, Reloadable{
	
    @Key("corpus.index.it")
    @DefaultValue("/TMF/data/it/indexWithTitles-Images-SameAs")
    String corpusIndexIT();
	
    @Key("kb.it")
    @DefaultValue("/TMF/data/it/KB")
    String kbIT();
    
    @Key("residualkb.it")
    @DefaultValue("/TMF/data/it/ResidualKB")
    String residualKBIT();
	
    @Key("stopWords.it")
    @DefaultValue("/TMF/data/it/italian-stopwords.list")
    String stopWordsIT();
	
    @Key("corpus.index.en")
    @DefaultValue("/TMF/data/en/indexWithTitles-Images")
    String corpusIndexEN();
	
    @Key("kb.en")
    @DefaultValue("/TMF/data/en/KB")
    String kbEN();
    
    @Key("residualkb.en")
    @DefaultValue("/TMF/data/en/ResidualKB")
    String residualKBEN();
    
    @Key("stopWords.en")
    @DefaultValue("/TMF/data/en/english-stopwords.list")
    String stopWordsEN();
	
}
