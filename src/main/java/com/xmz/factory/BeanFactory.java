package com.xmz.factory;

import com.xmz.dao.DemoDao;
import com.xmz.dao.impl.DemoOrcalDao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author xmz
 * @date 2023-11-26
 *
 * 当要变更dao时（使用MySql变成Oracal）
 * 我们可以引入一个静态工厂，来创建特定的类型的实现类，在Service中使用工厂来使用我们需要的实现类
 */
public class BeanFactory {
    /**
     * 如果这里源码文件丢失，项目连编译都无法通过
     * 因为这里类与类之间的依赖关系是紧耦合的关系
     * @return
     */
    public static DemoDao getDemoDao(){
        return new DemoOrcalDao(); //这里BeanFactory这个类强依赖于DemoOrcalDao类
//        return new DemoDaoImpl();
    }

    /**
     * 为了解决上面紧耦合的关系，我们可以使用反射，将他们之间的依赖关系转变成弱依赖
     * 反射！反射可以声明一个类的全限定名，来获取它的字节码描述，这样也能构造对象！
     * 这样项目就不会在编译器出现错误
     * @return
     */
    public static DemoDao getDemoDao1() {
        try {
            return (DemoDao) Class.forName("com.xmz.dao.impl.DemoDaoImpl").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DemoDao instantiation error, cause: " + e.getMessage());
        }
    }

    //（硬编码）但是上面的类路径名是写死的，这样在我们每次切换数据库后还得重新编译工程才可以正常运行，
    // 这显得貌似很没必要，应该有更好的处理方案。
    //我们可以使用 外部配置文件的形式解决硬编码的问题

    private static Properties properties;

    // 使用静态代码块初始化properties，加载factord.properties文件
    static {
        properties = new Properties();
        try {
            // 必须使用类加载器读取resource文件夹下的配置文件
            properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("factory.properties"));
        } catch (IOException e) {
            // BeanFactory类的静态初始化都失败了，那后续也没有必要继续执行了
            throw new ExceptionInInitializerError("BeanFactory initialize error, cause: " + e.getMessage());
        }
    }

    /**
     * 使用外部化配置+反射
     * 对于这种可能会变化的配置、属性等，通常不会直接硬编码在源代码中，
     * 而是抽取为一些配置文件的形式（ properties 、xml 、json 、yml 等），
     * 配合程序对配置文件的加载和解析，从而达到动态配置、降低配置耦合的目的。
     * @param beanName
     * @return
     */
//    public static Object getBean(String beanName) {
//        try {
//            Class<?> beanClazz = Class.forName(properties.getProperty("demoDao"));
//            return  beanClazz.newInstance();
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException("BeanFactory have not [" + beanName + "] bean!", e);
//        } catch (IllegalAccessException | InstantiationException e) {
//            throw new RuntimeException("[" + beanName + "] instantiation error!", e);
//        }
//    }


    /**
     * 由于上面的getBean 可能在每次获取一个bean的时候，都会创建一个新的实例
     *  所以我们在这里引入缓存
     *  如果对于这些没必要创建多个对象的组件，如果能有一种机制保证整个工程运行过程中只存在一个对象，
     *  那就可以大大减少资源消耗。
     *  于是可以在 BeanFactory 中加入一个缓存区：
     */
    // 缓存区，保存已经创建好的对象
    private static Map<String, Object> beanMap = new HashMap<>();
    public static Object getBean(String beanName) {
        // 双检锁保证beanMap中确实没有beanName对应的对象
        if (!beanMap.containsKey(beanName)) {
            synchronized (BeanFactory.class) {
                if (!beanMap.containsKey(beanName)) {
                    // 过了双检锁，证明确实没有，可以执行反射创建
                    try {
                        Class<?> beanClazz = Class.forName(properties.getProperty(beanName));
                        Object bean = beanClazz.newInstance();
                        // 反射创建后放入缓存再返回
                        beanMap.put(beanName, bean);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("BeanFactory have not [" + beanName + "] bean!", e);
                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException("[" + beanName + "] instantiation error!", e);
                    }
                }
            }
        }
        return beanMap.get(beanName);
    }


}
