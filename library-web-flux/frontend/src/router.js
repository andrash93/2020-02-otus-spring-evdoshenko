import Vue from 'vue';
import Router from 'vue-router';
import AuthorComponent from './components/AuthorComponent.vue';
import BookComponent from './components/BookComponent.vue';
import GenreComponent from './components/GenreComponent.vue';
import MainComponent from './components/MainComponent.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'Main',
            component: MainComponent,
        },
        {
            path: '/author',
            name: 'Author',
            component: AuthorComponent,
        },
        {
            path: '/book',
            name: 'Book',
            component: BookComponent,
        },
        {
            path: '/genre',
            name: 'Genre',
            component: GenreComponent,
        },
    ],
});