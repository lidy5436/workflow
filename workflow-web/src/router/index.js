import ProcessDefinition from '@/components/processDefinition/index.vue'
import Home from '@/components/home/index.vue'
import {createRouter, createWebHashHistory} from "vue-router";

const routes = [
    {
        path: '/',
        component: Home,
        redirect: '/processDefinition',
        meta: {
            title: '首页',
        },
        children: [
            {
                path: '/processDefinition',
                component: ProcessDefinition,
                meta: {
                    title: '流程定义'
                }
            }
        ]
    },

]

const router = createRouter({
    history: createWebHashHistory(),
    routes,
})

router.beforeEach((to, from, next) => {
    if (to.meta.title) {
        document.title = to.meta.title
    }
    next()
})

export default router;