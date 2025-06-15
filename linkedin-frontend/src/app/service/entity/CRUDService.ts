import {Observable} from "rxjs";

export interface CRUDService<T>{

  findAll(): Observable<T[]>;

  findById(id: number): Observable<T>;

  update(odj: T): Observable<T>;

  delete(id: number): Observable<T>;

  add(obj: T): Observable<T>;

}
