import {SavedJob} from './SavedJob';
import {FilterParams} from './FilterParams';

export class SearchParams {
  id: number;
  keywords: string[];
  location: string;
  username: string;
  searchFilters: Map<string, string>;
  filterParams: FilterParams;
  savedJobs: Set<SavedJob>;


  constructor(id: number, keywords: string[], location: string, username: string, searchFilters: Map<string, string>, filterParams: FilterParams, savedJobs: Set<SavedJob>) {
    this.id = id;
    this.keywords = keywords;
    this.location = location;
    this.username = username;
    this.searchFilters = searchFilters;
    this.filterParams = filterParams;
    this.savedJobs = savedJobs;
  }
}
