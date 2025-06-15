import {SavedJobService} from '../SavedJobService';
import {Observable} from 'rxjs';
import {SearchParams} from '../../../model/SearchParams';
import {Inject, Injectable, InjectionToken} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SEARCH_PARAMETERS_URL_TOKEN} from './SearchParamsServiceImpl';
import {SavedJob} from '../../../model/SavedJob';
import { FullJob } from '../../../model/FullJob';
import {Pagination} from '../../../model/pagination/Pagination';

export const SAVED_JOB_URL_TOKEN = new InjectionToken<string>('url');

@Injectable({
  providedIn: 'root'
})
export class SavedJobServiceImpl implements SavedJobService {

  private readonly url: string;

  constructor(@Inject(SAVED_JOB_URL_TOKEN) private baseUrl: string, private HttpClient: HttpClient) {
    this.url = baseUrl;
  }

  findSavedJobsByUserProfile(id: number, type: string, pagination: Pagination): Observable<any>{
    console.log(pagination);
    return this.HttpClient.post<any>(this.url + "/" + type + "/" + id, pagination);
  }

  findFullJobById(id: number): Observable<FullJob> {
    return this.HttpClient.get<FullJob>(this.url + "/job/" + id);
  }

  saveAll(savedJobs: SavedJob[]): Observable<any> {
    return this.HttpClient.post<any>(this.url + "/save_all", savedJobs);
  }

}
