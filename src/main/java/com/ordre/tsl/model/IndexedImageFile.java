package com.ordre.tsl.model;

import org.springframework.web.multipart.MultipartFile;

public class IndexedImageFile implements Comparable<IndexedImageFile> {

    private final MultipartFile multipartFile;
    private final int index;

    public IndexedImageFile(MultipartFile multipartFile, int index) {
        this.multipartFile = multipartFile;
        this.index = index;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int compareTo(IndexedImageFile o) {
        return getIndex() - o.getIndex();
    }
}
