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

    constructor() {
    }

    emitItemsChange(message: string): void {
        this.itemsMessageSource.next(message)
    }

    emitRoleChange(message: string): void {
        this.rolesMessageSource.next(message);
    }

    getStatusTranslation(status: string): string {
        switch (status) {
            case 'READY':
                return "Готов к выдаче";
            case 'DELETED':
                return "Отменен";
            case 'CANCELLED':
                return "Отменен";
            case 'QUEUED':
                return "В очереди на комплектацию";
            case 'COMPLETED':
                return "Выдан покупателю";
            default:
                return "В очереди на комплектацию";
        }
    }

}
