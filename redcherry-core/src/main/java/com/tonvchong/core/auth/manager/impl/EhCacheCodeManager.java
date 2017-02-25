package com.tonvchong.core.auth.manager.impl;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.stereotype.Component;

import com.tonvchong.core.auth.manager.CodeManager;
import com.tonvchong.core.util.config.CommonConfig;

@Slf4j
@Component("codeManager")
public class EhCacheCodeManager implements CodeManager {

	@Resource(name = "codeCache")
	private Cache cache;

	@PreDestroy
	protected void shutdown() {
		if (cache != null) {
			cache.getCacheManager().shutdown();
		}
	}

	@Override
	public String setCode(String sessionId, String code) {
		// 在闲置状态下，允许其在缓存中的最大时间
		int tokenTimeoutMinutes = CommonConfig.getInt("CODE_TIMEOUT_MINITUES");
		int seconds = tokenTimeoutMinutes * 60;
		removeCode(sessionId);
		log.info("=========setCode,ck="+sessionId+",code="+code);
		cache.put(new Element(sessionId, code, seconds, 0));
		return code;
	}

	@Override
	public String getCode(String sessionId) {
		log.info("==========getCode,ck="+sessionId);
		Element e = cache.get(sessionId);
		if(e!=null) {
			String code = (String)e.getObjectValue();
			log.info("getCode,code="+code);
			return code;
		}
		log.info("sessionId as key cache not exist");
		return null;
	}


	@Override
	public void removeCode(String sessionId) {
		log.info("===========removeCode,ck="+sessionId);
		Element e = cache.get(sessionId);
		if(e!=null) {
			cache.remove(sessionId);
		}
	}

}
