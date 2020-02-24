import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Book} from "../model/book";
import {ApiService} from "../../shared/api.service";

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css']
})
export class BookDetailsComponent implements OnInit {

  book: Book;
  uid: string;

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
      },
      err =>{
        alert("An Error Has Occurred!");
      }
    );
  }
}
