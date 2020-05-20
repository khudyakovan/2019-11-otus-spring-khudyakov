import {Component, OnInit} from '@angular/core';
import {ApiService} from "../shared/api.service";
import {first} from "rxjs/operators";
import {Router} from "@angular/router";
import {DataService} from "../shared/data.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    public username: string;
    public password: string;
    public error: string;

    constructor(private apiService: ApiService,
                private dataService: DataService,
                private router: Router) {
    }

    ngOnInit(): void {
    }

    public submit() {
        this.apiService.login(this.username, this.password)
            .pipe(first())
            .subscribe(
                result => {
                    this.router.navigate(['/']);
                },
                err => this.error = 'Could not authenticate'
            );
    }

}
