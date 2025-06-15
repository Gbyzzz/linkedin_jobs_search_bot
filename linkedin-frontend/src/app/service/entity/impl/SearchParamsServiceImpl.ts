import {SearchParamsService} from '../SearchParamsService';
import {SearchParams} from '../../../model/SearchParams';
import {HttpClient} from "@angular/common/http";
import {Observable} from 'rxjs';
import {Inject, Injectable, InjectionToken} from '@angular/core';

export const SEARCH_PARAMETERS_URL_TOKEN = new InjectionToken<string>('url');




@Injectable({
  providedIn: 'root'
})
export class SearchParamsServiceImpl implements SearchParamsService {

  private readonly url: string;

  constructor(@Inject(SEARCH_PARAMETERS_URL_TOKEN) private baseUrl: string, private HttpClient: HttpClient) {
    this.url = baseUrl;
  }

  findAllByUserProfile(id: number): Observable<SearchParams[]> {
    return this.HttpClient.get<SearchParams[]>(this.url + "/" + id);
  }




}
