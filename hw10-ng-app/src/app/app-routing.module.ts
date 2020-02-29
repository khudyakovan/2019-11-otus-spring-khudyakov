import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {BooksComponent} from "./books/books.component";
import {NotFoundComponent} from "./not-found/not-found.component";
import {AuthorsComponent} from "./authors/authors.component";
import {GenresComponent} from "./genres/genres.component";
import {BookDetailsComponent} from "./books/book-details/book-details.component";
import {BookAddComponent} from "./books/book-add/book-add.component";
import {BookEditComponent} from "./books/book-edit/book-edit.component";


const routes: Routes = [
  {
    path: 'books',
    component: BooksComponent
  },
  {
    path: 'book-details/:uid',
    component: BookDetailsComponent
  },
  {
    path: 'books/add',
    component: BookAddComponent
  },
  {
    path: 'books/edit/:uid',
    component: BookEditComponent
  },
  {
    path: 'authors',
    component: AuthorsComponent
  },
  {
    path: 'genres',
    component: GenresComponent
  },
  {
    path: '',
    component: BooksComponent,
    pathMatch: 'full'
  },
  {
    path: '**',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: false})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
