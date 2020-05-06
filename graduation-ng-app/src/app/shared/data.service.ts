import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})

@Injectable()
export class DataService {

  private messageSource = new BehaviorSubject('0');
  itemsInCart = this.messageSource.asObservable();

  constructor() { }

  emitItemsChange(message: string) {
    this.messageSource.next(message)
  }
}
