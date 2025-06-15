import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from "../../../environments/environment";


const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) {
  }

  login(username: string, password: string): Observable<any> {
    return this.http.post(environment.API_URL + 'sign_in', {
      username,
      password
    }, httpOptions);
  }

  telegramLogin(user: any): Observable<any> {
    return this.http.post(environment.API_URL + 'telegram_sign_in', user, httpOptions);
  }

  logout(): Observable<any> {
    return this.http.post(environment.API_URL + 'sign_out', httpOptions);
  }

  // checkPassword(passwordChange: PasswordChange): Observable<any> {
  //   return this.http.post<any>(environment.API_URL + 'check_password', passwordChange);
  // }
  //
  // changePassword(passwordChange: PasswordChange): Observable<any> {
  //   return this.http.post<any>(environment.API_URL + 'change_password', passwordChange);
  // }
  //
  // sendRecoverPasswordEmail(email: String): Observable<any> {
  //   return this.http.post<any>(environment.API_URL + 'send_password_recover_email', email);
  // }
  //
  // recoverPassword(passwordChange: PasswordChange): Observable<any> {
  //   return this.http.post<any>(environment.API_URL + 'recover_password', passwordChange,
  //     {
  //     headers: { 'Content-Type': 'application/json' }
  //   });
  // }

}
