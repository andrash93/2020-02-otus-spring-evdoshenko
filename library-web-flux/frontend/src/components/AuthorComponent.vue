<template>

    <div class="row justify-content-center">
        <div class="col-1.5">
            <br>
            <button @click="onAddClick()" type="button" class="btn btn-success btn-sm btn-block"><i>
                Добавить </i></button>
            <br>
            <button @click="onSearchClick()" type="button" class="btn btn-success btn-sm btn-block"><i>
                Поиск </i></button>
            <br>
            <button @click="onListClick()" type="button" class="btn btn-success btn-sm btn-block"><i> Список </i>
            </button>
        </div>
        <div class="col-8">

            <!--Список авторов-->
            <table class="table table-hover" v-if="listMode">
                <thead>
                <tr>
                    <th>Автор</th>
                    <th>Страна</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(author, index) in authors" :key="index">
                    <td width="35%">{{ author.name }}</td>
                    <td width="35%">{{ author.country }}</td>
                    <td width="30%">
                        <button type="button" class="btn btn-warning btn-sm" @click="editAuthorClick(author.id)">
                            Редактировать
                        </button>
                        <button type="button" class="btn btn-danger btn-sm" @click="removeAuthorClick(author.id)">
                            Удалить
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>

            <!--Редактирование/добавление авторов-->
            <form id="edit-form" v-if="editMode">
                <input type="hidden" name="id" v-model="author.id">
                <div class="form-group">
                    <label for="author-name">Имя автора</label>
                    <input type="text" class="form-control" id="author-name" name="name" v-model="author.name"
                           placeholder="имя автора">
                </div>
                <div class="form-group">
                    <label for="author-county">Страна</label>
                    <input type="text" class="form-control" id="author-county" name="country" v-model="author.country"
                           placeholder="страна">
                </div>
                <button @click="onSaveClick(author)" type="button" class="btn btn-success btn-sm">Сохранить</button>
            </form>

            <!--поиск авторов-->
            <form id="search-form" v-if="searchMode">
                <div class="form-group">
                    <label for="name">Имя автора</label>
                    <input type="text" class="form-control" id="name" name="name" v-model="searchName"
                           placeholder="имя автора">
                </div>
                <button @click="onSearchSubmitClick(searchName)" type="button" class="btn btn-success btn-sm">Поиск
                </button>
            </form>


        </div>
    </div>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'Author',
        data() {
            return {
                listMode: true,
                editMode: false,
                searchMode: false,

                authors: [],
                author: {
                    id: '',
                    name: '',
                    country: '',
                },
                searchName: '',

            };
        },
        methods: {
            findAllAuthors() {
                const path = '/api/author/list';
                axios.get(path)
                    .then((res) => {
                        this.authors = res.data;
                    })
                    .catch((error) => {
                        console.error(error);
                    });
            },

            getAuthorByIdAuthor(id) {
                const path = '/api/author/' + id;
                axios.get(path)
                    .then((res) => {
                        this.author = res.data;
                    })
                    .catch((error) => {
                        console.error(error);
                    });
            },

            findAuthorByName(name) {
                const path = '/api/author/find?name=' + name;
                axios.get(path).then((res) => {
                    this.authors = res.data;
                }).catch((error) => {
                        console.error(error);
                });
            },

            addAuthor(author) {
                axios.post('/api/author', author)
                    .then(() => {
                        this.findAllAuthors();
                        this.showListForm();
                    })
                    .catch((error) => {
                        console.log(error);
                        alert(error);
                    });
            },

            editAuthor(author) {
                axios.put("/api/author/" + author.id, author)
                    .then(() => {
                        this.findAllAuthors();
                        this.showListForm();
                    })
                    .catch((error) => {
                        console.error(error);
                        alert(error);
                    });
            },

            removeAuthor(id) {
                axios.delete("/api/author/" + id)
                    .then(() => {
                        this.findAllAuthors();
                        this.showListForm();
                    })
                    .catch((error) => {
                        console.error(error);
                        alert(error);
                    });
            },

            showAddForm() {
                this.listMode = false;
                this.editMode = true;
                this.searchMode = false;
            },

            showListForm() {
                this.listMode = true;
                this.editMode = false;
                this.searchMode = false;
            },

            showSearchForm() {
                this.listMode = false;
                this.editMode = false;
                this.searchMode = true;
            },

            onSaveClick(author) {
                if (author.id) {
                    this.editAuthor(author);
                } else {
                    this.addAuthor(author);
                }
            },

            editAuthorClick(id) {
                this.getAuthorByIdAuthor(id);
                this.showAddForm();
                console.log("editAuthorClick" + id)
            },

            removeAuthorClick(id) {
                console.log("removeAuthorClick" + id)
                this.removeAuthor(id);
            },

            onAddClick() {
                this.author.id = "";
                this.author.name = "";
                this.author.country = "";
                this.showAddForm();
                console.log("onAddClick")
            },

            onSearchClick() {
                this.searchName = '';
                this.showSearchForm();
                console.log("onSearchClick")
            },

            onListClick() {
                this.findAllAuthors();
                this.showListForm();
                console.log("onListClick")
            },

            onSearchSubmitClick(name) {
                this.findAuthorByName(name);
                this.showListForm();
                console.log("onSearchSubmitClick")
            },

        },

        created() {
            this.findAllAuthors();
        },

    };
</script>