import {Component, OnInit} from '@angular/core';
import {Book} from "../model/book";
import {Author} from "../../authors/model/author";
import {Genre} from "../../genres/model/genre";
import {ActivatedRoute} from "@angular/router";
import {ApiService} from "../../shared/api.service";

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

  constructor(private activatedRoute:ActivatedRoute, private apiService: ApiService) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.uid = params.get('uid');
      this.getBookDetails(params.get('uid'));
    });
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

}
