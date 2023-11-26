package com.xmz;

import com.xmz.bean.Cat;
import com.xmz.bean.Person;
import com.xmz.dao.DemoDao;
import com.xmz.dao.impl.DemoDaoImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xmz
 * @date 2023-11-26
 */
public class MainClass {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("springXml/spring.xml");

        // 依赖查找-------byName
        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);

        //依赖查找--------byType
//        DemoDao demoDao = beanFactory.getBean(DemoDaoImpl.class);
//        System.out.println(demoDao.findAll());

        Cat cat = beanFactory.getBean(Cat.class);
        System.out.println(cat);


    }
}
