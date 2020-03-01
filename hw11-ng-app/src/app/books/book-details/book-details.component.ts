import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Book} from "../model/book";
import {ApiService} from "../../shared/api.service";

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css']
})
export class BookDetailsComponent implements OnInit {

  book: Book;
  id: string;

  constructor(private activatedRoute:ActivatedRoute,
              private apiService: ApiService,
              private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => {
      this.id = params.get('id');
      this.getBookDetails(params.get('id'));
    });
  }

  public getBookDetails(uid){
    this.apiService.getBookDetails(uid).subscribe(
      res => {
        this.book = res;
      },
      err =>{
        alert("An Error Has Occurred!");
      }
    );
  }

  public deleteBook(){
    this.apiService.deleteBook(this.book).subscribe(
      res => {
        this.router.navigate(["/"]);
      },
      err =>{
        alert("An Error Has Occurred!");
      }
    );
  }

}
