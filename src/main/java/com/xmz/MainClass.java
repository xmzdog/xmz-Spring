package com.xmz;

import com.xmz.bean.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xmz
 * @date 2023-11-26
 */
public class MainClass {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("spring.xml");
        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
    }
}
