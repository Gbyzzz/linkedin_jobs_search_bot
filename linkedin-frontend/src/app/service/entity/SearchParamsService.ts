import {CRUDService} from './CRUDService';
import {SearchParams} from '../../model/SearchParams';
import {Observable} from 'rxjs';

export interface SearchParamsService {
  findAllByUserProfile(id: number): Observable<SearchParams[]>;
}
