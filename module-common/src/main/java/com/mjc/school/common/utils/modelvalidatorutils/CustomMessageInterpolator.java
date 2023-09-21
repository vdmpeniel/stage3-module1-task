package com.mjc.school.common.utils.modelvalidatorutils;

import com.mjc.school.common.utils.FileUtils;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

public class CustomMessageInterpolator extends ResourceBundleMessageInterpolator {
    private final static String basePath = "module-common/src/main/resources/validation-messages.properties";
    public CustomMessageInterpolator() {
        super(new PlatformResourceBundleLocator(basePath));
    }
    private static PlatformResourceBundleLocator getResourceLocator(){
        String resourcePath = FileUtils.getAbsolutePath(basePath).toString();
        return new PlatformResourceBundleLocator(resourcePath);
    }
}
