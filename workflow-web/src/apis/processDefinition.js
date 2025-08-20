import request from '@/utils/request.js';

// 分页查询流程定义列表
export const pageApi = (data) => {
    return request({
        url: '/flowable/process-definitions/page',
        method: 'POST',
        data: data
    })
}

// 激活/挂起流程定义
export const toggleSuspendApi = (data) => {
    return request({
        url: '/flowable/process-definitions/toggle-suspend',
        method: 'POST',
        data: data
    })
}
// 部署流程定义
export const processDeploymentApi = (data) => {
    return request({
        url: '/flowable/process-definitions/processDeployment',
        method: 'POST',
        data: data
    })
}