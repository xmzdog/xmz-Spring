package com.xmz.service.impl;

import com.xmz.dao.DemoDao;
import com.xmz.factory.BeanFactory;
import com.xmz.service.DemoService;

import java.util.List;

/**
 * @author xmz
 * @date 2023-11-26
 */
public class DemoServiceImpl implements DemoService {

//    private DemoDaoImpl demoDaoImpl = new DemoDaoImpl();
//    private DemoDao demoDao = BeanFactory.getDemoDao();
    private DemoDao demoDao = (DemoDao) BeanFactory.getBean("demoDao");
    public DemoServiceImpl(){
        for (int i = 0; i < 10; i++) {
            System.out.println(BeanFactory.getBean("demoDao"));
        }
    }
    @Override
    public List<String> findAll() {
        return demoDao.findAll();
    }
}
