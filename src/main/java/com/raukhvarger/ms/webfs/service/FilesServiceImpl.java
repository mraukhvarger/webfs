package com.raukhvarger.ms.webfs.service;

import com.raukhvarger.ms.webfs.entity.FileEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilesServiceImpl implements FilesService {


    public List<FileEntity> getAllFilesByFolder(Path path) {
        if (path.toFile().listFiles() == null)
            return new ArrayList<>();

        List<File> files = new ArrayList<>();
        Collections.addAll(files, path.toFile().listFiles());
        return files.stream().map(FileEntity::new).collect(Collectors.toList());
    }

    @Override
    public List<FileEntity> getFilesByFolder(Path path) {
        return getAllFilesByFolder(path).stream().filter(f -> !f.getIsDir()).collect(Collectors.toList());

    }

    @Override
    public List<FileEntity> getFoldersByFolder(Path path) {
        return getAllFilesByFolder(path).stream().filter(FileEntity::getIsDir).collect(Collectors.toList());
    }

}
