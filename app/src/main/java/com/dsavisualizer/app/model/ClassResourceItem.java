package com.dsavisualizer.app.model;

public class ClassResourceItem {
    public final String id;
    public final String title;
    public final String type;
    public final String fileName;
    public final String mimeType;
    public final int sizeBytes;
    public final String createdAt;
    public final String downloadPath;
    public final String uploaderName;

    public ClassResourceItem(String id, String title, String type, String fileName, String mimeType, int sizeBytes, String createdAt, String downloadPath, String uploaderName) {
        this.id = id == null ? "" : id;
        this.title = title == null ? "" : title;
        this.type = type == null ? "" : type;
        this.fileName = fileName == null ? "" : fileName;
        this.mimeType = mimeType == null ? "" : mimeType;
        this.sizeBytes = sizeBytes;
        this.createdAt = createdAt == null ? "" : createdAt;
        this.downloadPath = downloadPath == null ? "" : downloadPath;
        this.uploaderName = uploaderName == null ? "" : uploaderName;
    }
}
