package com.raukhvarger.ms.webfs.front.view.fileviewer;

import com.vaadin.flow.component.icon.VaadinIcon;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileViewer {

    String extension();

    VaadinIcon icon() default VaadinIcon.FILE;

}
