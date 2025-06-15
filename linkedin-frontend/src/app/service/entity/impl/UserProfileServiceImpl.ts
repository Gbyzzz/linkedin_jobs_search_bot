import {SearchParams} from '../../../model/SearchParams';
import {HttpClient} from "@angular/common/http";
import {Observable} from 'rxjs';
import {Inject, Injectable, InjectionToken} from '@angular/core';
import {UserProfileService} from '../UserProfileService';
import { UserProfile } from '../../../model/UserProfile';

export const USER_PROFILE_URL_TOKEN = new InjectionToken<string>('url');


@Injectable({
  providedIn: 'root'
})
export class UserProfileServiceImpl implements UserProfileService {

  private readonly url: string;

  constructor(@Inject(USER_PROFILE_URL_TOKEN) private baseUrl: string, private HttpClient: HttpClient) {
    this.url = baseUrl;
  }

  findUserProfiles(): Observable<UserProfile[]> {
    return this.HttpClient.get<UserProfile[]>(this.url + "/all");
    }





}
