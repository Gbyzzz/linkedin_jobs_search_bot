import {SearchParams} from './SearchParams';

export class FilterParams {
  id: number;
  includeWordsInDescription: string[];
  searchParams: SearchParams;
  excludeWordsFromTitle: string[];


  constructor(id: number, includeWordsInDescription: string[], searchParams: SearchParams, excludeWordsFromTitle: string[]) {
    this.id = id;
    this.includeWordsInDescription = includeWordsInDescription;
    this.searchParams = searchParams;
    this.excludeWordsFromTitle = excludeWordsFromTitle;
  }
}
