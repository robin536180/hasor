/*
 * Copyright 2008-2009 the original 赵永春(zyc@hasor.net).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.hasor.rsf.remoting.address;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.hasor.rsf.RsfBindInfo;
import net.hasor.rsf.adapter.AbstracAddressCenter;
/**
 * 地址管理中心，负责维护服务的远程服务提供者列表。
 * @version : 2014年12月15日
 * @author 赵永春(zyc@hasor.net)
 */
public class AddressCenter extends AbstracAddressCenter {
    private final Map<String, AddressPool> addressMap;
    public AddressCenter() {
        this.addressMap = new ConcurrentHashMap<String, AddressPool>();
    }
    //
    public URL findServiceAddress(RsfBindInfo<?> bindInfo) {
        AddressPool pool = this.addressMap.get(bindInfo.getBindID());
        if (pool == null)
            return null;
        return pool.nextAddress();
    }
    public void invalidAddress(URL address) {
        AddressPool pool = this.addressMap.get(bindInfo.getBindID());
                if (pool == null)
                    return  ;
                pool.
    }
    public void updateStaticAddress(RsfBindInfo<?> bindInfo, List<URL> address) {
        AddressPool pool = this.addressMap.get(bindInfo.getBindID());
        if (pool == null) {
            pool = new AddressPool();
            this.addressMap.put(bindInfo.getBindID(), pool);
        }
        pool.updateAddress(address);
    }
}