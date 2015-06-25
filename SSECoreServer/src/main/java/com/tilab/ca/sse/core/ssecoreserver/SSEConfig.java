/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tilab.ca.sse.core.ssecoreserver;

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
//@Sources({"file:/SSE/CORE/core.properties"})
@Sources({"file:${core.path}core.properties"})

public interface SSEConfig extends Config, Reloadable{
    
    @Key("rest.service.url")
    @DefaultValue("http://0.0.0.0:8079/sse/")
    String serviceUrl();

}
