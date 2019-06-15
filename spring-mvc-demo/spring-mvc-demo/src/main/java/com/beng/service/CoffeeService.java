package com.beng.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.beng.model.Coffee;
import com.beng.repository.CoffeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig
// @CacheConfig主要用于配置该类中会用到的一些共用的缓存配置
// @CacheConfig的作用：抽取@Cacheable、@CachePut、@CacheEvict的公共属性值
// @CacheConfig的属性
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    // 缓存用在更新/插入比较少的情况
    @Cacheable(cacheNames = "coffeeList") // 缓存结果
    public List<Coffee> getAllCoffee() {
        return coffeeRepository.findAll(Sort.by("id"));
    }

    /*1. @Cacheable的几个属性详解：
     *       cacheNames/value：指定缓存组件的名字
     *       key：缓存数据使用的key,可以用它来指定。默认使用方法参数的值，一般不需要指定
     *       keyGenerator：作用和key一样，二选一
     *       cacheManager和cacheResolver作用相同：指定缓存管理器，二选一
     *       condition：指定符合条件才缓存，比如：condition="#id>3"
     *                   也就是说传入的参数id>3才缓存数据
     *      unless：否定缓存，当unless为true时不缓存，可以获取方法结果进行判断
     *      sync：是否使用异步模式*/
    // @Cacheable(cacheNames= "coffee")
    // @Cacheable(cacheNames= "coffee",key="#name",condition="#name != 'admin'")
    @Cacheable(cacheNames = "coffee", key = "#name")
    public Coffee getCoffeeByName(String name) {
        return coffeeRepository.findCoffeeByName(name);
    }

    // @CachePut必须结合@Cacheable一起使用，否则没什么意义
    // @CachePut的作用：即调用方法，又更新缓存数据 ，修改了数据库中的数据，同时又更新了缓存！
    
    /**
     * @CachePut:即调用方法，又更新缓存数据
     * 修改了数据库中的数据，同时又更新了缓存
     *
     *运行时机：
     * 1.先调用目标方法
     * 2.将目标方法返回的结果缓存起来
     *
     * 测试步骤：
     * 1.查询name的咖啡信息
     * 2.以后查询还是之前的结果
     * 3.更新name的咖啡信息
     * 4.查询name=？返回的结果是什么？
     *     应该是更新后的咖啡信息
     *     但只更新了数据库，但没有更新缓存是什么原因？
     * 5.如何解决缓存和数据库同步更新？
     * 这样写：@CachePut(cacheNames = "coffee",key = "#coffee.name")
     *         @CachePut(cacheNames = "coffee",key = "#result.name")
     */
    @CachePut(cacheNames = "coffee", key = "#result.name")
    public Coffee updateCoffee(Coffee coffee) {
        return coffeeRepository.save(coffee);
    }
    
    /**
     *   @Caching是 @Cacheable、@CachePut、@CacheEvict注解的组合
     *   以下注解的含义：
     *   1.当使用指定名字查询数据库后，数据保存到缓存
     *   2.现在使用id、name就会直接查询缓存，而不是查询数据库
     */
    @Caching(
            cacheable = {@Cacheable(cacheNames="coffee",key="#name")},
            put={ @CachePut(key = "#result.id"),
                  @CachePut(key = "#result.name")
                }
    )
    public Coffee queryCoffeeByName(String name){
        return coffeeRepository.findCoffeeByName(name);
    }

    /**
     * @CacheEvict:清除缓存
     *    1.key:指定要清除缓存中的某条数据
     *    2.allEntries=true:删除缓存中的所有数据
     *    beforeInvocation=false:默认是在方法之后执行清除缓存
     *    3.beforeInvocation=true:现在是在方法执行之前执行清除缓存，
     *                          作用是：只清除缓存、不删除数据库数据
     */
    //@CacheEvict(cacheNames = "coffee",key = "#name")
    @CacheEvict(cacheNames = "coffee")
    public void delete(String name) {
    };

    @CacheEvict(cacheNames = "coffeeList")
    public void reload() {

    }

    public List<Coffee> getCoffeeByName(List<String> names) {
        return coffeeRepository.findByNameInOrderById(names);
    }
}