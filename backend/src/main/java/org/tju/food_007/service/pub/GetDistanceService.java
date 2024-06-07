package org.tju.food_007.service.pub;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tju.food_007.dto.pub.GetDistanceRequestDTO;
import org.tju.food_007.dto.pub.GetDistanceResponseDTO;

import org.tju.food_007.model.StoreEntity;
import org.tju.food_007.repository.pub.GetDistanceRepository;

import org.tju.food_007.utils.GeoService;

import java.util.ArrayList;


/**
 * @author WGY
 * @create 2024-03-10-16:18
 */
@Service
public class GetDistanceService {
    @Autowired
    GetDistanceRepository getDistanceRepository;
    @Autowired
    GeoService geoService;
    @Transactional
    public GetDistanceResponseDTO GetStoDistance(GetDistanceRequestDTO request)
    {
        ArrayList<StoreEntity> allStoreList=getDistanceRepository.findAllById(request.getSto_id());
        GetDistanceResponseDTO responseDTO=new  GetDistanceResponseDTO();
        ArrayList<Double> stoDistance=new ArrayList<Double>();

        for(StoreEntity sto :allStoreList){
            System.out.println(request.getUser_latitude());
            System.out.println(request.getUser_longitude());
            System.out.println(sto.getStoLatitude());
            System.out.println(sto.getStoLongitude());
            stoDistance.add(geoService.calculateDistanceInKilometer(Double.parseDouble(request.getUser_latitude()),
                    Double.parseDouble(request.getUser_longitude()),Double.parseDouble(sto.getStoLatitude()),
                    Double.parseDouble(sto.getStoLongitude())));

        }
        responseDTO.setDistance_list(stoDistance);
        return responseDTO;
    }

}
