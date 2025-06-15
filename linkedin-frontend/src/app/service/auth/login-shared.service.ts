import {Injectable} from "@angular/core";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginSharedService {
  private eventLoggedSource = new BehaviorSubject<boolean>(false);
  eventLoggedSubject = this.eventLoggedSource.asObservable();
  private eventUsernameSource = new BehaviorSubject<String>("");
  eventUsernameSubject = this.eventUsernameSource.asObservable();
  constructor() { }

  emitLoggedEvent(value: boolean) {
    this.eventLoggedSource.next(value)
  }
  emitUsernameEvent(value: string) {
    this.eventUsernameSource.next(value)
  }
}
