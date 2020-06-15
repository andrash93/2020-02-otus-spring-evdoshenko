<template>

    <div class="row justify-content-center">
        <div class="col-1.5">
            <br>
            <button @click="onAddClick()" type="button" class="btn btn-success btn-sm btn-block"><i>
                Добавить </i></button>
            <br>
            <button @click="onListClick()" type="button" class="btn btn-success btn-sm btn-block"><i> Список </i>
            </button>

        </div>
        <div class="col-8">

            <!--Список жанров-->
            <table class="table table-hover" v-if="listMode">
                <thead>
                <tr>
                    <th>Название</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(genre, index) in genres" :key="index">
                    <td width="65%">{{ genre.name }}</td>
                    <td width="35%">
                        <button type="button" class="btn btn-warning btn-sm" @click="editGenreClick(genre.id)">
                            Редактировать
                        </button>
                        <button type="button" class="btn btn-danger btn-sm" @click="removeGenreClick(genre.id)">
                            Удалить
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>


            <!--Редактирование/добавление жанров-->
            <form id="edit-form" v-if="editMode">
                <input type="hidden" name="id" v-model="genre.id">
                <div class="form-group">
                    <label for="author-name">Название</label>
                    <input type="text" class="form-control" id="author-name" name="name" v-model="genre.name"
                           placeholder="Название">
                </div>
                <button @click="onSaveClick(genre)" type="button" class="btn btn-success btn-sm">Сохранить</button>
            </form>

            <!--поиск Жанров-->
            <form id="search-form" v-if="searchMode">
                <div class="form-group">
                    <label for="name">Название</label>
                    <input type="text" class="form-control" id="name" name="name" v-model="searchName"
                           placeholder="Название">
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
        name: 'Genre',
        data() {
            return {
                listMode: true,
                editMode: false,

                genres: [],
                genre: {
                    id: '',
                    name: '',
                },
                searchName: '',
            };
        },
        methods: {

            findAllGenres() {
                const path = '/api/genre/list';
                axios.get(path)
                    .then((res) => {
                        this.genres = res.data;
                    })
                    .catch((error) => {
                        console.error(error);
                    });
            },

            getGenreById(id) {
                const path = '/api/genre/' + id;
                axios.get(path)
                    .then((res) => {
                        this.genre = res.data;
                    })
                    .catch((error) => {
                        console.error(error);
                    });
            },

            addGenre(genre) {
                axios.post('/api/genre', genre)
                    .then(() => {
                        this.findAllGenres();
                        this.showListForm();
                    })
                    .catch((error) => {
                        console.log(error);
                        alert(error);
                    });
            },

            editGenre(genre) {
                axios.put("/api/genre/" + genre.id, genre)
                    .then(() => {
                        this.findAllGenres();
                        this.showListForm();
                    })
                    .catch((error) => {
                        console.error(error);
                        alert(error);
                    });
            },

            removeGenre(id) {
                axios.delete("/api/genre/" + id)
                    .then(() => {
                        this.findAllGenres();
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
            },

            showListForm() {
                this.listMode = true;
                this.editMode = false;
            },

            onSaveClick(genre) {
                if (genre.id) {
                    this.editGenre(genre);
                } else {
                    this.addGenre(genre);
                }
            },

            editGenreClick(id) {
                this.getGenreById(id);
                this.showAddForm();
                console.log("editGenreClick" + id)
            },

            removeGenreClick(id) {
                console.log("removeGenreClick" + id)
                this.removeGenre(id);
            },

            onAddClick() {
                this.genre.id = "";
                this.genre.name = "";
                this.showAddForm();
                console.log("onAddClick")
            },

            onListClick() {
                this.findAllGenres();
                this.showListForm();
                console.log("onListClick")
            },

        },

        created() {
            this.findAllGenres();
        },

    };
</script>