package com.raukhvarger.ms.webfs.spring;

import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerExtensionsFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class AppConfiguration {

    @Bean
    public FactoryBean fileExtensionsViewerFactory() {
        ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
        factoryBean.setServiceLocatorInterface(FileViewerExtensionsFactory.class);
        Properties serviceMapping = new Properties();
        serviceMapping.put("", "not_realizable_extension");
        serviceMapping.put("txt", "txtViewer");
        factoryBean.setServiceMappings(serviceMapping);
        return factoryBean;
    }

}
