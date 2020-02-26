package com.raukhvarger.ms.webfs.front.view.fileviewer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.nio.file.Path;
import java.util.function.Function;

@Data
@Builder
@ToString
public class FileViewerItem {

    private String name;

    @Builder.Default
    private String size = "0";

    @Builder.Default
    private Boolean isFolder = false;

    private Path path;

    private VaadinIcon icon;

    private String extension;

    private Function<Path, Component> view;

}
