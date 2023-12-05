import {reactive} from 'vue'
import {defineStore} from "pinia";

export const useStore = defineStore('store', () => {
    const auth = reactive({
        account:null
    })
    const forum = reactive({
        types:[]
    })
    return { auth,forum }
})

export class store {
}