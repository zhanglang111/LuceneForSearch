package com.etc.newmoudle.Service;


import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface UploadService {

    public void uploadFileToServer(File[] files);
}
