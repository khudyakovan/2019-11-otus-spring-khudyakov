import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Author} from "../authors/model/author";
import {Genre} from "../genres/model/genre";
import {Book} from "../books/model/book";

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  private BASE_URL = "http://localhost:8080/api";
  private ALL_BOOKS_URL = `${this.BASE_URL}/`;
  private BOOK_DETAILS_URL = `${this.BASE_URL}/books/`;
  private NEW_BOOK_URL = `${this.BASE_URL}/books/add`;
  private EDIT_BOOK_URL = `${this.BASE_URL}/books/edit/`;
  private ALL_AUTHORS_URL = `${this.BASE_URL}/authors/all`;
  private ALL_GENRES_URL = `${this.BASE_URL}/genres/all`;


  constructor(private http: HttpClient) {
  }

  getAllBooks(): Observable<Book[]>{
    return this.http.get<Book[]>(this.ALL_BOOKS_URL);
  }

  getAllAuthors(): Observable<Author[]> {
    return this.http.get<Author[]>(this.ALL_AUTHORS_URL);
  }

  getAllGenres(): Observable<Genre[]> {
    return this.http.get<Genre[]>(this.ALL_GENRES_URL);
  }

  getBookDetails(uid: string): Observable<Book>{
    return this.http.get<Book>(this.BOOK_DETAILS_URL+uid);
  }

  addNewBook(book: Book): Observable<Book>{
    return this.http.post<Book>(this.NEW_BOOK_URL, book);
  }

  editBook(book: Book): Observable<Book>{
    return this.http.put<Book>(this.EDIT_BOOK_URL+book.uid, book);
  }
}
