import {Component, OnInit} from '@angular/core';
import {Book} from "../model/book";
import {ApiService} from "../../shared/api.service";
import {Router} from "@angular/router";
import {Author} from "../../authors/model/author";
import {Genre} from "../../genres/model/genre";
import {Location} from '@angular/common';

@Component({
  selector: 'app-book-add',
  templateUrl: './book-add.component.html',
  styleUrls: ['./book-add.component.css']
})
export class BookAddComponent implements OnInit {

  book: Book = new Book();
  authors: Author[] = [];
  genres: Genre[] = [];

  constructor(private router: Router,
              private apiService: ApiService,
              private location: Location) {
  }

  ngOnInit(): void {
    this.getAllAuthors();
    this.getAllGenres();
  }

  public getAllGenres(){
    this.apiService.getAllGenres().subscribe(
      res => {
        this.genres = res;
      },
      err =>{
        alert("An Error Has Occurred!");
      }
    );
  }

  public getAllAuthors(){
    this.apiService.getAllAuthors().subscribe(
      res => {
        this.authors = res;
      },
      err =>{
        alert("An Error Has Occurred!");
      }
    );
  }

  addNewBook(): void {
    this.apiService.addNewBook(this.book).subscribe(
      res => {
        this.book = res;
        this.router.navigate(["/book-details/"+this.book.uid]);
      },
      err => {
        alert("An error has occurred!");
      }
    )
  }

  public cancel() {
    this.location.back();
  }

}
