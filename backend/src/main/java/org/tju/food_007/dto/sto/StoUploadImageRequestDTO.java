package org.tju.food_007.dto.sto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;


/**
 * @author WGY
 * @create 2024-03-20-15:03
 */
@Getter
@Setter
public class StoUploadImageRequestDTO {
    private ArrayList<MultipartFile> images;
    private int com_id;
}
