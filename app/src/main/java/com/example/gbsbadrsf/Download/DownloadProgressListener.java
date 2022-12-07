package com.example.gbsbadrsf.Download;

public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
