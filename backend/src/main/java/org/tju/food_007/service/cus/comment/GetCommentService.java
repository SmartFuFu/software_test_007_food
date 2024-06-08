package org.tju.food_007.service.cus.comment;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tju.food_007.dto.cus.comment.GetCommentRequestDTO;
import org.tju.food_007.dto.cus.comment.GetCommentResponseDTO;
import org.tju.food_007.repository.cus.comment.GetCommentRepository;

import java.util.ArrayList;
import java.util.List;


@Service
public class GetCommentService {

    @Autowired
    GetCommentRepository getCommentRepository;

    public List<GetCommentResponseDTO> getComment(GetCommentRequestDTO request){
        List<GetCommentResponseDTO> response = new ArrayList<>();
        List<Object[]> objects=getCommentRepository.searchComment(request.getSto_ID());
        for(Object[] temp :objects){
            GetCommentResponseDTO tempDTO =new GetCommentResponseDTO();
            if(temp[0]!=null)tempDTO.setCmt_ID((Integer) temp[0]);
            if(temp[1]!=null)tempDTO.setCmt_content(temp[1].toString());
            if(temp[2]!=null)tempDTO.setCmt_time(temp[2].toString());
            if(temp[3]!=null)tempDTO.setInd_ID((Integer)temp[3]);
            if(temp[4]!=null)tempDTO.setUser_ID((Integer)temp[4]);
            if(temp[5]!=null)tempDTO.setUser_name(temp[5].toString());
            if(temp[6]!=null)tempDTO.setUser_logo(temp[6].toString());

            response.add(tempDTO);

        }

        int page_size = request.getPage_size();
        int page_num = request.getPage_num();
        System.out.println("查询到总数为" + response.size());
        if (response.size() <= page_size * (page_num - 1)) {
            response.clear();
            return response; // 如果请求页超出范围，则返回空列表
        }
        int beg = page_size * (page_num - 1); // 起始索引
        int end = Math.min(page_size * page_num, response.size()); // 结束索引（不包括）

        System.out.println("返回总数为" + (end - beg));

        return response.subList(beg, end);

    }
}
