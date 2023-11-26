package com.xmz.dao.impl;

import com.xmz.dao.DemoDao;

import java.util.Arrays;
import java.util.List;

/**
 * @author xmz
 * @date 2023-11-26
 */
public class DemoDaoImpl implements DemoDao {
    @Override
    public List<String> findAll() {
        return Arrays.asList("aaa","bbb","ccc");
    }
}
