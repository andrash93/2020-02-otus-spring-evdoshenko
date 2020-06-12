<template>
    <div id="app">
        <div class="row justify-content-center">
            <div class="col-3">
                <div class="row">
                    <div class="col">
                        <router-link to="author"><a href="" class="btn btn-primary"><i> Авторы </i></a></router-link>
                    </div>
                    <div class="col">
                        <router-link to="book"><a href=""  class="btn btn-primary"><i> Книги </i></a></router-link>
                    </div>
                    <div class="col">
                        <router-link to="genre"> <a href="" class="btn btn-primary"><i> Жанры </i></a></router-link>
                    </div>
                </div>
            </div>
        </div>
        <hr>
        <div class="container">
            <keep-alive>
                <router-view></router-view>
            </keep-alive>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'App',
    }
</script>

<style>
    #app {
        margin-top: 60px
    }
</style>
