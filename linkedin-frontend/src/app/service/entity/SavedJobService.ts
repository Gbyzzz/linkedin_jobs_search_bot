import {Observable} from 'rxjs';
import {SearchParams} from '../../model/SearchParams';
import {SavedJob} from '../../model/SavedJob';
import {FullJob} from '../../model/FullJob';
import {Pagination} from '../../model/pagination/Pagination';

export interface SavedJobService{
  findSavedJobsByUserProfile(id: number, type: string, pagination: Pagination): Observable<any>;
  findFullJobById(id: number): Observable<FullJob>;
  saveAll(savedJobs: SavedJob[]): Observable<any>;

}
