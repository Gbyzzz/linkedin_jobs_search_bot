import {Component, OnInit} from '@angular/core';
import {KeyValuePipe, NgForOf} from '@angular/common';
import {SearchParams} from '../../../model/SearchParams';
import {SearchParamsServiceImpl} from '../../../service/entity/impl/SearchParamsServiceImpl';
import {TokenStorageService} from '../../../service/auth/token-storage.service';

@Component({
  selector: 'app-search-params',
  imports: [
    NgForOf,
    KeyValuePipe
  ],
  templateUrl: './search-params.component.html',
  standalone: true,
  styleUrl: './search-params.component.scss'
})
export class SearchParamsComponent implements  OnInit {
  // userProfileId: number;
  protected searchParams: SearchParams[] = [];

  constructor(private searchParamsService: SearchParamsServiceImpl, private tokenStorage: TokenStorageService) {
  }

  ngOnInit(): void {
    // @ts-ignore
    this.searchParamsService.findAllByUserProfile(this.tokenStorage.getUser()?.chatId).subscribe(searchParams => {
      console.log(searchParams);
      this.searchParams = searchParams;
    });
  }
}

