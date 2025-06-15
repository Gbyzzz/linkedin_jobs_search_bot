import {Component, OnInit} from '@angular/core';
import {JobsComponent} from "./jobs/jobs.component";
import {MatTab, MatTabContent, MatTabGroup} from "@angular/material/tabs";
import {SearchParamsComponent} from "./search-params/search-params.component";
import {HeaderComponent} from '../header/header.component';
import {NgIf} from '@angular/common';
import {UserProfile} from '../../model/UserProfile';
import {TokenStorageService} from '../../service/auth/token-storage.service';
import {UsersComponent} from './users/users.component';

@Component({
  selector: 'app-main',
  imports: [
    JobsComponent,
    MatTab,
    MatTabContent,
    MatTabGroup,
    SearchParamsComponent,
    UsersComponent,
    NgIf
  ],
  templateUrl: './main.component.html',
  standalone: true,
  styleUrl: './main.component.scss'
})
export class MainComponent implements OnInit {
protected userProfile: UserProfile | null | undefined;

constructor(private tokenStorage: TokenStorageService) {
}

  ngOnInit(): void {
    this.userProfile = this.tokenStorage.getUser();
    }
}
