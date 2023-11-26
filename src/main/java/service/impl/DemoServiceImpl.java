package service.impl;

import dao.impl.DemoDaoImpl;
import service.DemoService;

import java.util.List;

/**
 * @author xmz
 * @date 2023-11-26
 */
public class DemoServiceImpl implements DemoService {

    private DemoDaoImpl demoDaoImpl = new DemoDaoImpl();
    @Override
    public List<String> findAll() {
        return demoDaoImpl.findAll();
    }
}
