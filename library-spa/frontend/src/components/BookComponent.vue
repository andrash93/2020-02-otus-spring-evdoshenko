<template>

    <div class="row justify-content-center">
        <div class="col-1.5">
            <br>
            <button @click="onAddClick()" type="button" class="btn btn-success btn-sm btn-block"><i>
                Добавить </i></button>
            <br>
            <button @click="onSearchByNameFormClick()" type="button" class="btn btn-success btn-sm btn-block"><i>
                Поиск </i></button>
            <br>
            <button @click="onSearchByAuthorFormClick()" type="button" class="btn btn-success btn-sm btn-block"><i>
                Поиск по автору</i></button>
            <br>
            <button @click="onListClick()" type="button" class="btn btn-success btn-sm btn-block"><i> Список </i>
            </button>
        </div>
        <div class="col-8">

            <!--Список книг-->
            <table class="table table-hover" v-if="listMode">
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Автор</th>
                    <th>Жанр</th>
                    <th>Изд-во</th>
                    <th>Стр</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(book, index) in books" :key="index">
                    <td width="16%">{{ book.name }}</td>
                    <td width="16%">{{ book.author }}</td>
                    <td width="16%">{{ book.genre }}</td>
                    <td width="16%">{{ book.publishing }}</td>
                    <td width="3%">{{ book.page }}</td>
                    <td width="33%">
                        <button type="button" class="btn btn-warning btn-sm" @click="onEditClick(book.id)">
                            Редактировать
                        </button>
                        <button type="button" class="btn btn-danger btn-sm" @click="onRemoveClick(book.id)">
                            Удалить
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>

            <!--Редактирование/добавление книг-->
            <form id="edit-form" v-if="editMode">
                <input type="hidden" name="id" v-model="book.id">
                <div class="form-group">
                    <label for="book-name">Название</label>
                    <input type="text" class="form-control" id="book-name" name="name" placeholder="Название"
                           v-model="book.name">
                </div>
                <div class="form-group">
                    <label for="author-input">Автор:</label>
                    <select class="form-control" id="author-input" name="authorId"
                            v-model="selectedAuthorId">
                        <option v-for="author in authors" v-bind:key="author.id" v-bind:value="author.id">
                            {{author.name}}
                        </option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="genre-input">Жанр:</label>
                    <select class="form-control" id="genre-input" name="genreId"
                            v-model="selectedGenreId">
                        <option v-for="genre in genres" v-bind:value="genre.id" v-bind:key="genre.id">{{genre.name}}
                        </option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="publishing-name">Издательство</label>
                    <input type="text" class="form-control" id="publishing-name" name="publishing"
                           placeholder="Издательство" v-model="book.publishing">
                </div>
                <div class="form-group">
                    <label for="page-name">Количество страниц</label>
                    <input type="text" class="form-control" id="page-name" name="page"
                           placeholder="Страниц" v-model="book.quantityPages">
                </div>

                <button @click="onSaveClick(book)" type="button" class="btn btn-success btn-sm">Сохранить</button>
            </form>

            <!--поиск книг по автору-->
            <form id="search-form" v-if="searchByAuthorMode">
                <div class="form-group">
                    <label for="author-id-input">Автор:</label>
                    <select class="form-control" id="author-id-input" name="authorId"
                            v-model="searchAuthorId">
                        <option v-for="author in authors" v-bind:key="author.id" v-bind:value="author.id">{{author.name}}</option>
                    </select>
                </div>
                <button @click="onSearchByAuthorClick(searchAuthorId)" type="button" class="btn btn-success btn-sm">
                    Поиск
                </button>
            </form>

            <!--поиск книг по названию-->
            <form id="search-name-form" v-if="searchByNameMode">
                <div class="form-group">
                    <div class="form-group">
                        <label for="name">Название</label>
                        <input type="text" class="form-control" id="name" name="page"
                               placeholder="Название" v-model="searchName">
                    </div>

                </div>
                <button @click="onSearchByNameClick(searchName)" type="button" class="btn btn-success btn-sm">
                    Поиск
                </button>
            </form>

        </div>
    </div>

</template>

<script>
    import axios from 'axios';

    export default {
        name: 'Book',
        data() {
            return {

                listMode: true,
                editMode: false,
                searchByNameMode: false,
                searchByAuthorMode: false,

                books: [],
                authors: [],
                genres: [],
                book: {
                    id: '',
                    name: '',
                    author: '',
                    genre: '',
                    publishing: '',
                    quantityPages: '',
                },
                selectedAuthorId: '',
                selectedGenreId: '',


                searchName: '',
                searchAuthorId: '',
            };
        },
        methods: {

            findAllBooks() {
                const path = '/api/book/list';
                axios.get(path)
                    .then((res) => {
                        this.books = res.data;
                    })
                    .catch((error) => {
                        console.error(error);
                    });
            },

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

            getBookById(id) {
                const path = '/api/book/' + id;
                axios.get(path)
                    .then((res) => {
                        this.book = res.data;
                        this.selectedAuthorId = res.data.authorId;
                        this.selectedGenreId = res.data.genreId;
                    })
                    .catch((error) => {
                        console.error(error);
                    });
            },

            removeBook(id) {
                axios.delete("/api/book/" + id)
                    .then(() => {
                        this.findAllBooks();
                        this.showListForm();
                    })
                    .catch((error) => {
                        console.error(error);
                        alert(error);
                    });
            },

            findBookByAuthor(id) {
                const path = '/api/book/find/author?id=' + id;
                axios.get(path).then((res) => {
                    this.books = res.data;
                }).catch((error) => {
                    console.error(error);
                });
            },

            findBookByName(name) {
                const path = '/api/book/find/name?name=' + name;
                axios.get(path).then((res) => {
                    this.books = res.data;
                }).catch((error) => {
                    console.error(error);
                });
            },

            addBook(book) {
                axios.post('/api/book', book)
                    .then(() => {
                        this.findAllBooks();
                        this.showListForm();
                    })
                    .catch((error) => {
                        console.log(error);
                        alert(error);
                    });
            },

            editBook(book) {
                axios.put("/api/book/" + book.id, book)
                    .then(() => {
                        this.findAllBooks();
                        this.showListForm();
                    })
                    .catch((error) => {
                        console.error(error);
                        alert(error);
                    });
            },

            showListForm() {
                this.listMode = true;
                this.editMode = false;
                this.searchByNameMode = false;
                this.searchByAuthorMode = false;
            },

            showAddForm() {
                this.listMode = false;
                this.editMode = true;
                this.searchByNameMode = false;
                this.searchByAuthorMode = false;
            },

            showSearchByAuthorForm() {
                this.listMode = false;
                this.editMode = false;
                this.searchByNameMode = false;
                this.searchByAuthorMode = true;
            },

            showSearchByNameForm() {
                this.listMode = false;
                this.editMode = false;
                this.searchByNameMode = true;
                this.searchByAuthorMode = false;
            },

            onEditClick(id) {
                this.getBookById(id);
                this.findAllAuthors();
                this.findAllGenres();
                console.log("onEditClick" + id + " this.book.author = " + this.book.author + " this.book.name = " + this.book.name)
                this.showAddForm();
                console.log("onEditClick" + id)
            },

            onRemoveClick(id) {
                console.log("onRemoveClick" + id)
                this.removeBook(id);
            },

            onAddClick() {
                this.book.id = "";
                this.book.name = "";
                this.book.author = "";
                this.book.genre = "";
                this.book.publishing = "";
                this.book.quantityPages = "";
                this.selectedAuthorId = "";
                this.selectedGenreId = "";
                this.findAllAuthors();
                this.findAllGenres();
                this.showAddForm();
                console.log("onAddClick")
            },


            onSaveClick(book) {
                if (book.id) {
                    const payload = {
                        id: book.id,
                        authorId: this.selectedAuthorId,
                        genreId: this.selectedGenreId,
                        name: book.name,
                        quantityPages: book.quantityPages,
                        publishing: book.publishing,
                    };
                    this.editBook(payload);
                } else {
                    console.log("this.selectedAuthorId = " + this.selectedAuthorId)
                    const payload = {
                        authorId: this.selectedAuthorId,
                        genreId: this.selectedGenreId,
                        name: book.name,
                        quantityPages: book.quantityPages,
                        publishing: book.publishing,
                    };
                    this.addBook(payload);
                }
            },

            onListClick() {
                this.findAllBooks();
                this.showListForm();
                console.log("onListClick")
            },

            onSearchByNameFormClick() {
                this.showSearchByNameForm();
                console.log("onSearchByNameFormClick")
            },

            onSearchByAuthorFormClick() {
                this.findAllAuthors();
                this.showSearchByAuthorForm();
                console.log("onSearchByAuthorFormClick")
            },

            onSearchByAuthorClick(id) {
                this.findBookByAuthor(id)
                this.showListForm();
                console.log("onSearchByAuthorClick")
            },

            onSearchByNameClick(name) {
                this.findBookByName(name)
                this.showListForm();
                console.log("onSearchByNameClick")
            },

        },
        activated() {
            this.findAllBooks();
            this.showListForm();
        },
        created() {
            this.findAllBooks();
            this.showListForm();
        },

    };
</script>