<template>
    <div id="app">
        <img alt="Vue logo" src="./assets/logo.png">
        用户名：<input type="text" v-model="username"/>
        密码：<input type="password" v-model="password"/>
        <button @click.prevent="login">登录</button>

        <hr>
        <button @click="getData">获取授权后的数据</button>
        <div>
            {{data}}
        </div>
    </div>
</template>

<script>
    import axios from './axios'
    export default {
        name: 'app',
        data(){
            return {
                username: null,
                password: null,
                data: null
            }
        },
        created(){
            axios.defaults.withCredentials = true;
        },
        components: {
        },
        methods: {
            setToken(token){
                axios.defaults.headers.common['Authorization'] = token;
            },
            getData(){
                axios.get('/api/data').then(res => {
                    this.data = res.data
                })
            },
            login(){
                axios.post('/api/login', {
                    username: this.username,
                    password: this.password
                }).then(res => {
                    console.log('登录成功', res.data);
                    this.setToken(res.data.token)
                }).catch(err => {
                })
            }
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
        margin-top: 60px;
    }
</style>
