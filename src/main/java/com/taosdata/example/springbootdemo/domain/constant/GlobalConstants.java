package com.taosdata.example.springbootdemo.domain.constant;

/**
 * 全局的key常量 (业务无关的key)
 *
 * @author Lion Li
 */
public interface GlobalConstants {

    /**
     * 全局 redis key
     */

    String DEVICE_STATUS_KEY =  "device::status:";//设备状态key
    String DEVICE_INFO_KEY =  "device::info:";//设备信息key

}
