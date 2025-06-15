import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable()
export class LoginService {

  private eventSource = new BehaviorSubject<string>("default message");
  eventSubject = this.eventSource.asObservable();

  constructor() { }

  emitEvent(value: string) {
    this.eventSource.next(value)
  }

}
