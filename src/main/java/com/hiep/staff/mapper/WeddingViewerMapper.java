package com.hiep.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.WeddingViewerEntity;
import com.hiep.staff.model.WeddingViewerModel;

@Mapper
public interface WeddingViewerMapper {

    void insertViewer(WeddingViewerModel viewerModel);

    List<WeddingViewerEntity> getAllViewers();

	void deleteViewer(String user_name);

	void deleteViewerById(Integer id);
    
    
}
