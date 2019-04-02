package com.june.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.june.dao.BlogMapper;
import com.june.dao.BlogTypeMapper;
import com.june.model.BlogType;
import com.june.service.BlogTypeService;

@Service
public class BlogTypeServiceImpl implements BlogTypeService {

    @Resource
    private BlogTypeMapper blogTypeMapper;

    @Resource
    private BlogMapper blogMapper;

    @Override
    public List<BlogType> getTypeList() {
        return wrap(blogTypeMapper.getTypeList(null));
    }

    @Override
    public List<BlogType> getTypeList(Map<String, Object> map) {
        return wrap(blogTypeMapper.getTypeList(map));
    }

    private List<BlogType> wrap(List<BlogType> blogTypes) {
        if (CollectionUtils.isEmpty(blogTypes)) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(blogTypes);
    }

    @Override
    public boolean delete(Integer id) {
        return blogTypeMapper.deleteByPrimaryKey(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean batchDelete(Integer id) {
        boolean isSuccess = delete(id);
        if (isSuccess) {
//			SubmitLinkUtils.deleteLink(ResourceType.CATEGORY, id);
            blogMapper.moveBlogToDefaultType(id);
        }
        return isSuccess;
    }

    @Override
    public boolean update(BlogType blogType) {
        boolean isSuccess = blogTypeMapper.updateByPrimaryKeySelective(blogType) > 0;
        if (isSuccess) {
//			SubmitLinkUtils.updateLink(ResourceType.CATEGORY, blogType.getTypeId());
        }
        return isSuccess;
    }

    @Override
    public boolean add(BlogType blogType) {
        boolean isSuccess = blogTypeMapper.insertSelective(blogType) > 0;
        if (isSuccess) {
//			SubmitLinkUtils.createLink(ResourceType.CATEGORY, blogType.getTypeId());
        }
        return isSuccess;
    }

    @Override
    public BlogType findById(Integer id) {
        return blogTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer getIdByName(String typeName) {
        return blogTypeMapper.getIdByName(typeName);
    }

    @Override
    public Integer getCount() {
        return blogTypeMapper.getCount();
    }

}
