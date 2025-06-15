import {Inject, Injectable} from "@angular/core";

export enum SortDirection{
  ASC = "ASC",
  DESC = "DESC",
}

@Injectable({
  providedIn: 'root'
})
export class SortDirectionUtil{
  change(sortDirection: SortDirection): SortDirection {
    if(sortDirection === SortDirection.ASC){
      sortDirection = SortDirection.DESC;
    } else {
      sortDirection = SortDirection.ASC;
    }
    return sortDirection;
  }
}

export class Pagination{
  pageSize: number;
  pageNumber: number;
  sortDirection: SortDirection;

  constructor(pageSize: number, pageNumber: number, sortDirection: SortDirection) {
    this.pageSize = pageSize;
    this.pageNumber = pageNumber;
    this.sortDirection = sortDirection;
  }
}
