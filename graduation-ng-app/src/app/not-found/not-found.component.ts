import {Component, OnInit} from '@angular/core';
import {DataService} from "../shared/data.service";

@Component({
    selector: 'app-not-found',
    templateUrl: './not-found.component.html',
    styleUrls: ['./not-found.component.css']
})
export class NotFoundComponent implements OnInit {
    badge: string;

    constructor(private data: DataService) {
    }

    ngOnInit(): void {
        this.data.itemsInCart.subscribe(message => this.badge = message);
    }

}
