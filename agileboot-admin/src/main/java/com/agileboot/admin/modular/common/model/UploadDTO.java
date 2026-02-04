package com.agileboot.admin.modular.common.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author valarchie
 */
@Data
@Builder
public class UploadDTO {


    private String url;

    private String fileName;

    private String newFileName;

    private String originalFilename;

}
