package org.tju.food_007.dto.sto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

/**
 * @author WGY
 * @create 2024-04-17-14:17
 */
@Setter
@Getter
public class StoUploadImageInNeedRequestDTO {
    private ArrayList<MultipartFile> images;
    private int sto_id;
}
//会用于上传店面图片和营业执照