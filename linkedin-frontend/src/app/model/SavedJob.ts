import {UserProfile} from './UserProfile';
import {SearchParams} from './SearchParams';

export class SavedJob {
  private _id: number;
  private _jobId: number;
  private _replyState: string;
  private _dateApplied: Date;
  private _userProfile: UserProfile;

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get jobId(): number {
    return this._jobId;
  }

  set jobId(value: number) {
    this._jobId = value;
  }

  get replyState(): string {
    return this._replyState;
  }

  set replyState(value: string) {
    this._replyState = value;
  }

  get dateApplied(): Date {
    return this._dateApplied;
  }

  set dateApplied(value: Date) {
    this._dateApplied = value;
  }

  get userProfile(): UserProfile {
    return this._userProfile;
  }

  set userProfile(value: UserProfile) {
    this._userProfile = value;
  }

  get searchParams(): Set<SearchParams> {
    return this._searchParams;
  }

  set searchParams(value: Set<SearchParams>) {
    this._searchParams = value;
  }

  private _searchParams: Set<SearchParams>;


  constructor(id: number, jobId: number, replyState: string, dateApplied: Date,
              userProfile: UserProfile, searchParams: Set<SearchParams>) {
    this._id = id;
    this._jobId = jobId;
    this._replyState = replyState;
    this._dateApplied = dateApplied;
    this._userProfile = userProfile;
    this._searchParams = searchParams;
  }
}
