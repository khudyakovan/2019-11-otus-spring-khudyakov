import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})

@Injectable()
export class DataService {

  private itemsMessageSource = new BehaviorSubject('0');
  private rolesMessageSource = new BehaviorSubject(null);

  itemsInCart = this.itemsMessageSource.asObservable();

  currentRole = this.rolesMessageSource.asObservable();

  constructor() { }

  emitItemsChange(message: string) {
    this.itemsMessageSource.next(message)
  }

  emitRoleChange(message: string){
    this.rolesMessageSource.next(message);
  }
}
