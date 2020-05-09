import {Component, OnInit} from '@angular/core';
import {DataService} from "../shared/data.service";
import {ApiService} from "../shared/api.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-navigation',
    templateUrl: './navigation.component.html',
    styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {

    badge: string;

    constructor(private data: DataService,
                public apiService: ApiService,
                private router: Router) {
    }

    ngOnInit(): void {
        this.data.itemsInCart.subscribe(message => this.badge = message);
    }

    logout() {
        this.apiService.logout();
        this.router.navigate(['login']);
    }

}
