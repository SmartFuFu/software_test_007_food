package org.tju.food_007.dto.sto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;



/**
 * @author WGY
 * @create 2024-04-17-14:17
 */
@Setter
@Getter
public class UserUploadLogoImageRequestDTO {
    private MultipartFile image;
    private int user_id;
}
//会用于上传店面图片和营业执照