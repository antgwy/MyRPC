package org.myproject.server;

import java.util.HashMap;
import java.util.Map;

/**
 * ����ע�����ģ��������ӿ���ʵ�����ӳ��
 */
public class ServiceProvider {
    /**
     * ��ŷ���ӿ��������ʵ�����ӳ��
     */
    private Map<String, Object> interfaceProvider;

    public ServiceProvider(){
        this.interfaceProvider = new HashMap<>();
    }

    /**
     * ע�����ʵ���࣬�Զ�ע����ʵ�ֵ����нӿ�
     *
     * @param service ����ʵ����ʵ��
     */
    public void provideServiceInterface(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for(Class<?> clazz : interfaces){
            interfaceProvider.put(clazz.getName(), service);
            System.out.println("ע�����: " + clazz.getName());
        }
    }

    /**
     * ��ȡ����ʵ����
     *
     * @param interfaceName ����ӿ���
     * @return ����ʵ����ʵ��
     */
    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }

    /**
     * ��ȡ���з���
     *
     * @return ����ӳ���
     */
    public Map<String, Object> getServiceProviderMap(){
        return interfaceProvider;
    }
}