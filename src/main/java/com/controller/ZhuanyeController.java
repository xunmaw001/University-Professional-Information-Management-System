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

import com.entity.ZhuanyeEntity;

import com.service.ZhuanyeService;
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
@RequestMapping("/zhuanye")
public class ZhuanyeController {
    private static final Logger logger = LoggerFactory.getLogger(ZhuanyeController.class);

    @Autowired
    private ZhuanyeService zhuanyeService;

    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params){
        logger.debug("Controller:"+this.getClass().getName()+",page方法");
        PageUtils page = zhuanyeService.queryPage(params);
        return R.ok().put("data", page);
    }
    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("Controller:"+this.getClass().getName()+",info方法");
        ZhuanyeEntity zhuanye = zhuanyeService.selectById(id);
        if(zhuanye!=null){
            return R.ok().put("data", zhuanye);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody ZhuanyeEntity zhuanye, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",save");
        Wrapper<ZhuanyeEntity> queryWrapper = new EntityWrapper<ZhuanyeEntity>()
            .eq("name", zhuanye.getName())
            .eq("aobjective", zhuanye.getAobjective())
            .eq("prospects", zhuanye.getProspects())
            .eq("notice_content", zhuanye.getNoticeContent())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZhuanyeEntity zhuanyeEntity = zhuanyeService.selectOne(queryWrapper);
        if("".equals(zhuanye.getZyPhoto()) || "null".equals(zhuanye.getZyPhoto())){
            zhuanye.setZyPhoto(null);
        }
        if(zhuanyeEntity==null){
            zhuanyeService.insert(zhuanye);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ZhuanyeEntity zhuanye, HttpServletRequest request){
        logger.debug("Controller:"+this.getClass().getName()+",update");
        //根据字段查询是否有相同数据
        Wrapper<ZhuanyeEntity> queryWrapper = new EntityWrapper<ZhuanyeEntity>()
            .notIn("id",zhuanye.getId())
            .eq("name", zhuanye.getName())
            .eq("aobjective", zhuanye.getAobjective())
            .eq("prospects", zhuanye.getProspects())
            .eq("notice_content", zhuanye.getNoticeContent())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZhuanyeEntity zhuanyeEntity = zhuanyeService.selectOne(queryWrapper);
        if("".equals(zhuanye.getZyPhoto()) || "null".equals(zhuanye.getZyPhoto())){
                zhuanye.setZyPhoto(null);
        }
        if(zhuanyeEntity==null){
            zhuanyeService.updateById(zhuanye);//根据id更新
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
        zhuanyeService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
}

