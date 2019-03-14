package com.ordre.tsl.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

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
        return getMultipartFile().getOriginalFilename()
                .compareTo(o.getMultipartFile().getOriginalFilename());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexedImageFile that = (IndexedImageFile) o;
        return index == that.index &&
                Objects.equals(multipartFile, that.multipartFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(multipartFile, index);
    }
}
