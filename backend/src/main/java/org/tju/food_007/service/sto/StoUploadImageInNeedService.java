package org.tju.food_007.service.sto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.tju.food_007.dto.sto.StoUploadImageInNeedRequestDTO;
import org.tju.food_007.dto.sto.StoUploadImageRequestDTO;
import org.tju.food_007.model.CommodityImageEntity;
import org.tju.food_007.model.StoreImageEntity;
import org.tju.food_007.model.StoreLicenseEntity;
import org.tju.food_007.repository.sto.StoUploadStoImagesRepository;
import org.tju.food_007.repository.sto.StoUploadStoLicenseRepository;

import java.io.IOException;
import java.io.InputStream;

import static org.tju.food_007.utils.ObsOperationTool.uploadInputStream;

/**
 * @author WGY
 * @create 2024-04-17-14:22
 */
@Service
public class StoUploadImageInNeedService {
    @Autowired
    StoUploadStoImagesRepository stoUploadStoImagesRepository;
    @Autowired
    StoUploadStoLicenseRepository stoUploadStoLicenseRepository;
    @Transactional
    public void UploadStoImage(StoUploadImageInNeedRequestDTO formDTO) throws IOException {
        Integer sto_image_index=0;
        System.out.println("formDTO.getSto_id() ： "+formDTO.getSto_id());

        for(MultipartFile image_file : formDTO.getImages()){
            InputStream image_obs=image_file.getInputStream();
            String file_name=String.valueOf(formDTO.getSto_id())+"_"+String.valueOf(sto_image_index)+".jpg";
            sto_image_index++;
            uploadInputStream("store_image/",file_name,image_obs);
            StoreImageEntity stoImage=new  StoreImageEntity();
            stoImage.setStoId(formDTO.getSto_id());
            stoImage.setStoImage("store_image/"+file_name);
            stoUploadStoImagesRepository.save(stoImage);
        }
    }
    @Transactional
    public void UploadStoLicenseImage(StoUploadImageInNeedRequestDTO formDTO) throws IOException {
        Integer sto_image_index=0;
        System.out.println("formDTO.getSto_id() ： "+formDTO.getSto_id());

        for(MultipartFile image_file : formDTO.getImages()){
            InputStream image_obs=image_file.getInputStream();
            String file_name=String.valueOf(formDTO.getSto_id())+"_"+String.valueOf(sto_image_index)+".jpg";
            sto_image_index++;
            uploadInputStream("store_license_image/",file_name,image_obs);
            StoreLicenseEntity stoImage=new StoreLicenseEntity();
            stoImage.setStoId(formDTO.getSto_id());
            stoImage.setStoLicense("store_license_image/"+file_name);
            stoUploadStoLicenseRepository.save(stoImage);
        }
    }
}
