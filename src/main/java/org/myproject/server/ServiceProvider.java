package org.myproject.server;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务注册中心，管理服务接口与实现类的映射
 */
public class ServiceProvider {
    /**
     * 存放服务接口名与服务实现类的映射
     */
    private Map<String, Object> interfaceProvider;

    public ServiceProvider(){
        this.interfaceProvider = new HashMap<>();
    }

    /**
     * 注册服务实现类，自动注册其实现的所有接口
     *
     * @param service 服务实现类实例
     */
    public void provideServiceInterface(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for(Class<?> clazz : interfaces){
            interfaceProvider.put(clazz.getName(), service);
            System.out.println("注册服务: " + clazz.getName());
        }
    }

    /**
     * 获取服务实现类
     *
     * @param interfaceName 服务接口名
     * @return 服务实现类实例
     */
    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }

    /**
     * 获取所有服务
     *
     * @return 服务映射表
     */
    public Map<String, Object> getServiceProviderMap(){
        return interfaceProvider;
    }
}