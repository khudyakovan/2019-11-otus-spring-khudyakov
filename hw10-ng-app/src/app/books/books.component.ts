import {Component, OnInit} from '@angular/core';
import {Book} from "./model/book";
import {ApiService} from "../shared/api.service";

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit {

  books: Book[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.getAllBooks();
  }

  public getAllBooks(){
    this.apiService.getAllBooks().subscribe(
      res => {
        this.books = res;
      },
      err =>{
        alert("An Error Has Occurred!");
      }
    );
  }
}
