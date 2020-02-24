import {Component, OnInit} from '@angular/core';
import {Book} from "../model/book";
import {Author} from "../../authors/model/author";
import {Genre} from "../../genres/model/genre";
import {ActivatedRoute, Router} from "@angular/router";
import {ApiService} from "../../shared/api.service";
import {Location} from '@angular/common';

@Component({
  selector: 'app-book-edit',
  templateUrl: './book-edit.component.html',
  styleUrls: ['./book-edit.component.css']
})
export class BookEditComponent implements OnInit {

  uid: string;
  book: Book = new Book();
  authors: Author[] = [];
  genres: Genre[] = [];

  constructor(private activatedRoute:ActivatedRoute,
              private apiService: ApiService,
              private router: Router,
              private location: Location
  ) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.uid = params.get('uid');
      this.getBookDetails(params.get('uid'));
    });
  }

  editBook(): void {
    this.apiService.editBook(this.book).subscribe(
      res => {
        this.book = res;
        this.router.navigate(["/book-details/"+this.book.uid]);
      },
      err => {
        alert("An error has occurred!");
      }
    )
  }

  public getBookDetails(uid){
    this.apiService.getBookDetails(uid).subscribe(
      res => {
        this.book = res;
        this.getAllAuthors();
        this.getAllGenres();
      },
      err =>{
        alert("An Error Has Occurred!");
      }
    );
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

  public cancel() {
    this.location.back();
  }
}
