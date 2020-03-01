import {Component, OnInit} from '@angular/core';
import {Author} from "./model/author";
import {ApiService} from "../shared/api.service";

@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements OnInit {

  authors: Author[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.getAllAuthors();
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
