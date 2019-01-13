package com.webnotes.config;

import com.webnotes.business.BusinessLayerConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitialization extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{BusinessLayerConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMVCConfig.class};
    }


    @Override
    protected String[] getServletMappings() {
        return new String[]{ "/" };
    }
}

