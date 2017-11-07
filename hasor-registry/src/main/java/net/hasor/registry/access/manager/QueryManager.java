/*
 * Copyright 2008-2009 the original author or authors.
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
package net.hasor.registry.access.manager;
import net.hasor.core.Inject;
import net.hasor.core.Singleton;
import net.hasor.registry.access.adapter111222.DataAdapter;
import net.hasor.registry.access.domain.*;
import net.hasor.rsf.domain.RsfServiceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * 提供服务的查询能力
 * @version : 2016年9月18日
 * @author 赵永春(zyc@hasor.net)
 */
@Singleton
public class QueryManager {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Inject
    private DataAdapter dataAdapter;
    @Inject
    private TaskManager taskManager;
    //
    /** 请求Center做一次全量推送 */
    public Result<Void> requestProviders(InstanceInfo instance, String serviceID) {
        Result<List<String>> listResult = this.queryProviders(serviceID);
        if (!listResult.isSuccess()) {
            return DateCenterUtils.buildFailedResult(listResult);
        }
        //
        TaskManager.PublishTask task = new TaskManager.PublishTask();
        task.setAddressList(listResult.getResult());
        task.setPublishRange(Collections.singletonList(instance.getRsfAddress()));
        this.taskManager.addTask(serviceID, task);
        //
        ResultDO<Void> result = new ResultDO<Void>();
        result.setSuccess(true);
        result.setErrorInfo(ErrorCode.OK);
        return result;
    }
    //
    /** 查询提供者列表 */
    public Result<List<String>> queryProviders(String serviceID) {
        //
        final int rowCount = this.dataAdapter.getPointCountByServiceID(serviceID, RsfServiceType.Provider);
        final List<String> resultList = new ArrayList<String>(rowCount);
        final int limitSize = 100;
        int rowIndex = 0;
        while (rowIndex <= rowCount) {
            List<String> targetList = this.dataAdapter.getPointByServiceID(serviceID, RsfServiceType.Provider, rowIndex, limitSize);
            if (targetList == null || targetList.isEmpty()) {
                break;
            }
            rowIndex = rowIndex + limitSize;
            resultList.addAll(targetList);
        }
        //
        ResultDO<List<String>> result = new ResultDO<List<String>>();
        result.setSuccess(true);
        result.setResult(resultList);
        result.setErrorInfo(ErrorCode.OK);
        return result;
    }
}