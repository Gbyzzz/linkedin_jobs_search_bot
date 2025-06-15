import {Component, OnInit} from '@angular/core';
import {LoginSharedService} from '../../service/auth/login-shared.service';
import {AuthService} from '../../service/auth/auth.service';
import {TokenStorageService} from '../../service/auth/token-storage.service';
import {Router, RouterLink} from '@angular/router';
import {NgIf} from '@angular/common';
import {UserProfile} from '../../model/UserProfile';



@Component({
  selector: 'app-header',
  imports: [
    NgIf,
    RouterLink

  ],
  templateUrl: './header.component.html',
  standalone: true,
  styleUrl: './header.component.scss'
})
export class HeaderComponent implements  OnInit {
protected isLoggedIn = false;
protected userProfile: UserProfile | null | undefined;

constructor(private tokenStorage: TokenStorageService,
            private authService: AuthService,
            private router: Router) {
}

  ngOnInit(): void {
  this.userProfile = this.tokenStorage.getUser();
    if(this.userProfile) {
      this.isLoggedIn = true;
    }
    }

  logout() {
    this.tokenStorage.signOut();
    this.authService.logout();
    this.router.navigate(['/login']).then();
  }
}
