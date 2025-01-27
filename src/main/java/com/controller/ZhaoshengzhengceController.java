package com.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.entity.ZhaoshengzhengceEntity;

import com.service.ZhaoshengzhengceService;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 
 * 后端接口
 * @author
 * @email
 * @date 2021-02-22
*/
@RestController
@Controller
@RequestMapping("/zhaoshengzhengce")
public class ZhaoshengzhengceController {
    private static final Logger logger = LoggerFactory.getLogger(ZhaoshengzhengceController.class);

    @Autowired
    private ZhaoshengzhengceService zhaoshengzhengceService;

    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params){
        logger.debug("Controller:"+this.getClass().getName()+",page方法");
        PageUtils page = zhaoshengzhengceService.queryPage(params);
        return R.ok().put("data", page);
    }
    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("Controller:"+this.getClass().getName()+",info方法");
        ZhaoshengzhengceEntity zhaoshengzhengce = zhaoshengzhengceService.selectById(id);
        if(zhaoshengzhengce!=null){
            return R.ok().put("data", zhaoshengzhengce);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody ZhaoshengzhengceEntity zhaoshengzhengce, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",save");
        Wrapper<ZhaoshengzhengceEntity> queryWrapper = new EntityWrapper<ZhaoshengzhengceEntity>()
            .eq("notice_content", zhaoshengzhengce.getNoticeContent())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZhaoshengzhengceEntity zhaoshengzhengceEntity = zhaoshengzhengceService.selectOne(queryWrapper);
        if(zhaoshengzhengceEntity==null){
            zhaoshengzhengceService.insert(zhaoshengzhengce);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ZhaoshengzhengceEntity zhaoshengzhengce, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",update");
        //根据字段查询是否有相同数据
        Wrapper<ZhaoshengzhengceEntity> queryWrapper = new EntityWrapper<ZhaoshengzhengceEntity>()
            .notIn("id",zhaoshengzhengce.getId())
            .eq("notice_content", zhaoshengzhengce.getNoticeContent())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZhaoshengzhengceEntity zhaoshengzhengceEntity = zhaoshengzhengceService.selectOne(queryWrapper);
        if(zhaoshengzhengceEntity==null){
            zhaoshengzhengce.setCreateTime(new Date());
            zhaoshengzhengceService.updateById(zhaoshengzhengce);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        logger.debug("Controller:"+this.getClass().getName()+",delete");
        zhaoshengzhengceService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}

