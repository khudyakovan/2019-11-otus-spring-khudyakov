import {Component, OnInit} from '@angular/core';
import {Genre} from "./model/genre";
import {ApiService} from "../shared/api.service";

@Component({
  selector: 'app-genres',
  templateUrl: './genres.component.html',
  styleUrls: ['./genres.component.css']
})
export class GenresComponent implements OnInit {

  genres: Genre[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
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

}
