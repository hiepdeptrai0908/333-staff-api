package com.hiep.staff.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hiep.staff.entity.WeddingEntity;
import com.hiep.staff.model.WeddingModel;

@Mapper
public interface WeddingMapper {

	void insertAcceptWedding(WeddingModel weddingModel);

	List<WeddingEntity> getAllWish();

}
