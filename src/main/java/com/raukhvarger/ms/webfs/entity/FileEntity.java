package com.raukhvarger.ms.webfs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class FileEntity {

    @NonNull
    private String name;

    private Long size = 0L;

    private Boolean isDir = false;

}
