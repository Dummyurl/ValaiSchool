package com.valai.school.interfaces;

/**
 * @author by Mohit Arora on 17/2/18.
 */

public interface GetFileDownload {
    void getFileDownloadCall(String url, String fileName);

    boolean isInterNetConnected();

    void showMessage();
}
