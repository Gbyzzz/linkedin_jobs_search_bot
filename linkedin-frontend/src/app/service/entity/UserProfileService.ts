import {CRUDService} from './CRUDService';
import {UserProfile} from '../../model/UserProfile';
import {Observable} from 'rxjs';

export interface UserProfileService {
  findUserProfiles(): Observable<UserProfile[]>;
}
