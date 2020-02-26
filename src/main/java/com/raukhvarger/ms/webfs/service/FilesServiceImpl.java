package com.raukhvarger.ms.webfs.service;

import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewer;
import com.raukhvarger.ms.webfs.front.view.fileviewer.FileViewerItem;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.lang.reflect.Constructor;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@UIScope
public class FilesServiceImpl implements FilesService {

    private final Logger logger = LoggerFactory.getLogger(FilesServiceImpl.class);

    private List<FileViewerItem> fileViewerItems;

    private final ApplicationContext appContext;

    public FilesServiceImpl(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    @PostConstruct
    private void init() {
        fileViewerItems = getFileViewerClasses();
    }

    public List<FileViewerItem> getAllFilesByFolder(Path path) {
        if (path.toFile().listFiles() == null)
            return new ArrayList<>();

        List<File> files = new ArrayList<>();
        Collections.addAll(files, path.toFile().listFiles());
        return files.stream().map(f -> {
            Path filePath = f.toPath();
            FileViewerItem extFile = getFileViewer(filePath);
            return FileViewerItem.builder()
                    .name(f.getName())
                    .extension(getFileExtension(filePath))
                    .size(FileUtils.byteCountToDisplaySize(f.length()))
                    .icon(extFile.getIcon())
                    .isFolder(f.isDirectory())
                    .view(extFile.getView())
                    .path(filePath)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<FileViewerItem> getFilesByFolder(Path path) {
        return getAllFilesByFolder(path).stream().filter(f -> !f.getIsFolder()).collect(Collectors.toList());
    }

    @Override
    public List<FileViewerItem> getFoldersByFolder(Path path) {
        return getAllFilesByFolder(path).stream().filter(FileViewerItem::getIsFolder).collect(Collectors.toList());
    }

    private String getFileExtension(Path path) {
        File f = path.toFile();
        return f.getName().contains(".") ? f.getName().substring(f.getName().lastIndexOf(".") + 1) : "";
    }

    private List<FileViewerItem> getFileViewerClasses() {
        List<FileViewerItem> result = new ArrayList<>();

        logger.info("Load extensions");

        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(true);

        scanner.addIncludeFilter(new AnnotationTypeFilter(FileViewer.class));

        scanner.findCandidateComponents("com.raukhvarger.ms.webfs.front.view.fileviewer").stream()
                .filter(bd -> bd instanceof ScannedGenericBeanDefinition)
                .map(bd -> (ScannedGenericBeanDefinition) bd)
                .forEach(bd -> {
                    try {
                        Class<?> c = Class.forName(bd.getBeanClassName());
                        Constructor constructor = c.getConstructor(Path.class);
                        result.add(
                                FileViewerItem.builder()
                                        .view((Path path) -> {
                                            Component view = null;
                                            try {
                                                view = (Component) constructor.newInstance(path);
                                            } catch (Exception e) {
                                                logger.error("Failed creation FileViewer instance");
                                            }
                                            return view;
                                        })
                                        .extension(c.getAnnotation(FileViewer.class).extension())
                                        .icon(c.getAnnotation(FileViewer.class).icon())
                                        .build()
                        );
                    } catch (Throwable e) {
                        logger.error("FileViewer extension not loaded", e);
                    }
                });

        logger.info("Extensions: " + result);
        return result;
    }

    @Override
    public FileViewerItem getFileViewer(Path path) {
        return fileViewerItems.stream()
                .filter(ext -> getFileExtension(path).endsWith(ext.getExtension()))
                .findFirst()
                .orElse(FileViewerItem.builder()
                        .icon(VaadinIcon.FILE_O)
                        .build()
                );
    }

}
