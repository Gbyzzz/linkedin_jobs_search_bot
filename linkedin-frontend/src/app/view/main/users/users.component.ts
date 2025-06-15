import {Component, OnInit} from '@angular/core';
import {KeyValuePipe, NgForOf} from "@angular/common";
import {UserProfile} from '../../../model/UserProfile';
import {UserProfileServiceImpl} from '../../../service/entity/impl/UserProfileServiceImpl';

@Component({
  selector: 'app-users',
  imports: [
    NgForOf
  ],
  templateUrl: './users.component.html',
  standalone: true,
  styleUrl: './users.component.scss'
})
export class UsersComponent implements OnInit {

  protected userProfiles: UserProfile[] = [];

  constructor(private userProfileService: UserProfileServiceImpl) {
  }
    ngOnInit(): void {
        this.userProfileService.findUserProfiles().subscribe(userProfiles =>{

          this.userProfiles = userProfiles;
        });
    }

}
