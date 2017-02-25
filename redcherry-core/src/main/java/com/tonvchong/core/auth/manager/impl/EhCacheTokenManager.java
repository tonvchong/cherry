package com.tonvchong.core.auth.manager.impl;

import java.util.UUID;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.stereotype.Component;

import com.tonvchong.core.auth.manager.TokenManager;
import com.tonvchong.core.auth.user.AuthUser;
import com.tonvchong.core.util.config.CommonConfig;
import com.tonvchong.core.util.security.MD5Util;

@Slf4j
@Component("tokenManager")
public class EhCacheTokenManager implements TokenManager {
	@Resource(name = "authCache")
	private Cache cache;

	@PreDestroy
	protected void shutdown() {
		if (cache != null) {
			cache.getCacheManager().shutdown();
		}
	}

	@Override
	public String generateToken(AuthUser user) {
		AuthUser userInCache = user;
		String uid = String.valueOf(userInCache.getUserid());
		String token = MD5Util.MD5Encode(UUID.randomUUID().toString().replace("-", ""),"UTF-8");
		log.info("generateToken,uid="+uid,"token="+token);

		// 在闲置状态下，允许其在缓存中的最大时间
		int tokenTimeoutMinutes = CommonConfig.getInt("TOKEN_TIMEOUT_MINITUES");
		int seconds = tokenTimeoutMinutes * 60;
		//log.info("token timeout seconds=" + seconds);
		
		removeTokenByUid(uid);
		cache.put(new Element(uid, token, seconds, 0));
		cache.put(new Element(token, userInCache, seconds, 0));
		return token;
	}
	
	public String getToken(String uid) {
		log.info("getToken,userid="+uid);
		Element e = cache.get(uid);
		if(e!=null) {
			return (String)e.getObjectValue();
		}
		log.info("uid as key cache not exist");
		return null;
	}
		
	public AuthUser getUser(String token) {
		Element e = cache.get(token);
		if(e!=null) {
			return (AuthUser)e.getObjectValue();
		}
		log.info("token as key cache not exist");
		return null;
	}
	
	@Override
	public boolean check(String token) {
		Element e = cache.get(token);
		log.info("check,token="+token +",element="+e);
		if(e!=null) {
			AuthUser user = (AuthUser)e.getObjectValue();
			log.info("check,userid="+user.getUserid());
			//操作uid为key的cache，延长缓存时间
			cache.get(user.getUserid());
			return true;
		}
		log.info("token as key cache not exist");
		return false;
	}

	@Override
	public void removeToken(String token) {
		Element e = cache.get(token);
		if(e!=null) {
			AuthUser user = (AuthUser)e.getObjectValue();
			if(user!=null) {
				cache.remove(user.getUserid());
			}
			cache.remove(token);
		}
	}
	
	public void removeTokenByUid(String uid) {
		Element e = cache.get(uid);
		if(e!=null) {
			String token = (String)e.getObjectValue();
			cache.remove(uid);
			cache.remove(token);			
		}
	}
}
