import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavigationComponent} from './navigation/navigation.component';
import {BooksComponent} from './books/books.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {AuthorsComponent} from './authors/authors.component';
import {GenresComponent} from './genres/genres.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {BookDetailsComponent} from './books/book-details/book-details.component';
import {BookAddComponent} from './books/book-add/book-add.component';
import {BookEditComponent} from './books/book-edit/book-edit.component';

@NgModule({
  declarations: [
    AppComponent,
    NavigationComponent,
    BooksComponent,
    NotFoundComponent,
    AuthorsComponent,
    GenresComponent,
    BookDetailsComponent,
    BookAddComponent,
    BookEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
