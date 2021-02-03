package com.voipadmin.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.voipadmin.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.voipadmin.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.voipadmin.domain.User.class.getName());
            createCache(cm, com.voipadmin.domain.Authority.class.getName());
            createCache(cm, com.voipadmin.domain.User.class.getName() + ".authorities");
            createCache(cm, com.voipadmin.domain.Device.class.getName());
            createCache(cm, com.voipadmin.domain.Device.class.getName() + ".settings");
            createCache(cm, com.voipadmin.domain.Device.class.getName() + ".voipAccounts");
            createCache(cm, com.voipadmin.domain.Device.class.getName() + ".children");
            createCache(cm, com.voipadmin.domain.DeviceModel.class.getName());
            createCache(cm, com.voipadmin.domain.DeviceModel.class.getName() + ".options");
            createCache(cm, com.voipadmin.domain.OtherDeviceType.class.getName());
            createCache(cm, com.voipadmin.domain.ResponsiblePerson.class.getName());
            createCache(cm, com.voipadmin.domain.Department.class.getName());
            createCache(cm, com.voipadmin.domain.VoipAccount.class.getName());
            createCache(cm, com.voipadmin.domain.AsteriskAccount.class.getName());
            createCache(cm, com.voipadmin.domain.Setting.class.getName());
            createCache(cm, com.voipadmin.domain.Setting.class.getName() + ".selectedValues");
            createCache(cm, com.voipadmin.domain.Option.class.getName());
            createCache(cm, com.voipadmin.domain.Option.class.getName() + ".possibleValues");
            createCache(cm, com.voipadmin.domain.Option.class.getName() + ".models");
            createCache(cm, com.voipadmin.domain.OptionValue.class.getName());
            createCache(cm, com.voipadmin.domain.OptionValue.class.getName() + ".settings");
            createCache(cm, com.voipadmin.domain.Vendor.class.getName());
            createCache(cm, com.voipadmin.domain.Vendor.class.getName() + ".deviceModels");
            createCache(cm, com.voipadmin.domain.Vendor.class.getName() + ".options");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
